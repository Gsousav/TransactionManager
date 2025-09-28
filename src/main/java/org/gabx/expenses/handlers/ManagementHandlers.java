package org.gabx.expenses.handlers;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.TransactionManager;
import org.gabx.expenses.ui.UserInputHandler;

public class ManagementHandlers {
    private final TransactionManager transactionManager;
    
    public ManagementHandlers(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public void addNewCategory() {
        System.out.println("\n[CATEGORY] === ADD NEW CATEGORY ===");
        
        System.out.println("1. [INCOME] Add Income Category");
        System.out.println("2. [EXPENSE] Add Expense Category");
        
        int choice = UserInputHandler.getIntInput(">> Enter choice: ");
        String categoryName = UserInputHandler.getStringInput("[CATEGORY] Enter new category name: ");
        
        if (categoryName.isEmpty()) {
            System.out.println("[ERROR] Category name cannot be empty.");
            return;
        }
        
        switch (choice) {
            case 1:
                Income.addIncomeCategory(categoryName);
                System.out.printf("[SUCCESS] Income category '%s' added successfully!%n", categoryName);
                break;
            case 2:
                Expense.addExpenseCategory(categoryName);
                System.out.printf("[SUCCESS] Expense category '%s' added successfully!%n", categoryName);
                break;
            default:
                System.out.println("[ERROR] Invalid choice.");
        }
    }
    
    public void createBackup() {
        System.out.println("\n[BACKUP] === CREATE BACKUP ===");
        
        transactionManager.createBackup();
        System.out.println("[SUCCESS] Backup created successfully!");
    }
    
    public void showDataLocation() {
        System.out.println("\n[LOCATION] === DATA LOCATION ===");
        System.out.println("[LOCATION] Your data is stored at:");
        System.out.println("   " + transactionManager.getStorageLocation());
        System.out.println("\n[INFO] This location contains:");
        System.out.println("   [FILE] transactions.json - All your transaction data");
        System.out.println("   [FILE] categories.json - Your custom categories");
        System.out.println("   [LOCATION] backups/ - Backup files");
    }
    
    public void printTransactionSummary() {
        transactionManager.printTransactionSummary();
    }
}
