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
        System.out.println("\n🏷️ === ADD NEW CATEGORY ===");
        
        System.out.println("1. 💰 Add Income Category");
        System.out.println("2. 💸 Add Expense Category");
        
        int choice = UserInputHandler.getIntInput("➤ Enter choice: ");
        String categoryName = UserInputHandler.getStringInput("🏷️ Enter new category name: ");
        
        if (categoryName.isEmpty()) {
            System.out.println("❌ Category name cannot be empty.");
            return;
        }
        
        switch (choice) {
            case 1:
                Income.addIncomeCategory(categoryName);
                System.out.printf("✅ Income category '%s' added successfully!%n", categoryName);
                break;
            case 2:
                Expense.addExpenseCategory(categoryName);
                System.out.printf("✅ Expense category '%s' added successfully!%n", categoryName);
                break;
            default:
                System.out.println("❌ Invalid choice.");
        }
    }
    
    public void createBackup() {
        System.out.println("\n💾 === CREATE BACKUP ===");
        
        transactionManager.createBackup();
        System.out.println("✅ Backup created successfully!");
    }
    
    public void showDataLocation() {
        System.out.println("\n📁 === DATA LOCATION ===");
        System.out.println("📁 Your data is stored at:");
        System.out.println("   " + transactionManager.getStorageLocation());
        System.out.println("\n💡 This location contains:");
        System.out.println("   📄 transactions.json - All your transaction data");
        System.out.println("   📄 categories.json - Your custom categories");
        System.out.println("   📁 backups/ - Backup files");
    }
    
    public void printTransactionSummary() {
        transactionManager.printTransactionSummary();
    }
}
