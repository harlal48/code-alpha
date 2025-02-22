package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/market-data")
public class MarketDataServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/stock_trading";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray jsonArray = new JSONArray();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT stock_name, current_price, last_updated FROM stock ORDER BY last_updated DESC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JSONObject stockData = new JSONObject();
                stockData.put("name", rs.getString("stock_name"));
                stockData.put("price", rs.getDouble("current_price"));
                stockData.put("time", rs.getTimestamp("last_updated").toString());
                jsonArray.put(stockData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonArray.put(new JSONObject().put("error", "Database connection failed: " + e.getMessage()));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonArray.toString());
            out.flush();
        }
    }
}
