package com.ticketreservation.ui;

import java.util.Scanner;
import java.util.List;

import com.ticketreservation.dsa.QueueManager;
import com.ticketreservation.dao.TicketDAO;
import com.ticketreservation.model.Ticket;
import com.ticketreservation.util.InputValidator;

public class Main {
    private static QueueManager queueManager;
    private static TicketDAO ticketDAO;
    private static Scanner scanner;

    public static void main(String[] args) {
        queueManager = new QueueManager();
        ticketDAO = new TicketDAO();
        scanner = new Scanner(System.in);

        loadQueueFromDatabase();

        int choice;

        do {
            showMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    addTicket();
                    break;
                case 2:
                    processTicket();
                    break;
                case 3:
                    queueManager.displayQueue();
                    break;

                case 4:
                    viewProcessedTickets();
                    break;
                case 5:
                    peekWaitingTicket();
                    break;
                case 6:
                    queueManager.displayStatistics();
                    System.out.println("\nThank you for using Ticket Reservation System!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please enter a number between 1 and 5.");
            }

        } while (choice != 6);

        scanner.close();
    }

    /** Display main menu */
    private static void showMenu() {
        System.out.println("\n==========================================");
        System.out.println("     QUEUE-BASED TICKET RESERVATION SYSTEM ");
        System.out.println("==========================================");
        System.out.println("1. Add Ticket Request(Enqueue)");
        System.out.println("2. Process Ticket(Dequeue)");
        System.out.println("3. View Waiting Queue(Enqueued tickets)");
        System.out.println("4. View Processed Tickets(Dequeued tickets)");
        System.out.println("5. Peek a ticket(Peek)");
        System.out.println("6. Exit");
        System.out.println("------------------------------------------");
        System.out.print("Enter your choice: ");
    }

    /** Read user menu choice */
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Add new ticket (ENQUEUE operation) */
    private static void addTicket() {
        System.out.print("\nEnter customer name: ");
        String customerName = scanner.nextLine().trim();

        // validation
        if (!InputValidator.isNotEmpty(customerName)) {
            System.out.println("Error: Customer name cannot be empty!");
            return;
        }

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine().trim();

        if (!InputValidator.isNotEmpty(eventName)) {
            System.out.println("Error: Event name cannot be empty!");
            return;
        }

        try {
            Ticket ticket = new Ticket(0, customerName, eventName, "WAITING");

            if (!ticketDAO.addTicket(ticket)) {
                System.out.println("Error: Failed to save ticket to database!");
                return;
            }

            if (!queueManager.enqueue(ticket)) {
                System.out.println("Error: Failed to add ticket to queue!");
                return;
            }

            System.out.println("\n✓ Ticket added successfully and placed in queue.");

        } catch (Exception e) {
            System.out.println("Error adding ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Process ticket (DEQUEUE operation) */
    private static void processTicket() {
        try {
            Ticket ticket = queueManager.dequeue();

            if (ticket == null) {
                return;
            }

            if (!ticketDAO.updateTicketStatus("PROCESSED", ticket.getTicketId())) {
                System.out.println("Warning: Ticket removed from queue but database update failed!");

            }

            System.out.println("\n✓ Ticket processed successfully:");
            System.out.println(ticket);

        } catch (Exception e) {
            System.out.println("Error processing ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Display all processed tickets */
    private static void viewProcessedTickets() {
        try {
            System.out.println("\n════════════════════════════════════════");
            System.out.println("        PROCESSED TICKETS");
            System.out.println("════════════════════════════════════════");

            List<Ticket> processedTickets = ticketDAO.getProcessedTickets();

            if (processedTickets.isEmpty()) {
                System.out.println("No processed tickets yet.");
            } else {
                processedTickets.forEach(System.out::println);

            }

            System.out.println("════════════════════════════════════════\n");

        } catch (Exception e) {
            System.out.println("Error retrieving processed tickets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Load waiting tickets from database into queue on startup */
    private static void loadQueueFromDatabase() {
        try {
            List<Ticket> waitingTickets = ticketDAO.getWaitingTickets();

            if (waitingTickets.isEmpty()) {
                System.out.println("No waiting tickets found in database.");
            } else {
                Ticket[] ticketArray = waitingTickets.toArray(new Ticket[0]);
                queueManager.loadFromDatabase(ticketArray);
            }

        } catch (Exception e) {
            System.out.println("Error loading queue from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void peekWaitingTicket() {
        try {
            Ticket ticket = queueManager.peek();

            if (ticket == null) {
                return;
            }

            System.out.println("\n✓ Peeked ticket successfully:");
            System.out.println("-------------------------------");
            System.out.println(ticket.toString());
              System.out.println("-------------------------------");

        } catch (Exception e) {
            System.out.println("Error peeking ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }
}