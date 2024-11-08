package main;

import java.util.Scanner;

public class LibrarySystem {
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

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static BookService bookService = new BookService(); 

    public static void main(String[] args) {
        displayWelcomeMenu();
    }

    public static void displayWelcomeMenu() {
        System.out.println(BRIGHT_MAGENTA + "Welcome to the Library Management System " + RESET);
        System.out.println("1. Login as User");
        System.out.println("2. Login as Admin");
        System.out.print("Select option: ");
        
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                userFlow();
            } else if (choice == 2) {
                adminFlow();
            } else {
                System.out.println(BRIGHT_RED + "Invalid option. " + RESET);
                displayWelcomeMenu();  // Recursive call to return to main menu
            }
        } else {
            System.out.println(BRIGHT_RED + "Invalid input." + RESET);
            scanner.nextLine();  // Clear invalid input
            displayWelcomeMenu();
        }
    }

    private static void userFlow() {
        System.out.println(BLUE + "\nUser Login/Register " + RESET);
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Select option: ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                int userId = userService.registerUser(name, password);
                System.out.println(BRIGHT_YELLOW + "Registration successful! Your User ID is: " + RESET + userId);
                userMenu(userId);
            } else if (choice == 2) {
                System.out.print("Enter User ID: ");
                int userId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (userService.loginUser(userId, password)) {
                    System.out.println(BRIGHT_GREEN + "Login successful!" + RESET);
                    userMenu(userId);
                } else {
                    System.out.println(BRIGHT_RED + "Login failed. " + RESET);
                    displayWelcomeMenu();
                }
            } else {
                System.out.println(BRIGHT_RED + "Invalid choice. " + RESET);
                displayWelcomeMenu();
            }
        } else {
            System.out.println(BRIGHT_RED + "Invalid input. " + RESET);
            scanner.nextLine();  // Clear invalid input
            displayWelcomeMenu();
        }
    }

    private static void userMenu(int userId) {
        while (true) {
            System.out.println(BLUE + "\nMain Menu:" + RESET);
            System.out.println("1. View All Books");
            System.out.println("2. Purchase Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Select option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println(BLUE + "\nList of Available Books " + RESET);
                        bookService.showAllBooks();
                        break;
                    case 2:
                        System.out.print("Enter Book ID to purchase: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();
                        bookService.buyBook(bookId, userId);
                        break;
                    case 3:
                        System.out.print("Enter Book ID to return: ");
                        bookId = scanner.nextInt();
                        scanner.nextLine();
                        bookService.returnBook(bookId, userId);
                        break;
                    case 4:
                        System.out.println(YELLOW + "Returning to Main Menu..." + RESET);
                        displayWelcomeMenu();
                        return;
                    default:
                        System.out.println(BRIGHT_RED + "Invalid option. " + RESET);
                        displayWelcomeMenu();
                        return;
                }
            } else {
                System.out.println(BRIGHT_RED + "Invalid input." + RESET);
                scanner.nextLine();  // Clear invalid input
                displayWelcomeMenu();
                return;
            }
        }
    }

    private static void adminFlow() {
        System.out.println("\nAdmin Login ");
        System.out.print("Enter Admin Password: ");
        String adminPassword = scanner.nextLine();

        if (adminPassword.equals("admin123")) { // Sample password
            adminMenu();
        } else {
            System.out.println(BRIGHT_RED + "Invalid Admin password." + RESET);
            displayWelcomeMenu();
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println(BLUE + "\nAdmin Menu " + RESET);
            System.out.println("1. Add New Book");
            System.out.println("2. View Users who Purchased Books");
            System.out.println("3. View All User Details");
            System.out.println("4. Logout to Main Menu");
            System.out.print("Select option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Book Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        bookService.addNewBook(title, author, quantity);
                        System.out.println(BRIGHT_GREEN + "Book added successfully." + RESET);
                        break;
                    case 2:
                        bookService.showUsersWhoPurchasedBooks();
                        break;
                    case 3:
                        bookService.showAllUserDetails();
                        break;
                    case 4:
                        System.out.println(YELLOW + "Returning to Main Menu..." + RESET);
                        displayWelcomeMenu();
                        return;
                    default:
                        System.out.println(BRIGHT_RED + "Invalid option. " + RESET);
                        displayWelcomeMenu();
                        return;
                }
            } else {
                System.out.println(BRIGHT_RED + "Invalid input." + RESET);
                scanner.nextLine();  // Clear invalid input
                displayWelcomeMenu();
                return;
            }
        }
    }
}
