-- Queue-Based Ticket Reservation System Database Schema

CREATE DATABASE IF NOT EXISTS ticket_reservation;
USE ticket_reservation;

-- Tickets Table (Main table for this system)
CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    status ENUM('WAITING', 'PROCESSED') DEFAULT 'WAITING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);