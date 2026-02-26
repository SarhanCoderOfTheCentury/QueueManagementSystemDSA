package com.ticketreservation.util;

/** Input validation utilities */
public class InputValidator {

    /** Validate email format */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(emailRegex);
    }

    /** Validate password (minimum 8 characters) */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    /** Validate username (alphanumeric, 3-20 characters) */
    public static boolean isValidUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9_]{3,20}$";
        return username != null && username.matches(usernameRegex);
    }

    /** Validate integer input */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Validate positive integer */
    public static boolean isPositiveInteger(String input) {
        try {
            int num = Integer.parseInt(input);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Validate double input */
    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Check if string is not empty or null */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}