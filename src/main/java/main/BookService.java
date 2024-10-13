package main;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {
    public void showAllBooks() {
      
        System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "Title", "Author", "Quantity");

        // Fetch books from database (assuming you have a ResultSet)
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM books";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through the result and print each book in a formatted manner
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");

                // Format each row with fixed-width columns
                System.out.printf("%-5d %-20s %-20s %-10d%n", id, title, author, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Purchase a book
    public void buyBook(int id, int userId) {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Check book quantity
            String sql = "SELECT quantity FROM books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity > 0) {
                    // Update quantity
                    String updateSql = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
                    PreparedStatement updatePstmt = conn.prepareStatement(updateSql);
                    updatePstmt.setInt(1, id);
                    updatePstmt.executeUpdate();
                    System.out.println("Book purchased successfully!");

                    // Log transaction (Assume transaction_id is AUTO_INCREMENT)
                    String logSql = "INSERT INTO transactions (user_id, book_id) VALUES (?, ?)";
                    PreparedStatement logPstmt = conn.prepareStatement(logSql);
                    logPstmt.setInt(1, userId);
                    logPstmt.setInt(2, id);  // Change 'id' to 'book_id' if your column name is different
                    logPstmt.executeUpdate();
                } else {
                    System.out.println("Book is out of stock.");
                }
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Add a new book to the library
    public void addBook(String title, String author, int quantity) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("Book added successfully!");

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return a book
    public void returnBook(int bookId) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
            System.out.println("Book returned successfully!");

            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
