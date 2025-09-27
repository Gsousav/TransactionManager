package org.gabx.expenses.ui;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UserInputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return 0; // Default for some cases
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
    
    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount.");
            }
        }
    }
    
    public static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    return LocalDate.now();
                }
                
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Please enter date in format yyyy-MM-dd (e.g., 2024-01-15)");
            }
        }
    }
    
    public static String getCategoryInput(String prompt, List<String> categories) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            // Try to parse as number first
            try {
                int num = Integer.parseInt(input);
                if (num >= 1 && num <= categories.size()) {
                    return categories.get(num - 1);
                } else {
                    System.out.println("❌ Invalid number. Please try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                // Not a number, treat as category name
                if (categories.contains(input)) {
                    return input;
                } else {
                    // New category
                    if (confirmAction("🆕 '" + input + "' is a new category. Add it? (y/N): ")) {
                        return input;
                    } else {
                        System.out.println("Please select an existing category or number.");
                        continue;
                    }
                }
            }
        }
    }
    
    public static boolean confirmAction(String prompt) {
        System.out.print(prompt);
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
    
    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static String generateId() {
        return "TXN_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
}
