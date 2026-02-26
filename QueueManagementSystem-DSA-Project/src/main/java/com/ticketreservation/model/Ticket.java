package com.ticketreservation.model;

/** Ticket Model Class - Represents a ticket booking request */
public class Ticket {
    private int ticketId;
    private String customerName;
    private String eventName;
    private String status;
    private String createdAt;

    /** Default constructor for DAO instantiation */
    public Ticket() {
        this.ticketId = 0;
        this.customerName = "";
        this.eventName = "";
        this.status = "WAITING";
        this.createdAt = getCurrentTimestamp();
    }

    /** Constructor with all parameters */
    public Ticket(int ticketId, String customerName, String eventName, String status) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.eventName = eventName;
        this.status = status;
        this.createdAt = getCurrentTimestamp();
    }

    /** Constructor for database retrieval */
    public Ticket(int ticketId, String customerName, String eventName, String status, String createdAt) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.eventName = eventName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /** Get current timestamp */
    private String getCurrentTimestamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    @Override
    public String toString() {
        return String.format("Ticket ID: %d, Customer Name: %s, Event Name: %s, Status: %s, Created At: %s",
                ticketId, customerName, eventName, status, createdAt);
    }
}