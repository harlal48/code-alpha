package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class StockService {

    private static final String API_KEY = "demo"; // Replace with your API key
    private static final String API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s";

    public static double getStockPrice(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            System.err.println("❌ Stock symbol cannot be null or empty.");
            return 0.0;
        }

        try {
            String formattedUrl = String.format(API_URL, symbol, API_KEY);
            HttpURLConnection conn = (HttpURLConnection) new URL(formattedUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("❌ API Request Failed! Response Code: " + responseCode);
                return 0.0;
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());
            JSONObject globalQuote = json.optJSONObject("Global Quote");

            if (globalQuote != null && globalQuote.has("05. price")) {
                return globalQuote.getDouble("05. price");
            } else {
                System.err.println("❌ Stock data not found for symbol: " + symbol);
                return 0.0;
            }

        } catch (Exception e) {
            System.err.println("❌ Error fetching stock price: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }
}
