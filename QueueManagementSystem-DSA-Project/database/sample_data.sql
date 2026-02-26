-- Sample Data for Ticket Reservation System

USE ticket_reservation;

-- Insert Sample Users
INSERT INTO users (username, email, password) VALUES
('user1', 'user1@example.com', 'password123'),
('user2', 'user2@example.com', 'password456'),
('user3', 'user3@example.com', 'password789'),
('user4', 'user4@example.com', 'password000'),
('user5', 'user5@example.com', 'password111');

-- Insert Sample Events
INSERT INTO events (event_name, event_date, total_tickets, available_tickets, price) VALUES
('Concert 2026', '2026-03-15 19:00:00', 100, 100, 50.00),
('Movie Premiere', '2026-04-20 18:30:00', 150, 150, 25.00),
('Sports Game', '2026-05-10 15:00:00', 200, 200, 75.00),
('Theater Show', '2026-06-05 20:00:00', 80, 80, 40.00),
('Conference 2026', '2026-07-15 09:00:00', 300, 300, 100.00);

-- Insert Sample Tickets
INSERT INTO tickets (event_id, ticket_number, status) 
SELECT event_id, CONCAT('TKT-', event_id, '-', ROW_NUMBER() OVER (PARTITION BY event_id ORDER BY event_id)) as ticket_number, 'AVAILABLE'
FROM (
    SELECT event_id FROM events
) e
CROSS JOIN (
    SELECT @row := 0
) init
WHERE @row < total_tickets;
