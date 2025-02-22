package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/StockPriceServlet")
public class StockPriceServlet extends HttpServlet {
    
    private static final String API_KEY = "YOUR_ALPHA_VANTAGE_API_KEY"; // Add your API key
    private static final String API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stockSymbol = request.getParameter("symbol");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            jsonResponse.put("error", "Stock symbol is required!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                double currentPrice = fetchStockPrice(stockSymbol);
                if (currentPrice > 0) {
                    jsonResponse.put("symbol", stockSymbol);
                    jsonResponse.put("price", currentPrice);
                    response.setStatus(HttpServletResponse.SC_OK); // HTTP 200
                } else {
                    jsonResponse.put("error", "Failed to fetch stock price.");
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (IOException e) {
                jsonResponse.put("error", "Error fetching stock price: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }

    private double fetchStockPrice(String stockSymbol) throws IOException {
        String formattedUrl = String.format(API_URL, stockSymbol, API_KEY);

        HttpURLConnection conn = null;
        try {
            URL url = new URL(formattedUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("❌ API Request Failed! Response Code: " + responseCode);
                return 0.0;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject globalQuote = jsonResponse.optJSONObject("Global Quote");
                if (globalQuote != null) {
                    return globalQuote.optDouble("05. price", 0.0); // safer handling
                } else {
                    System.err.println("❌ Invalid API Response: " + jsonResponse);
                    return 0.0;
                }
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
