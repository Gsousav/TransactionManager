package org.gabx.expenses.ui;

import org.gabx.expenses.transactions.Transaction;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private static final int STANDARD_MENU_WIDTH = 80; // Increased width for bigger UI
    private static final int LARGE_MENU_WIDTH = 100; // For main headers and large displays
    
    public static void showNumberedCategories(List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, categories.get(i));
        }
    }
    
    public static void displayTransactionSummary(Transaction transaction) {
        printCenteredSection("TRANSACTION DETAILS");
        System.out.printf("[ID] %s%n", transaction.getId());
        System.out.printf("[DATE] %s%n", transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("[DESC] %s%n", transaction.getDescription());
        System.out.printf("[AMOUNT] $%,.2f%n", transaction.getAmount());
        System.out.printf("[CATEGORY] %s%n", transaction.getCategory());
        System.out.printf("[TYPE] %s%n", transaction.getTransactionType());
    }
    
    public static void displayTransactionDetails(Transaction transaction) {
        String title = "TRANSACTION DETAILS";
        int maxWidth = Math.max(title.length() + 4, STANDARD_MENU_WIDTH);
        int contentWidth = maxWidth - 4; // Account for "| " and " |"
        
        // Header
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.println("|" + centerText(title, maxWidth - 2) + "|");
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        
        // Content - Fixed width calculations
        System.out.printf("| [ID] %-" + (contentWidth - 6) + "s |%n", 
                         truncate(transaction.getId(), contentWidth - 6));
        System.out.printf("| [DATE] %-" + (contentWidth - 7) + "s |%n", 
                         transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("| [DESC] %-" + (contentWidth - 7) + "s |%n", 
                         truncate(transaction.getDescription(), contentWidth - 7));
        System.out.printf("| [AMOUNT] $%-" + (contentWidth - 9) + ".2f |%n", 
                         transaction.getAmount());
        System.out.printf("| [CATEGORY] %-" + (contentWidth - 11) + "s |%n", 
                         truncate(transaction.getCategory(), contentWidth - 11));
        System.out.printf("| [TYPE] %-" + (contentWidth - 7) + "s |%n", 
                         truncate(transaction.getTransactionType(), contentWidth - 7));
        
        // Footer
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
    }
    
    public static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
    }
    
    public static String centerText(String text) {
        return centerText(text, STANDARD_MENU_WIDTH - 4); // Account for border characters
    }
    
    public static String createMenuBox(String title, String[] menuItems) {
        StringBuilder box = new StringBuilder();
        int maxWidth = Math.max(title.length() + 4, STANDARD_MENU_WIDTH);
        int contentWidth = maxWidth - 4; // Account for "| " and " |"
        
        // Top border
        box.append("\n+").append("=".repeat(maxWidth - 2)).append("+\n");
        
        // Title
        box.append("|").append(centerText(title, maxWidth - 2)).append("|\n");
        
        // Separator
        box.append("+").append("=".repeat(maxWidth - 2)).append("+\n");
        
        // Menu items - Fixed width calculation
        for (String item : menuItems) {
            String truncatedItem = truncate(item, contentWidth);
            box.append("| ").append(String.format("%-" + contentWidth + "s", truncatedItem)).append(" |\n");
        }
        
        // Bottom border
        box.append("+").append("=".repeat(maxWidth - 2)).append("+");
        
        return box.toString();
    }
    
    public static String createHeaderBox(String title, String subtitle) {
        int titleWidth = title.length() + 4;
        int subtitleWidth = subtitle.isEmpty() ? 0 : subtitle.length() + 4;
        int maxWidth = Math.max(Math.max(titleWidth, subtitleWidth), LARGE_MENU_WIDTH);
        
        StringBuilder box = new StringBuilder();
        
        // Top border
        box.append("+").append("=".repeat(maxWidth - 2)).append("+\n");
        
        // Title
        box.append("|").append(centerText(title, maxWidth - 2)).append("|\n");
        
        // Subtitle (only if not empty)
        if (!subtitle.isEmpty()) {
            box.append("|").append(centerText(subtitle, maxWidth - 2)).append("|\n");
        }
        
        // Bottom border
        box.append("+").append("=".repeat(maxWidth - 2)).append("+");
        
        return box.toString();
    }
    
    public static String createLargeHeaderBox(String title, String subtitle) {
        int titleWidth = title.length() + 4;
        int subtitleWidth = subtitle.isEmpty() ? 0 : subtitle.length() + 4;
        int maxWidth = Math.max(Math.max(titleWidth, subtitleWidth), LARGE_MENU_WIDTH);
        
        StringBuilder box = new StringBuilder();
        
        // Decorative top border
        box.append("+").append("=".repeat(maxWidth - 2)).append("+\n");
        box.append("|").append(" ".repeat(maxWidth - 2)).append("|\n");
        
        // Title with extra spacing
        box.append("|").append(centerText(title, maxWidth - 2)).append("|\n");
        
        // Subtitle (only if not empty)
        if (!subtitle.isEmpty()) {
            box.append("|").append(centerText(subtitle, maxWidth - 2)).append("|\n");
        }
        
        box.append("|").append(" ".repeat(maxWidth - 2)).append("|\n");
        box.append("+").append("=".repeat(maxWidth - 2)).append("+");
        
        return box.toString();
    }
    
    public static void printProgressBar(int current, int total, int width) {
        if (total == 0) return;
        
        int filled = (int) ((double) current / total * width);
        StringBuilder bar = new StringBuilder();
        bar.append("[");
        
        for (int i = 0; i < width; i++) {
            if (i < filled) {
                bar.append("=");
            } else {
                bar.append("-");
            }
        }
        
        bar.append("] ").append(current).append("/").append(total);
        System.out.println(bar.toString());
    }
    
    public static void printTableHeader(String[] headers) {
        // Top border
        System.out.print("+");
        for (String header : headers) {
            int width = Math.max(header.length() + 2, 15);
            System.out.print("-".repeat(width - 1) + "+");
        }
        System.out.println();
        
        // Headers
        System.out.print("|");
        for (String header : headers) {
            int width = Math.max(header.length() + 2, 15);
            System.out.printf(" %-" + (width - 2) + "s |", header);
        }
        System.out.println();
        
        // Separator
        System.out.print("+");
        for (String header : headers) {
            int width = Math.max(header.length() + 2, 15);
            System.out.print("-".repeat(width - 1) + "+");
        }
        System.out.println();
    }
    
    public static void printTableRow(String[] values) {
        System.out.print("|");
        for (int i = 0; i < values.length; i++) {
            int width = 15; // Default width, could be made configurable
            System.out.printf(" %-" + (width - 2) + "s |", truncate(values[i], width - 2));
        }
        System.out.println();
    }
    
    public static void printTableFooter(int columnCount) {
        System.out.print("+");
        for (int i = 0; i < columnCount; i++) {
            System.out.print("-".repeat(14) + "+");
        }
        System.out.println();
    }
    
    public static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
    
    public static void printCenteredSection(String title) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(centerText(title, 80));
        System.out.println("=".repeat(80));
    }
    
    public static void printLargeCenteredSection(String title) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println(centerText(title, 100));
        System.out.println("=".repeat(100));
    }
    
    public static void printSeparator() {
        System.out.println("-".repeat(80));
    }
    
    public static void printLargeSeparator() {
        System.out.println("=".repeat(100));
    }
    
    public static void printInfoBox(String message) {
        int width = Math.max(message.length() + 4, 60);
        System.out.println("+" + "=".repeat(width - 2) + "+");
        System.out.println("|" + centerText(message, width - 2) + "|");
        System.out.println("+" + "=".repeat(width - 2) + "+");
    }
    
    public static void printSuccessBox(String message) {
        int width = Math.max(message.length() + 4, 60);
        System.out.println("+" + "=".repeat(width - 2) + "+");
        System.out.println("|" + centerText("[SUCCESS] " + message, width - 2) + "|");
        System.out.println("+" + "=".repeat(width - 2) + "+");
    }
    
    public static void printErrorBox(String message) {
        int width = Math.max(message.length() + 4, 60);
        System.out.println("+" + "=".repeat(width - 2) + "+");
        System.out.println("|" + centerText("[ERROR] " + message, width - 2) + "|");
        System.out.println("+" + "=".repeat(width - 2) + "+");
    }
    
    public static void printWarningBox(String message) {
        int width = Math.max(message.length() + 4, 60);
        System.out.println("+" + "=".repeat(width - 2) + "+");
        System.out.println("|" + centerText("[WARNING] " + message, width - 2) + "|");
        System.out.println("+" + "=".repeat(width - 2) + "+");
    }
}