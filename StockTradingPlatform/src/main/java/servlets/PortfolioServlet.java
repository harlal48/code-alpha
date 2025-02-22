package com.stocktrading;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.json.*;

@WebServlet("/PortfolioDataServlet")
public class PortfolioServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray stockArray = new JSONArray();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_trading", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT stock_name, buy_price, current_price FROM stock");

            while (rs.next()) {
                JSONObject stock = new JSONObject();
                stock.put("stockName", rs.getString("stock_name"));
                stock.put("buyPrice", rs.getDouble("buy_price"));
                stock.put("currentPrice", rs.getDouble("current_price"));
                stockArray.put(stock);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.print(stockArray.toString());
        out.flush();
    }
}
