package com.mycompany.stocktradingplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DatabaseConnection;

@WebServlet("/SellStockServlet")
public class SellStockServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stockName = request.getParameter("stockName");
        int sellQuantity = Integer.parseInt(request.getParameter("quantity"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            // पहले चेक करें कि स्टॉक यूज़र के पास है या नहीं
            String checkStock = "SELECT id, quantity FROM stock WHERE stock_name = ? ORDER BY buy_price ASC";
            PreparedStatement checkStmt = conn.prepareStatement(checkStock);
            checkStmt.setString(1, stockName);
            ResultSet rs = checkStmt.executeQuery();

            int remainingQuantity = sellQuantity;
            while (rs.next() && remainingQuantity > 0) {
                int stockId = rs.getInt("id");
                int availableQuantity = rs.getInt("quantity");

                if (availableQuantity <= remainingQuantity) {
                    // पूरी row बेच दो (DELETE कर दो)
                    String deleteStock = "DELETE FROM stock WHERE id = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteStock);
                    deleteStmt.setInt(1, stockId);
                    deleteStmt.executeUpdate();
                    remainingQuantity -= availableQuantity;
                } else {
                    // कुछ quantity बची है, तो UPDATE करो
                    String updateStock = "UPDATE stock SET quantity = quantity - ? WHERE id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateStock);
                    updateStmt.setInt(1, remainingQuantity);
                    updateStmt.setInt(2, stockId);
                    updateStmt.executeUpdate();
                    remainingQuantity = 0;
                }
            }

            if (remainingQuantity == 0) {
                response.sendRedirect("portfolio.jsp?message=Stock Sold Successfully");
            } else {
                response.sendRedirect("portfolio.jsp?message=Not Enough Stock to Sell");
            }
        } catch (Exception e) {
    e.printStackTrace(); // Console में पूरी error दिखाएगा
    request.setAttribute("errorMessage", e.getMessage()); // Error message JSP में भेजो
    request.getRequestDispatcher("portfolio.jsp").forward(request, response);
}

    }
}
