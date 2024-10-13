//Create the Database:
CREATE DATABASE LibraryDB;
USE LibraryDB;

//users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    password VARCHAR(100)
);

//books table

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    quantity INT
);

//transaction 

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    date_borrowed DATE,
    date_returned DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

//books table 

INSERT INTO books (title, author, quantity) VALUES
('Harry Potter', 'J.K. Rowling', 5),
('The Hobbit', 'J.R.R. Tolkien', 3),
('1984', 'George Orwell', 0),
('The Catcher in the Rye', 'J.D. Salinger', 2);
