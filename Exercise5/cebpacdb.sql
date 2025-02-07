CREATE DATABASE CEBPACDB
 
USE CEBPACDB
 
CREATE TABLE Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    account_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO Admin (username, password) 
VALUES 
    ('pj', 'password123'),
    ('avi', 'mypassword'),
    ('frnnd', 'pswrd');
 
SELECT username, password, account_created FROM accounts;
