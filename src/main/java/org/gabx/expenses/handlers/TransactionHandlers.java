package org.gabx.expenses.handlers;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.TransactionManager;
import org.gabx.expenses.ui.UserInputHandler;
import org.gabx.expenses.ui.DisplayUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionHandlers {
    private final TransactionManager transactionManager;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public TransactionHandlers(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public void addIncome() {
        System.out.println("\n[INCOME] === ADD INCOME ===");
        
        Income income = new Income();
        
        double amount = UserInputHandler.getDoubleInput("[AMOUNT] Enter income amount: $");
        income.setAmount(amount);
        
        String description = UserInputHandler.getStringInput("[DESC] Enter description: ");
        income.setDescription(description);
        
        LocalDate date = UserInputHandler.getDateInput("[DATE] Enter date (yyyy-MM-dd) or press Enter for today: ");
        income.setDate(date);
        
        System.out.println("\n[CATEGORY] Available Income Categories:");
        DisplayUtils.showNumberedCategories(income.getAvailableCategories());
        String category = UserInputHandler.getCategoryInput("[CATEGORY] Enter category (name or number): ", income.getAvailableCategories());
        income.setCategory(category);
        
        income.setId(UserInputHandler.generateId());
        transactionManager.addTransaction(income);
        
        System.out.println("[SUCCESS] Income added successfully!");
        DisplayUtils.displayTransactionSummary(income);
    }
    
    public void addExpense() {
        System.out.println("\n[EXPENSE] === ADD EXPENSE ===");
        
        Expense expense = new Expense();
        
        double amount = UserInputHandler.getDoubleInput("[AMOUNT] Enter expense amount: $");
        expense.setAmount(amount);
        
        String description = UserInputHandler.getStringInput("[DESC] Enter description: ");
        expense.setDescription(description);
        
        LocalDate date = UserInputHandler.getDateInput("[DATE] Enter date (yyyy-MM-dd) or press Enter for today: ");
        expense.setDate(date);
        
        System.out.println("\n[CATEGORY] Available Expense Categories:");
        DisplayUtils.showNumberedCategories(expense.getAvailableCategories());
        String category = UserInputHandler.getCategoryInput("[CATEGORY] Enter category (name or number): ", expense.getAvailableCategories());
        expense.setCategory(category);
        
        expense.setId(UserInputHandler.generateId());
        transactionManager.addTransaction(expense);
        
        System.out.println("[SUCCESS] Expense added successfully!");
        DisplayUtils.displayTransactionSummary(expense);
    }
    
    public void removeTransaction() {
        System.out.println("\n[ERROR] === REMOVE TRANSACTION ===");
        
        // Show recent transactions for reference
        List<Transaction> recent = transactionManager.getRecentTransactions(10);
        if (recent.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("Recent transactions:");
        for (int i = 0; i < recent.size(); i++) {
            Transaction t = recent.get(i);
            System.out.printf("%d. [%s] %s - %s: $%.2f (%s)%n", 
                i + 1, t.getId(), t.getDate().format(DISPLAY_FORMATTER), 
                t.getDescription(), t.getAmount(), t.getTransactionType());
        }
        
        String id = UserInputHandler.getStringInput("\n[FIND] Enter transaction ID to remove: ");
        
        Transaction transaction = transactionManager.findTransactionById(id);
        if (transaction == null) {
            System.out.println("[ERROR] Transaction not found.");
            return;
        }
        
        System.out.println("[SUCCESS] Transaction found:");
        DisplayUtils.displayTransactionDetails(transaction);
        
        // Ask for confirmation before deletion
        String confirm = UserInputHandler.getStringInput("\n[WARNING] Are you sure you want to delete this transaction? (yes/no): ");
        if ("yes".equalsIgnoreCase(confirm)) {
            transactionManager.removeTransaction(id);
            System.out.println("[SUCCESS] Transaction removed successfully!");
        } else {
            System.out.println("[CANCELLED] Transaction deletion cancelled.");
        }
    }
    
    public void findTransaction() {
        System.out.println("\n[FIND] === FIND TRANSACTION ===");
        
        String id = UserInputHandler.getStringInput("[FIND] Enter transaction ID: ");
        
        Transaction transaction = transactionManager.findTransactionById(id);
        if (transaction == null) {
            System.out.println("[ERROR] Transaction not found.");
            return;
        }
        
        System.out.println("[SUCCESS] Transaction found:");
        DisplayUtils.displayTransactionDetails(transaction);
    }
}
