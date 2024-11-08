
Library Management System

The Library Management System is a console-based Java application designed to manage books in a library, handle user registrations, allow users to purchase and return books, and maintain transaction history. The system uses MySQL as the relational database to store details about books, users, and transactions.


## Features
- **User Registration & Login**: Automatically generates a user ID when a new user registers.
- **Admin Module**: Allows administrators to add new books, view user details, and track users who have purchased books.
- **Book Management**: Users can view available books, purchase books, add new books, and return borrowed books.
- **Transaction Logging**: The system logs transactions whenever a book is purchased or returned.
- **Database Integration**: Book and user information are stored in a MySQL database.


 ## Technologies Used

 - **Java**
- **MySQL**: Relational database management system for storing data.
- **JDBC (Java Database Connectivity)**: To connect and interact with the database.

##  Prerequisites
1. **Java**: Ensure you have JDK installed.
2. **MySQL**: A MySQL server to run the database.
3. **Eclipse IDE**: For coding and running the project.
##  Database Setup
1. Install MySQL and create a database named `LibraryDB`.
2. Use the following SQL queries to create tables and populate them:
   
   ```sql
   CREATE DATABASE LibraryDB;
   USE LibraryDB;
   
   -- Create Users Table
   CREATE TABLE users (
       user_id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100),
       password VARCHAR(100)
   );
   
   -- Create Books Table
   CREATE TABLE books (
       book_id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255),
       author VARCHAR(255),
       quantity INT
   );
   
   -- Create Transactions Table
   CREATE TABLE transactions (
       transaction_id INT AUTO_INCREMENT PRIMARY KEY,
       user_id INT,
       book_id INT,
       date_borrowed DATE,
       date_returned DATE,
       FOREIGN KEY (user_id) REFERENCES users(user_id),
       FOREIGN KEY (book_id) REFERENCES books(book_id)
   );
   
   -- Insert Sample Data into Books Table
   INSERT INTO books (title, author, quantity) VALUES
   ('Harry Potter', 'J.K. Rowling', 5),
   ('The Hobbit', 'J.R.R. Tolkien', 3),
   ('1984', 'George Orwell', 0),
   ('The Catcher in the Rye', 'J.D. Salinger', 2);

## Usage
**User Access**: Users can register, log in, view available books, purchase, and return books.
**Admin Access**: Admins log in with a predefined password to access additional management features.
