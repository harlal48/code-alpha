package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session, do not create a new one
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("user"); // Clear user data (optional)
            session.invalidate(); // Invalidate session
        }

        // Redirect to login page with logout message
        response.sendRedirect("login.jsp?message=You have been logged out successfully.");
    }
}
