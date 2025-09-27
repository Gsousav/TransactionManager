package org.gabx.expenses.handlers;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.RecurringExpenseManager;
import org.gabx.expenses.ui.UserInputHandler;
import org.gabx.expenses.ui.DisplayUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class RecurringExpenseHandlers {
    private final RecurringExpenseManager recurringExpenseManager;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public RecurringExpenseHandlers(RecurringExpenseManager recurringExpenseManager) {
        this.recurringExpenseManager = recurringExpenseManager;
    }
    
    public void addRecurringExpense() {
        System.out.println("\n🔄 === ADD RECURRING EXPENSE ===");
        
        RecurringExpense recurringExpense = new RecurringExpense();
        
        double amount = UserInputHandler.getDoubleInput("💵 Enter expense amount: $");
        recurringExpense.setAmount(amount);
        
        String description = UserInputHandler.getStringInput("📝 Enter description: ");
        recurringExpense.setDescription(description);
        
        LocalDate startDate = UserInputHandler.getDateInput("📅 Enter start date (yyyy-MM-dd) or press Enter for today: ");
        recurringExpense.setStartDate(startDate);
        recurringExpense.setDate(startDate);
        
        System.out.println("\n🏷️ Available Expense Categories:");
        DisplayUtils.showNumberedCategories(recurringExpense.getAvailableCategories());
        String category = UserInputHandler.getCategoryInput("🏷️ Enter category (name or number): ", recurringExpense.getAvailableCategories());
        recurringExpense.setCategory(category);
        
        // Frequency selection
        System.out.println("\n🔄 Available Frequencies:");
        List<RecurringExpense.Frequency> frequencies = RecurringExpense.getAvailableFrequencies();
        for (int i = 0; i < frequencies.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, frequencies.get(i).getDisplayName());
        }
        
        int freqChoice = UserInputHandler.getIntInput("🔄 Select frequency (1-" + frequencies.size() + "): ");
        if (freqChoice < 1 || freqChoice > frequencies.size()) {
            System.out.println("❌ Invalid frequency choice. Setting to Monthly.");
            freqChoice = 3; // Default to Monthly
        }
        recurringExpense.setFrequency(frequencies.get(freqChoice - 1));
        
        recurringExpense.setId(UserInputHandler.generateId());
        recurringExpenseManager.addRecurringExpense(recurringExpense);
        
        System.out.println("✅ Recurring expense added successfully!");
        displayRecurringExpenseSummary(recurringExpense);
    }
    
    public void viewRecurringExpenses() {
        System.out.println("\n🔄 === RECURRING EXPENSES ===");
        
        List<RecurringExpense> recurringExpenses = recurringExpenseManager.getAllRecurringExpenses();
        
        if (recurringExpenses.isEmpty()) {
            System.out.println("📭 No recurring expenses found.");
            return;
        }
        
        System.out.printf("%n🔄 All Recurring Expenses (%d total):%n", recurringExpenses.size());
        System.out.println("═".repeat(90));
        System.out.printf("%-12s %-25s %-15s %-12s %-12s %-8s%n", "ID", "Description", "Amount", "Frequency", "Next Due", "Status");
        System.out.println("═".repeat(90));
        
        for (RecurringExpense re : recurringExpenses) {
            String status = re.isActive() ? "✅ Active" : "❌ Inactive";
            System.out.printf("%-12s %-25s $%,10.2f %-12s %-12s %-8s%n",
                re.getId().substring(0, Math.min(12, re.getId().length())),
                DisplayUtils.truncate(re.getDescription(), 25),
                re.getAmount(),
                re.getFrequency().getDisplayName(),
                re.getNextDueDate().format(DateTimeFormatter.ofPattern("MMM dd")),
                status);
        }
    }
    
    public void viewActiveRecurringExpenses() {
        System.out.println("\n✅ === ACTIVE RECURRING EXPENSES ===");
        
        List<RecurringExpense> activeExpenses = recurringExpenseManager.getActiveRecurringExpenses();
        
        if (activeExpenses.isEmpty()) {
            System.out.println("📭 No active recurring expenses found.");
            return;
        }
        
        System.out.printf("%n✅ Active Recurring Expenses (%d total):%n", activeExpenses.size());
        System.out.println("═".repeat(90));
        System.out.printf("%-12s %-25s %-15s %-12s %-12s%n", "ID", "Description", "Amount", "Frequency", "Next Due");
        System.out.println("═".repeat(90));
        
        for (RecurringExpense re : activeExpenses) {
            System.out.printf("%-12s %-25s $%,10.2f %-12s %-12s%n",
                re.getId().substring(0, Math.min(12, re.getId().length())),
                DisplayUtils.truncate(re.getDescription(), 25),
                re.getAmount(),
                re.getFrequency().getDisplayName(),
                re.getNextDueDate().format(DateTimeFormatter.ofPattern("MMM dd")));
        }
    }
    
    public void viewUpcomingRecurringExpenses() {
        System.out.println("\n📅 === UPCOMING RECURRING EXPENSES ===");
        
        int daysAhead = UserInputHandler.getIntInput("📅 How many days ahead to show? (default 30): ");
        if (daysAhead <= 0) daysAhead = 30;
        
        List<RecurringExpense> upcoming = recurringExpenseManager.getUpcomingRecurringExpenses(daysAhead);
        
        if (upcoming.isEmpty()) {
            System.out.printf("📭 No recurring expenses due in the next %d days.%n", daysAhead);
            return;
        }
        
        System.out.printf("%n📅 Recurring Expenses Due in Next %d Days (%d total):%n", daysAhead, upcoming.size());
        System.out.println("═".repeat(90));
        System.out.printf("%-12s %-25s %-15s %-12s %-12s%n", "ID", "Description", "Amount", "Frequency", "Due Date");
        System.out.println("═".repeat(90));
        
        for (RecurringExpense re : upcoming) {
            System.out.printf("%-12s %-25s $%,10.2f %-12s %-12s%n",
                re.getId().substring(0, Math.min(12, re.getId().length())),
                DisplayUtils.truncate(re.getDescription(), 25),
                re.getAmount(),
                re.getFrequency().getDisplayName(),
                re.getNextDueDate().format(DateTimeFormatter.ofPattern("MMM dd")));
        }
        
        double totalUpcoming = upcoming.stream().mapToDouble(RecurringExpense::getAmount).sum();
        System.out.println("═".repeat(90));
        System.out.printf("💸 Total Amount Due: $%,15.2f%n", totalUpcoming);
    }
    
    public void removeRecurringExpense() {
        System.out.println("\n❌ === REMOVE RECURRING EXPENSE ===");
        
        List<RecurringExpense> recurringExpenses = recurringExpenseManager.getAllRecurringExpenses();
        if (recurringExpenses.isEmpty()) {
            System.out.println("📭 No recurring expenses found.");
            return;
        }
        
        System.out.println("Current recurring expenses:");
        for (int i = 0; i < recurringExpenses.size(); i++) {
            RecurringExpense re = recurringExpenses.get(i);
            String status = re.isActive() ? "✅" : "❌";
            System.out.printf("%d. [%s] %s - %s: $%.2f (%s) - Due: %s%n", 
                i + 1, re.getId().substring(0, Math.min(8, re.getId().length())), 
                status, re.getDescription(), re.getAmount(), 
                re.getFrequency().getDisplayName(), 
                re.getNextDueDate().format(DISPLAY_FORMATTER));
        }
        
        String id = UserInputHandler.getStringInput("\n🔍 Enter recurring expense ID to remove: ");
        
        RecurringExpense recurringExpense = recurringExpenseManager.findRecurringExpenseById(id);
        if (recurringExpense == null) {
            System.out.println("❌ Recurring expense not found.");
            return;
        }
        
        System.out.println("✅ Recurring expense found:");
        displayRecurringExpenseSummary(recurringExpense);
        
        String confirm = UserInputHandler.getStringInput("⚠️ Are you sure you want to remove this recurring expense? (yes/no): ");
        if ("yes".equalsIgnoreCase(confirm)) {
            recurringExpenseManager.removeRecurringExpense(id);
            System.out.println("✅ Recurring expense removed successfully!");
        } else {
            System.out.println("❌ Operation cancelled.");
        }
    }
    
    public void toggleRecurringExpenseStatus() {
        System.out.println("\n🔄 === TOGGLE RECURRING EXPENSE STATUS ===");
        
        List<RecurringExpense> recurringExpenses = recurringExpenseManager.getAllRecurringExpenses();
        if (recurringExpenses.isEmpty()) {
            System.out.println("📭 No recurring expenses found.");
            return;
        }
        
        System.out.println("Current recurring expenses:");
        for (int i = 0; i < recurringExpenses.size(); i++) {
            RecurringExpense re = recurringExpenses.get(i);
            String status = re.isActive() ? "✅ Active" : "❌ Inactive";
            System.out.printf("%d. [%s] %s - %s: $%.2f (%s) - %s%n", 
                i + 1, re.getId().substring(0, Math.min(8, re.getId().length())), 
                status, re.getDescription(), re.getAmount(), 
                re.getFrequency().getDisplayName(), status);
        }
        
        String id = UserInputHandler.getStringInput("\n🔍 Enter recurring expense ID to toggle: ");
        
        RecurringExpense recurringExpense = recurringExpenseManager.findRecurringExpenseById(id);
        if (recurringExpense == null) {
            System.out.println("❌ Recurring expense not found.");
            return;
        }
        
        String oldStatus = recurringExpense.isActive() ? "Active" : "Inactive";
        recurringExpenseManager.toggleRecurringExpenseStatus(id);
        String newStatus = !recurringExpense.isActive() ? "Active" : "Inactive";
        
        System.out.printf("✅ Recurring expense status changed from %s to %s!%n", oldStatus, newStatus);
        displayRecurringExpenseSummary(recurringExpense);
    }
    
    public void processRecurringExpenses() {
        System.out.println("\n⚡ === PROCESS RECURRING EXPENSES ===");
        
        System.out.println("This will generate regular expense transactions from recurring expenses that are due.");
        
        LocalDate processDate = UserInputHandler.getDateInput("📅 Enter date to process (yyyy-MM-dd) or press Enter for today: ");
        
        List<Expense> generatedExpenses = recurringExpenseManager.processRecurringExpenses(processDate);
        
        if (generatedExpenses.isEmpty()) {
            System.out.println("📭 No recurring expenses were due on " + processDate.format(DISPLAY_FORMATTER));
            return;
        }
        
        System.out.printf("✅ Generated %d expense transactions for %s:%n", 
            generatedExpenses.size(), processDate.format(DISPLAY_FORMATTER));
        System.out.println("═".repeat(70));
        
        for (Expense expense : generatedExpenses) {
            System.out.printf("💸 %s - $%,.2f [%s]%n", 
                expense.getDescription(), expense.getAmount(), expense.getCategory());
        }
        
        double total = generatedExpenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.println("═".repeat(70));
        System.out.printf("💸 Total Generated: $%,15.2f%n", total);
    }
    
    public void processOverdueRecurringExpenses() {
        System.out.println("\n⚠️ === PROCESS OVERDUE RECURRING EXPENSES ===");
        
        System.out.println("This will generate regular expense transactions for all overdue recurring expenses.");
        
        String confirm = UserInputHandler.getStringInput("⚠️ This may generate many transactions. Continue? (yes/no): ");
        if (!"yes".equalsIgnoreCase(confirm)) {
            System.out.println("❌ Operation cancelled.");
            return;
        }
        
        List<Expense> generatedExpenses = recurringExpenseManager.processOverdueRecurringExpenses();
        
        if (generatedExpenses.isEmpty()) {
            System.out.println("📭 No overdue recurring expenses found.");
            return;
        }
        
        System.out.printf("✅ Generated %d expense transactions for overdue recurring expenses:%n", 
            generatedExpenses.size());
        System.out.println("═".repeat(70));
        
        for (Expense expense : generatedExpenses) {
            System.out.printf("💸 %s - $%,.2f [%s] - %s%n", 
                expense.getDescription(), expense.getAmount(), expense.getCategory(),
                expense.getDate().format(DISPLAY_FORMATTER));
        }
        
        double total = generatedExpenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.println("═".repeat(70));
        System.out.printf("💸 Total Generated: $%,15.2f%n", total);
    }
    
    public void recurringExpenseSummary() {
        System.out.println("\n📊 === RECURRING EXPENSE SUMMARY ===");
        
        recurringExpenseManager.printRecurringExpenseSummary();
        
        // Show breakdown by category
        Map<String, Double> byCategory = recurringExpenseManager.getRecurringExpensesByCategory();
        if (!byCategory.isEmpty()) {
            System.out.println("🏷️ Recurring Expenses by Category:");
            System.out.println("═".repeat(40));
            byCategory.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.printf("💸 %-20s $%,10.2f%n", entry.getKey(), entry.getValue());
                });
        }
        
        // Show breakdown by frequency
        Map<RecurringExpense.Frequency, Double> byFrequency = recurringExpenseManager.getRecurringExpensesByFrequency();
        if (!byFrequency.isEmpty()) {
            System.out.println("\n🔄 Recurring Expenses by Frequency:");
            System.out.println("═".repeat(40));
            byFrequency.entrySet().stream()
                .sorted(Map.Entry.<RecurringExpense.Frequency, Double>comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.printf("🔄 %-15s $%,10.2f%n", entry.getKey().getDisplayName(), entry.getValue());
                });
        }
    }
    
    private void displayRecurringExpenseSummary(RecurringExpense recurringExpense) {
        System.out.println("\n📋 Recurring Expense Summary:");
        System.out.println("═".repeat(50));
        System.out.printf("🆔 ID: %s%n", recurringExpense.getId());
        System.out.printf("📝 Description: %s%n", recurringExpense.getDescription());
        System.out.printf("💵 Amount: $%,.2f%n", recurringExpense.getAmount());
        System.out.printf("🏷️ Category: %s%n", recurringExpense.getCategory());
        System.out.printf("🔄 Frequency: %s%n", recurringExpense.getFrequency().getDisplayName());
        System.out.printf("📅 Start Date: %s%n", recurringExpense.getStartDate().format(DISPLAY_FORMATTER));
        System.out.printf("📅 Next Due: %s%n", recurringExpense.getNextDueDate().format(DISPLAY_FORMATTER));
        System.out.printf("✅ Status: %s%n", recurringExpense.isActive() ? "Active" : "Inactive");
    }
}
