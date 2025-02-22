package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import database.DatabaseConnection;

public class UserService {

    // Method to authenticate user using username and password
    public static User loginUser(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            System.err.println("❌ Username and password cannot be empty.");
            return null;
        }

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // Plain text — To be replaced with hashing later

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                } else {
                    System.out.println("❌ Invalid credentials for user: " + username);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error during login: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // No user found
    }
}
