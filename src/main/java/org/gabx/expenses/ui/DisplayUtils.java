package org.gabx.expenses.ui;

import org.gabx.expenses.transactions.Transaction;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisplayUtils {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public static void showNumberedCategories(List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, categories.get(i));
        }
    }
    
    public static void displayTransactionSummary(Transaction transaction) {
        System.out.println("\n📋 Transaction Details:");
        System.out.println("═".repeat(40));
        System.out.printf("🆔 ID:          %s%n", transaction.getId());
        System.out.printf("📅 Date:        %s%n", transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("📝 Description: %s%n", transaction.getDescription());
        System.out.printf("💰 Amount:      $%,.2f%n", transaction.getAmount());
        System.out.printf("🏷️ Category:    %s%n", transaction.getCategory());
        System.out.printf("📊 Type:        %s%n", transaction.getTransactionType());
    }
    
    public static void displayTransactionDetails(Transaction transaction) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           TRANSACTION DETAILS          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ 🆔 ID:          %-22s ║%n", transaction.getId());
        System.out.printf("║ 📅 Date:        %-22s ║%n", transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("║ 📝 Description: %-22s ║%n", truncate(transaction.getDescription(), 22));
        System.out.printf("║ 💰 Amount:      $%-21.2f ║%n", transaction.getAmount());
        System.out.printf("║ 🏷️ Category:    %-22s ║%n", transaction.getCategory());
        System.out.printf("║ 📊 Type:        %-22s ║%n", transaction.getTransactionType());
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
    }
    
    public static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
