package com.ticketreservation.dao;

import com.ticketreservation.db.DBConnection;
import com.ticketreservation.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private final Connection connection;

    public TicketDAO() {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Add a new ticket to the database
     * Note: Database schema needs to be adjusted to match this implementation
     */
    public boolean addTicket(Ticket ticket) {
        // Adjusted SQL to match actual database columns
        String sql = "INSERT INTO tickets (customer_name, event_name, status, created_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getCustomerName());
            stmt.setString(2, ticket.getEventName());
            stmt.setString(3, ticket.getStatus());
            stmt.setString(4, ticket.getCreatedAt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding ticket: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update ticket status by ticket ID
     */
    public boolean updateTicketStatus(String status, int ticketId) {
        String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating ticket status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all waiting tickets in FIFO order
     */
    public List<Ticket> getWaitingTickets() {
        String sql = "SELECT * FROM tickets WHERE status = 'WAITING' ORDER BY created_at ASC";

        List<Ticket> ticketList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getString("customer_name"),
                        rs.getString("event_name"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
                ticketList.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving waiting tickets: " + e.getMessage());
            e.printStackTrace();
        }

        return ticketList;
    }

    /**
     * Get first waiting ticket without removing (PEEK operation)
     */
    public Ticket peekWaitingTicket() {
        String sql = "SELECT * FROM tickets WHERE status = 'WAITING' ORDER BY created_at ASC LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getString("customer_name"),
                        rs.getString("event_name"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error peeking waiting ticket: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get all processed tickets
     */
    public List<Ticket> getProcessedTickets() {
        String sql = "SELECT * FROM tickets WHERE status = 'PROCESSED' ORDER BY created_at DESC";

        List<Ticket> processedTickets = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getString("customer_name"),
                        rs.getString("event_name"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
                processedTickets.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving processed tickets: " + e.getMessage());
            e.printStackTrace();
        }

        return processedTickets;
    }

    /**
     * Get ticket by ID
     */
    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getString("customer_name"),
                        rs.getString("event_name"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving ticket: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get total count of tickets
     */
    public int getTotalTicketCount() {
        String sql = "SELECT COUNT(*) as total FROM tickets";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error counting tickets: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Delete ticket by ID (for cleanup)
     */
    public boolean deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting ticket: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

