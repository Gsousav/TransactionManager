package org.gabx.expenses.ui;

import org.gabx.expenses.transactions.Transaction;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private static final int STANDARD_MENU_WIDTH = 50; // Standard width for all menus
    
    public static void showNumberedCategories(List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, categories.get(i));
        }
    }
    
    public static void displayTransactionSummary(Transaction transaction) {
        printCenteredSection("TRANSACTION DETAILS");
        System.out.printf("🆔 ID:          %s%n", transaction.getId());
        System.out.printf("📅 Date:        %s%n", transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("📝 Description: %s%n", transaction.getDescription());
        System.out.printf("💰 Amount:      $%,.2f%n", transaction.getAmount());
        System.out.printf("🏷️ Category:    %s%n", transaction.getCategory());
        System.out.printf("📊 Type:        %s%n", transaction.getTransactionType());
    }
    
    public static void displayTransactionDetails(Transaction transaction) {
        String title = "TRANSACTION DETAILS";
        int maxWidth = Math.max(title.length() + 4, STANDARD_MENU_WIDTH);
        int contentWidth = maxWidth - 4; // Account for "║ " and " ║"
        
        // Header
        System.out.println("╔" + "═".repeat(maxWidth - 2) + "╗");
        System.out.println("║" + centerText(title, maxWidth - 2) + "║");
        System.out.println("╠" + "═".repeat(maxWidth - 2) + "╣");
        
        // Content - Fixed width calculations
        System.out.printf("║ 🆔 ID:          %-" + (contentWidth - 16) + "s ║%n", 
                         truncate(transaction.getId(), contentWidth - 16));
        System.out.printf("║ 📅 Date:        %-" + (contentWidth - 16) + "s ║%n", 
                         transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("║ 📝 Description: %-" + (contentWidth - 16) + "s ║%n", 
                         truncate(transaction.getDescription(), contentWidth - 16));
        System.out.printf("║ 💰 Amount:      $%-" + (contentWidth - 17) + ".2f ║%n", 
                         transaction.getAmount());
        System.out.printf("║ 🏷️ Category:    %-" + (contentWidth - 16) + "s ║%n", 
                         truncate(transaction.getCategory(), contentWidth - 16));
        System.out.printf("║ 📊 Type:        %-" + (contentWidth - 16) + "s ║%n", 
                         truncate(transaction.getTransactionType(), contentWidth - 16));
        
        // Footer
        System.out.println("╚" + "═".repeat(maxWidth - 2) + "╝");
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
        int contentWidth = maxWidth - 4; // Account for "║ " and " ║"
        
        // Top border
        box.append("\n╔").append("═".repeat(maxWidth - 2)).append("╗\n");
        
        // Title
        box.append("║").append(centerText(title, maxWidth - 2)).append("║\n");
        
        // Separator
        box.append("╠").append("═".repeat(maxWidth - 2)).append("╣\n");
        
        // Menu items - Fixed width calculation
        for (String item : menuItems) {
            String truncatedItem = truncate(item, contentWidth);
            box.append("║ ").append(String.format("%-" + contentWidth + "s", truncatedItem)).append(" ║\n");
        }
        
        // Bottom border
        box.append("╚").append("═".repeat(maxWidth - 2)).append("╝");
        
        return box.toString();
    }
    
    public static String createHeaderBox(String title, String subtitle) {
        int titleWidth = title.length() + 4;
        int subtitleWidth = subtitle.isEmpty() ? 0 : subtitle.length() + 4;
        int maxWidth = Math.max(Math.max(titleWidth, subtitleWidth), STANDARD_MENU_WIDTH);
        
        StringBuilder box = new StringBuilder();
        
        // Top border
        box.append("╔").append("═".repeat(maxWidth - 2)).append("╗\n");
        
        // Title
        box.append("║").append(centerText(title, maxWidth - 2)).append("║\n");
        
        // Subtitle (only if not empty)
        if (!subtitle.isEmpty()) {
            box.append("║").append(centerText(subtitle, maxWidth - 2)).append("║\n");
        }
        
        // Bottom border
        box.append("╚").append("═".repeat(maxWidth - 2)).append("╝");
        
        return box.toString();
    }
    
    public static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
    
    public static void printCenteredSection(String title) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println(centerText(title, 60));
        System.out.println("═".repeat(60));
    }
}