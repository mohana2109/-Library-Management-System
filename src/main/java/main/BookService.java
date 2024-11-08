 package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {
		     public static final String BRIGHT_GREEN = "\033[1;32m";
			 public static final String RESET = "\033[0m";  // Reset to default
			 public static final String GREEN = "\033[0;32m";  // Green
			 public static final String BRIGHT_RED = "\033[1;31m";  // Bright Red
			 public static final String BRIGHT_YELLOW = "\033[1;33m";  // Bright Yellow
			 public static final String BLUE = "\033[0;34m";  // Blue
	     public void showAllBooks() {
	         
	         System.out.printf("%-5s | %-30s | %-20s | %-10s%n", "ID", "Title", "Author", "Quantity");
	         System.out.println("--------------------------------------------------------------------------");

	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "SELECT * FROM books";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery();

	             while (resultSet.next()) {
	                 int id = resultSet.getInt("id");
	                 String title = resultSet.getString("title");
	                 String author = resultSet.getString("author");
	                 int quantity = resultSet.getInt("quantity");

	                 System.out.printf("%-5d | %-30s | %-20s | %-10d%n", id, title, author, quantity);
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     public void addNewBook(String title, String author, int quantity) {
	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             statement.setString(1, title);
	             statement.setString(2, author);
	             statement.setInt(3, quantity);
	             statement.executeUpdate();
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     public void buyBook(int bookId, int userId) {
	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "UPDATE books SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             statement.setInt(1, bookId);

	             int rowsUpdated = statement.executeUpdate();
	             if (rowsUpdated > 0) {
	                 recordTransaction(bookId, userId, "PURCHASE");
	                 System.out.println(BRIGHT_GREEN+"Book purchased successfully!"+RESET);
	             } else {
	                 System.out.println(BRIGHT_RED +"Book is out of stock."+RESET);
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     public void returnBook(int bookId, int userId) {
	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             statement.setInt(1, bookId);

	             int rowsUpdated = statement.executeUpdate();
	             if (rowsUpdated > 0) {
	                 recordTransaction(bookId, userId, "RETURN");
	                 System.out.println(BRIGHT_GREEN+"Book returned successfully!"+RESET);
	             } else {
	                 System.out.println(BRIGHT_RED+"Error in returning the book."+RESET);
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     public void showUsersWhoPurchasedBooks() {
	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "SELECT u.id, u.name, b.title FROM transactions t JOIN users u ON t.user_id = u.id JOIN books b ON t.book_id = b.id WHERE t.transaction_type = 'PURCHASE'";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             ResultSet resultSet = statement.executeQuery();

	             System.out.println(BLUE+"\nUsers Who Purchased Books"+RESET);
	             System.out.printf("%-10s | %-20s | %-30s%n", "User ID", "Name", "Book Title");
	             System.out.println("-----------------------------------------------------------------------------");
	             while (resultSet.next()) {
	                 System.out.printf("%-10d | %-20s | %-30s%n", resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("title"));
	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     private void recordTransaction(int bookId, int userId, String transactionType) {
	         try (Connection connection = DBConnection.getConnection()) {
	             String sql = "INSERT INTO transactions (user_id, book_id, transaction_type) VALUES (?, ?, ?)";
	             PreparedStatement statement = connection.prepareStatement(sql);
	             statement.setInt(1, userId);
	             statement.setInt(2, bookId);
	             statement.setString(3, transactionType);
	             statement.executeUpdate();
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }

	     public void showAllUserDetails() {
	         System.out.println(BLUE+"\nList of All Users"+RESET);
	         System.out.printf("%-5s  %-30s%n", "ID", "Name");
	         System.out.println("-------------------------------------");

	         try (Connection con = DBConnection.getConnection()) {
	             String sql = "SELECT id, name,password FROM users"; // Adjust this query if you have additional user details
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery();

	             while (rs.next()) {
	                 int id = rs.getInt("id");
	                 String name = rs.getString("name");

	                 System.out.printf("%-5d  %-30s%n", id, name);
	             }
	             System.out.println("-------------------------------------"); // Footer line
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }
	     }
	 }
