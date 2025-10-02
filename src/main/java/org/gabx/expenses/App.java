package org.gabx.expenses;

import org.gabx.expenses.manager.TransactionManager;
import org.gabx.expenses.manager.RecurringExpenseManager;
import org.gabx.expenses.ui.MenuSystem;
import org.gabx.expenses.ui.UserInputHandler;
import org.gabx.expenses.handlers.TransactionHandlers;
import org.gabx.expenses.handlers.ViewHandlers;
import org.gabx.expenses.handlers.ManagementHandlers;
import org.gabx.expenses.handlers.RecurringExpenseHandlers;

public class App {
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final RecurringExpenseManager recurringExpenseManager = new RecurringExpenseManager(transactionManager);
    private static final TransactionHandlers transactionHandlers = new TransactionHandlers(transactionManager);
    private static final ViewHandlers viewHandlers = new ViewHandlers(transactionManager);
    private static final ManagementHandlers managementHandlers = new ManagementHandlers(transactionManager);
    private static final RecurringExpenseHandlers recurringExpenseHandlers = new RecurringExpenseHandlers(recurringExpenseManager);
    
    public static void main(String[] args) {
        MenuSystem.displayAppHeader();
        
        // Show startup info
        transactionManager.printTransactionSummary();
        recurringExpenseManager.printRecurringExpenseSummary();
        
        // Process overdue recurring expenses on startup
        processOverdueOnStartup();
        
        while (true) {
            MenuSystem.displayMainMenu();
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    transactionSubmenu();
                    break;
                case 2:
                    viewingSubmenu();
                    break;
                case 3:
                    reportsSubmenu();
                    break;
                case 4:
                    managementSubmenu();
                    break;
                case 5:
                    System.out.println("\n[SAVE] All data has been automatically saved!");
                    System.out.println("[LOCATION] Data location: " + transactionManager.getStorageLocation());
                    System.out.println("Thank you for using Transaction Tracker!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.\n");
            }
        }
    }
    
    
    private static void transactionSubmenu() {
        while (true) {
            MenuSystem.displayTransactionMenu();
            
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    transactionHandlers.addIncome();
                    break;
                case 2:
                    transactionHandlers.addExpense();
                    break;
                case 3:
                    recurringExpenseHandlers.addRecurringExpense();
                    break;
                case 4:
                    transactionHandlers.removeTransaction();
                    break;
                case 5:
                    transactionHandlers.findTransaction();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewingSubmenu() {
        while (true) {
            MenuSystem.displayViewingMenu();
            
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewHandlers.viewMonthlySummary();
                    break;
                case 2:
                    viewHandlers.viewIncomeByCategory();
                    break;
                case 3:
                    viewHandlers.viewExpensesByCategory();
                    break;
                case 4:
                    viewHandlers.viewRecentTransactions();
                    break;
                case 5:
                    viewHandlers.viewTransactionsByDateRange();
                    break;
                case 6:
                    viewHandlers.viewYearlyOverview();
                    break;
                case 7:
                    recurringExpenseSubmenu();
                    break;
                case 8:
                    viewHandlers.viewLifetimeBalance();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.");
            }
        }
    }
    
    private static void reportsSubmenu() {
        while (true) {
            MenuSystem.displayReportsMenu();
            
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewHandlers.monthlyTrendAnalysis();
                    break;
                case 2:
                    viewHandlers.categorySpendingAnalysis();
                    break;
                case 3:
                    viewHandlers.yearToDateSummary();
                    break;
                case 4:
                    viewHandlers.spendingInsights();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.");
            }
        }
    }
    
    private static void managementSubmenu() {
        while (true) {
            MenuSystem.displayManagementMenu();
            
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    managementHandlers.addNewCategory();
                    break;
                case 2:
                    managementHandlers.createBackup();
                    break;
                case 3:
                    managementHandlers.showDataLocation();
                    break;
                case 4:
                    managementHandlers.printTransactionSummary();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.");
            }
        }
    }
    
    private static void recurringExpenseSubmenu() {
        while (true) {
            MenuSystem.displayRecurringExpenseMenu();
            
            int choice = UserInputHandler.getIntInput(">> Enter your choice: ");
            
            switch (choice) {
                case 1:
                    recurringExpenseHandlers.addRecurringExpense();
                    break;
                case 2:
                    recurringExpenseHandlers.viewRecurringExpenses();
                    break;
                case 3:
                    recurringExpenseHandlers.viewActiveRecurringExpenses();
                    break;
                case 4:
                    recurringExpenseHandlers.viewUpcomingRecurringExpenses();
                    break;
                case 5:
                    recurringExpenseHandlers.removeRecurringExpense();
                    break;
                case 6:
                    recurringExpenseHandlers.toggleRecurringExpenseStatus();
                    break;
                case 7:
                    recurringExpenseHandlers.processRecurringExpenses();
                    break;
                case 8:
                    recurringExpenseHandlers.processOverdueRecurringExpenses();
                    break;
                case 9:
                    recurringExpenseHandlers.recurringExpenseSummary();
                    break;
                case 10:
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Please try again.");
            }
        }
    }
    
    private static void processOverdueOnStartup() {
        // Check if there are any overdue recurring expenses that haven't been processed yet
        java.time.LocalDate today = java.time.LocalDate.now();
        var overdueExpenses = recurringExpenseManager.getRecurringExpensesDueBetween(
            java.time.LocalDate.of(2020, 1, 1), today.minusDays(1));
        
        if (!overdueExpenses.isEmpty()) {
            // Check if any of these overdue expenses actually need processing
            boolean hasUnprocessedExpenses = overdueExpenses.stream()
                .anyMatch(re -> {
                    // Check if there are any periods between nextDueDate and today that haven't been processed
                    java.time.LocalDate current = re.getNextDueDate();
                    while (!current.isAfter(today)) {
                        if (!hasExpenseForRecurringAndDate(re.getId(), current)) {
                            return true; // Found an unprocessed period
                        }
                        current = getNextDateForFrequency(current, re.getFrequency());
                    }
                    return false;
                });
            
            if (hasUnprocessedExpenses) {
                System.out.println("\n[WARNING] Found overdue recurring expense(s) that haven't been processed yet.");
                System.out.println("Would you like to process them now? (This will generate regular expense transactions)");
                
                String choice = UserInputHandler.getStringInput("Process overdue expenses? (yes/no, default=yes): ");
                if (!"no".equalsIgnoreCase(choice)) {
                    var generatedExpenses = recurringExpenseManager.processOverdueRecurringExpenses();
                    if (!generatedExpenses.isEmpty()) {
                        System.out.printf("[SUCCESS] Generated %d expense transactions from overdue recurring expenses.%n", 
                            generatedExpenses.size());
                    } else {
                        System.out.println("[INFO] All overdue recurring expenses have already been processed.");
                    }
                }
            }
        }
    }
    
    // Helper method to check if an expense exists for a recurring expense and date
    private static boolean hasExpenseForRecurringAndDate(String recurringExpenseId, java.time.LocalDate date) {
        return transactionManager.getAllTransactions().stream()
            .filter(t -> t instanceof org.gabx.expenses.transactions.Expense)
            .map(t -> (org.gabx.expenses.transactions.Expense) t)
            .anyMatch(e -> recurringExpenseId.equals(e.getOriginalRecurringId()) && date.equals(e.getDate()));
    }
    
    // Helper method to get next date for frequency
    private static java.time.LocalDate getNextDateForFrequency(java.time.LocalDate date, org.gabx.expenses.transactions.RecurringExpense.Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
            case FOUR_WEEKLY:
                return date.plusWeeks(4);
            case MONTHLY:
                return date.plusMonths(1);
            case QUARTERLY:
                return date.plusMonths(3);
            case YEARLY:
                return date.plusYears(1);
            default:
                return date;
        }
    }
}
