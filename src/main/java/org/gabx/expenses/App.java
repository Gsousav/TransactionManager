package org.gabx.expenses;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.TransactionManager;
import java.util.Scanner;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static void main(String[] args) {
        System.out.println("=== Personal Expense Tracker ===");
        System.out.println("Welcome to your expense tracking system!\n");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewMonthlySummary();
                    break;
                case 4:
                    viewIncomeByCategory();
                    break;
                case 5:
                    viewExpensesByCategory();
                    break;
                case 6:
                    addNewCategory();
                    break;
                case 7:
                    System.out.println("Thank you for using Expense Tracker!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Monthly Summary");
        System.out.println("4. View Income by Category");
        System.out.println("5. View Expenses by Category");
        System.out.println("6. Add New Category");
        System.out.println("7. Exit");
        System.out.println("==================");
    }
    
    private static void addIncome() {
        System.out.println("\n=== ADD INCOME ===");
        
        Income income = new Income();
        
        // Set amount
        double amount = getDoubleInput("Enter income amount: $");
        income.setAmount(amount);
        
        // Set description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        income.setDescription(description);
        
        // Set date
        LocalDate date = getDateInput("Enter date (yyyy-MM-dd) or press Enter for today: ");
        income.setDate(date);
        
        // Set category
        System.out.println("\nAvailable Income Categories:");
        showCategories(income.getAvailableCategories());
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        income.setCategory(category);
        
        // Generate ID
        income.setId(generateId());
        
        transactionManager.addTransaction(income);
        System.out.println("Income added successfully!\n");
    }
    
    private static void addExpense() {
        System.out.println("\n=== ADD EXPENSE ===");
        
        Expense expense = new Expense();
        
        // Set amount
        double amount = getDoubleInput("Enter expense amount: $");
        expense.setAmount(amount);
        
        // Set description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        expense.setDescription(description);
        
        // Set date
        LocalDate date = getDateInput("Enter date (yyyy-MM-dd) or press Enter for today: ");
        expense.setDate(date);
        
        // Set category
        System.out.println("\nAvailable Expense Categories:");
        showCategories(expense.getAvailableCategories());
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        expense.setCategory(category);
        
        // Generate ID
        expense.setId(generateId());
        
        transactionManager.addTransaction(expense);
        System.out.println("Expense added successfully!\n");
    }
    
    private static void viewMonthlySummary() {
        System.out.println("\n=== MONTHLY SUMMARY ===");
        
        int month = getIntInput("Enter month (1-12): ");
        int year = getIntInput("Enter year: ");
        
        if (month < 1 || month > 12) {
            System.out.println("Invalid month. Please enter a value between 1 and 12.\n");
            return;
        }
        
        double totalIncome = transactionManager.getTotalIncome(month, year);
        double totalExpenses = transactionManager.getTotalExpenses(month, year);
        double balance = totalIncome - totalExpenses;
        
        System.out.println("\n--- Summary for " + month + "/" + year + " ---");
        System.out.printf("Total Income:  $%.2f%n", totalIncome);
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);
        System.out.println("------------------------");
        System.out.printf("Balance:       $%.2f%n", balance);
        
        if (balance > 0) {
            System.out.println("✓ You saved money this month!");
        } else if (balance < 0) {
            System.out.println("⚠ You overspent this month!");
        } else {
            System.out.println("= You broke even this month.");
        }
        System.out.println();
    }
    
    private static void viewIncomeByCategory() {
        System.out.println("\n=== INCOME BY CATEGORY ===");
        
        int month = getIntInput("Enter month (1-12): ");
        int year = getIntInput("Enter year: ");
        
        Map<String, Double> incomeByCategory = transactionManager.getIncomeByCategory(month, year);
        
        if (incomeByCategory.isEmpty()) {
            System.out.println("No income recorded for " + month + "/" + year + "\n");
            return;
        }
        
        System.out.println("\n--- Income Categories for " + month + "/" + year + " ---");
        for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
            System.out.printf("%-15s: $%.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }
    
    private static void viewExpensesByCategory() {
        System.out.println("\n=== EXPENSES BY CATEGORY ===");
        
        int month = getIntInput("Enter month (1-12): ");
        int year = getIntInput("Enter year: ");
        
        Map<String, Double> expensesByCategory = transactionManager.getExpensesByCategory(month, year);
        
        if (expensesByCategory.isEmpty()) {
            System.out.println("No expenses recorded for " + month + "/" + year + "\n");
            return;
        }
        
        System.out.println("\n--- Expense Categories for " + month + "/" + year + " ---");
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            System.out.printf("%-15s: $%.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }
    
    private static void addNewCategory() {
        System.out.println("\n=== ADD NEW CATEGORY ===");
        System.out.println("1. Add Income Category");
        System.out.println("2. Add Expense Category");
        
        int choice = getIntInput("Enter choice: ");
        System.out.print("Enter new category name: ");
        String categoryName = scanner.nextLine();
        
        switch (choice) {
            case 1:
                Income.addIncomeCategory(categoryName);
                System.out.println("Income category '" + categoryName + "' added successfully!\n");
                break;
            case 2:
                Expense.addExpenseCategory(categoryName);
                System.out.println("Expense category '" + categoryName + "' added successfully!\n");
                break;
            default:
                System.out.println("Invalid choice.\n");
        }
    }
    
    // Helper Methods
    private static void showCategories(java.util.List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, categories.get(i));
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
    
    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    return LocalDate.now(); // Default to today
                }
                
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in format yyyy-MM-dd (e.g., 2024-01-15)");
            }
        }
    }
    
    private static String generateId() {
        return "TXN_" + System.currentTimeMillis();
    }
}
