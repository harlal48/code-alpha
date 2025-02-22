package com.mycompany.stocktradingplatform.servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddStockServlet")
public class AddStockServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get stock data from form
        String stockName = request.getParameter("stockName");
        double buyPrice = Double.parseDouble(request.getParameter("buyPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Validate inputs (basic check)
        if (stockName == null || stockName.trim().isEmpty() || buyPrice <= 0 || quantity <= 0) {
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid inputs!\"}");
            return;
        }

        // Database connection
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_trading", "root", "")) {
            String query = "INSERT INTO stock (stock_name, buy_price, quantity, total_value) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, stockName);
                ps.setDouble(2, buyPrice);
                ps.setInt(3, quantity);
                ps.setDouble(4, buyPrice * quantity);
                
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().write("{\"status\": \"success\", \"message\": \"Stock added successfully!\"}");
                } else {
                    response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to add stock.\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}");
        }
    }
}
