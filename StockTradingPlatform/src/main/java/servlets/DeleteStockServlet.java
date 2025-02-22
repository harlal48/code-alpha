package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteStockServlet")
public class DeleteStockServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stockName = request.getParameter("stockName");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_trading", "root", "")) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM stock WHERE stock_name = ?");
            stmt.setString(1, stockName);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                out.write("{\"status\": \"success\", \"message\": \"Stock deleted successfully!\"}");
            } else {
                out.write("{\"status\": \"error\", \"message\": \"Stock not found!\"}");
            }
        } catch (Exception e) {
            out.write("{\"status\": \"error\", \"message\": \"Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
