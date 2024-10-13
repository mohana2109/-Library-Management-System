package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserService {
    private int currentUserId;

    // Register a user and return the generated user ID
    public int registerUser(String name, String password) {
        int userId = -1;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentUserId = userId; // Set current logged in user
        return userId;
    }

    // Login a user
    public boolean loginUser(int userId, String name) {
        boolean success = false;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE id = ? AND name = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                success = true;
                currentUserId = userId;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }
}
