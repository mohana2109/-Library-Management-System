package main;

import java.util.Scanner;

public class LibrarySystem {
    private static UserService userService = new UserService();
    private static BookService bookService = new BookService();
    
   
    public static final String RESET = "\033[0m";  // Reset to default
    public static final String BLACK = "\033[0;30m";  // Black
    public static final String RED = "\033[0;31m";  // Red
    public static final String GREEN = "\033[0;32m";  // Green
    public static final String YELLOW = "\033[0;33m";  // Yellow
    public static final String BLUE = "\033[0;34m";  // Blue
    public static final String MAGENTA = "\033[0;35m";  // Magenta
    public static final String CYAN = "\033[0;36m";  // Cyan
    public static final String WHITE = "\033[0;37m";  // White

    // Bright colors
    public static final String BRIGHT_BLACK = "\033[1;30m";  // Bright Black (Gray)
    public static final String BRIGHT_RED = "\033[1;31m";  // Bright Red
    public static final String BRIGHT_GREEN = "\033[1;32m";  // Bright Green
    public static final String BRIGHT_YELLOW = "\033[1;33m";  // Bright Yellow
    public static final String BRIGHT_BLUE = "\033[1;34m";  // Bright Blue
    public static final String BRIGHT_MAGENTA = "\033[1;35m";  // Bright Magenta
    public static final String BRIGHT_CYAN = "\033[1;36m";  // Bright Cyan
    public static final String BRIGHT_WHITE = "\033[1;37m";  // Bright White


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(BRIGHT_MAGENTA+"WELCOME TO THE LIBRARY MANAGEMENT SYSTEM "+RESET);

        int choice = 0;
        boolean validInput = false;

        // Loop for login or register
        while (!validInput) {
            System.out.println("1. Register\n2. Login");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            if (choice == 1) {
                // Register
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                int userId = userService.registerUser(name, password);
                System.out.println(BRIGHT_YELLOW+"Registration successful! Your User ID is:  " +RESET + userId);
            } else if (choice == 2) {
                // Login
                System.out.print("Enter User ID: ");
                int userId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                if (userService.loginUser(userId, name)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed. Please try again.");
                    continue;
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            validInput = true; // Exit loop after successful registration or login
        }

        // Main menu after login or registration
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Purchase Book\n2. Add Book\n3. Return Book\n4. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    // Purchase Book
                    System.out.println("\n--- List of Available Books ---");
                    bookService.showAllBooks();
                    System.out.print("Enter Book ID to purchase: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    bookService.buyBook(bookId, userService.getCurrentUserId());
                    break;

                case 2:
                    // Add Book
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book quantity: ");
                    int quantity = scanner.nextInt();
                    bookService.addBook(title, author, quantity);
                    break;

                case 3:
                    // Return Book
                    System.out.print("Enter Book ID to return: ");
                    bookId = scanner.nextInt();
                    bookService.returnBook(bookId);
                    break;

                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
