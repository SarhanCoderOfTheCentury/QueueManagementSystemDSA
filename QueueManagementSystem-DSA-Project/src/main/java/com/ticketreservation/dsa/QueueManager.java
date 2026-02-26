/**
 * QueueManager - Queue implementation using Singly Linked List (FIFO)
 * O(1) enqueue/dequeue, O(n) displayQueue*/
package com.ticketreservation.dsa;

import com.ticketreservation.model.Ticket;

/** Node class for Singly Linked List */
class Node {
    Ticket data;
    Node next;

    public Node(Ticket data) {
        this.data = data;
        this.next = null;
    }

    public Ticket getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

public class QueueManager {
    private Node front;
    private Node rear;
    private int size;

    /** Initialize empty queue */
    public QueueManager() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /** Add ticket to rear of queue (FIFO) */
    public boolean enqueue(Ticket ticket) {
        try {
            if (ticket == null) {
                System.out.println("Error: Cannot enqueue null ticket");
                return false;
            }

            Node newNode = new Node(ticket);
            
            if (isEmpty()) {
                front = newNode;
                rear = newNode;
            } else {
                rear.setNext(newNode);
                rear = newNode;
            }
            
            size++;
            return true;
        } catch (Exception e) {
            System.out.println("Error in enqueue operation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /** Remove and return ticket from front of queue (FIFO) */
    public Ticket dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow! No tickets in waiting queue.");
            return null;
        }
        
        Ticket removedTicket = front.getData();
        front = front.getNext();
        
        if (front == null) {
            rear = null;
        }
        
        size--;
        return removedTicket;
    }

    /** View front ticket without removing it */
    public Ticket peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return null;
        }
        return front.getData();
    }

    /** Check if queue is empty */
    public boolean isEmpty() {
        return front == null;
    }

    /** Get current number of tickets in queue */
    public int getSize() {
        return size;
    }

    /** Display all waiting tickets in FIFO order (O(n)) */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║        WAITING QUEUE IS EMPTY              ║");
            System.out.println("╚════════════════════════════════════════════╝\n");
            return;
        }

        System.out.println("\n╔═════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              WAITING QUEUE (FIFO ORDER)               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Position  │  ID      │  Customer Name  │  Event Name  │  Time        ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════╣");

        Node current = front;
        int position = 1;

        while (current != null) {
            Ticket ticket = current.getData();
            System.out.printf("║%-6d │ %-6d │ %-12s │ %-12s │ %-12s ║\n",
                    position,
                    ticket.getTicketId(),
                    truncateString(ticket.getCustomerName(), 12),
                    truncateString(ticket.getEventName(), 12),
                    truncateString(ticket.getCreatedAt(), 12));
            
            current = current.getNext();
            position++;
        }

        System.out.println("╚═════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Waiting: " + size + " ticket(s)\n");
    }

    /** Truncate strings for display formatting */
    private String truncateString(String str, int length) {
        if (str == null) {
            return "";
        }
        if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        }
        return str;
    }

    /** Load queue from database on program startup */
    public void loadFromDatabase(Ticket[] waitingTickets) {
        if (waitingTickets == null || waitingTickets.length == 0) {
            System.out.println("No waiting tickets found in database.");
            return;
        }

        for (Ticket ticket : waitingTickets) {
            if (ticket != null) {
                enqueue(ticket);
            }
        }

        System.out.println("Loaded " + size + " waiting tickets from database.");
    }

    /** Display queue statistics */
    public void displayStatistics() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("        QUEUE STATISTICS");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Total Tickets in Queue: " + size);
        System.out.println("Queue Status: " + (isEmpty() ? "EMPTY" : "ACTIVE"));
        
        if (!isEmpty()) {
            System.out.println("Next to Process: " + front.getData().getCustomerName());
            System.out.println("Last in Queue: " + rear.getData().getCustomerName());
        }
        
        System.out.println("═══════════════════════════════════════\n");
    }
}