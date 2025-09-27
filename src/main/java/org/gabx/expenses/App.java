package org.gabx.expenses;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.TransactionManager;
import java.util.Scanner;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     Personal Transaction Tracker      ║");
        System.out.println("║        Your Financial Command Center   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        // Show startup info
        transactionManager.printTransactionSummary();
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("➤ Enter your choice: ");
            
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
                    System.out.println("\n💾 All data has been automatically saved!");
                    System.out.println("📁 Data location: " + transactionManager.getStorageLocation());
                    System.out.println("Thank you for using Transaction Tracker! 👋");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.\n");
            }
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n╔═══════════════ MAIN MENU ═══════════════╗");
        System.out.println("║ 1. 📝 Add Transactions                  ║");
        System.out.println("║ 2. 👀 View Transactions                 ║");
        System.out.println("║ 3. 📊 Reports & Analysis                ║");
        System.out.println("║ 4. ⚙️  Management & Settings            ║");
        System.out.println("║ 5. 🚪 Exit                               ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    private static void transactionSubmenu() {
        while (true) {
            System.out.println("\n╔═══════════ TRANSACTION MENU ════════════╗");
            System.out.println("║ 1. 💰 Add Income                        ║");
            System.out.println("║ 2. 💸 Add Expense                       ║");
            System.out.println("║ 3. ❌ Remove Transaction                ║");
            System.out.println("║ 4. 🔍 Find Transaction by ID            ║");
            System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
            System.out.println("╚════════════════════════════════════════╝");
            
            int choice = getIntInput("➤ Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    removeTransaction();
                    break;
                case 4:
                    findTransaction();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
    
    private static void viewingSubmenu() {
        while (true) {
            System.out.println("\n╔═════════════ VIEWING MENU ══════════════╗");
            System.out.println("║ 1. 📅 Monthly Summary                   ║");
            System.out.println("║ 2. 📊 Income by Category                ║");
            System.out.println("║ 3. 📊 Expenses by Category              ║");
            System.out.println("║ 4. 📋 Recent Transactions               ║");
            System.out.println("║ 5. 📆 Transactions by Date Range        ║");
            System.out.println("║ 6. 📈 Yearly Overview                   ║");
            System.out.println("║ 7. ⬅️  Back to Main Menu                ║");
            System.out.println("╚════════════════════════════════════════╝");
            
            int choice = getIntInput("➤ Enter your choice: ");
            
            switch (choice) {
                case 1:
                    viewMonthlySummary();
                    break;
                case 2:
                    viewIncomeByCategory();
                    break;
                case 3:
                    viewExpensesByCategory();
                    break;
                case 4:
                    viewRecentTransactions();
                    break;
                case 5:
                    viewTransactionsByDateRange();
                    break;
                case 6:
                    viewYearlyOverview();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
    
    private static void reportsSubmenu() {
        while (true) {
            System.out.println("\n╔═════════════ REPORTS MENU ══════════════╗");
            System.out.println("║ 1. 📈 Monthly Trend Analysis            ║");
            System.out.println("║ 2. 💹 Category Spending Analysis        ║");
            System.out.println("║ 3. 📊 Year-to-Date Summary               ║");
            System.out.println("║ 4. 💡 Spending Insights                 ║");
            System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
            System.out.println("╚════════════════════════════════════════╝");
            
            int choice = getIntInput("➤ Enter your choice: ");
            
            switch (choice) {
                case 1:
                    monthlyTrendAnalysis();
                    break;
                case 2:
                    categorySpendingAnalysis();
                    break;
                case 3:
                    yearToDateSummary();
                    break;
                case 4:
                    spendingInsights();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
    
    private static void managementSubmenu() {
        while (true) {
            System.out.println("\n╔═══════════ MANAGEMENT MENU ═════════════╗");
            System.out.println("║ 1. 🏷️  Add New Category                 ║");
            System.out.println("║ 2. 💾 Create Backup                     ║");
            System.out.println("║ 3. 📁 Show Data Location                ║");
            System.out.println("║ 4. 📊 Transaction Summary               ║");
            System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
            System.out.println("╚════════════════════════════════════════╝");
            
            int choice = getIntInput("➤ Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewCategory();
                    break;
                case 2:
                    createBackup();
                    break;
                case 3:
                    showDataLocation();
                    break;
                case 4:
                    transactionManager.printTransactionSummary();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addIncome() {
        System.out.println("\n💰 === ADD INCOME ===");
        
        Income income = new Income();
        
        double amount = getDoubleInput("💵 Enter income amount: $");
        income.setAmount(amount);
        
        System.out.print("📝 Enter description: ");
        String description = scanner.nextLine();
        income.setDescription(description);
        
        LocalDate date = getDateInput("📅 Enter date (yyyy-MM-dd) or press Enter for today: ");
        income.setDate(date);
        
        System.out.println("\n🏷️ Available Income Categories:");
        showNumberedCategories(income.getAvailableCategories());
        String category = getCategoryInput("🏷️ Enter category (name or number): ", income.getAvailableCategories());
        income.setCategory(category);
        
        income.setId(generateId());
        transactionManager.addTransaction(income);
        
        System.out.println("✅ Income added successfully!");
        displayTransactionSummary(income);
    }
    
    private static void addExpense() {
        System.out.println("\n💸 === ADD EXPENSE ===");
        
        Expense expense = new Expense();
        
        double amount = getDoubleInput("💵 Enter expense amount: $");
        expense.setAmount(amount);
        
        System.out.print("📝 Enter description: ");
        String description = scanner.nextLine();
        expense.setDescription(description);
        
        LocalDate date = getDateInput("📅 Enter date (yyyy-MM-dd) or press Enter for today: ");
        expense.setDate(date);
        
        System.out.println("\n🏷️ Available Expense Categories:");
        showNumberedCategories(expense.getAvailableCategories());
        String category = getCategoryInput("🏷️ Enter category (name or number): ", expense.getAvailableCategories());
        expense.setCategory(category);
        
        expense.setId(generateId());
        transactionManager.addTransaction(expense);
        
        System.out.println("✅ Expense added successfully!");
        displayTransactionSummary(expense);
    }
    
    private static void removeTransaction() {
        System.out.println("\n❌ === REMOVE TRANSACTION ===");
        
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
        
        System.out.print("\n🔍 Enter transaction ID to remove: ");
        String id = scanner.nextLine().trim();
        
        Transaction transaction = transactionManager.findTransactionById(id);
        if (transaction == null) {
            System.out.println("❌ Transaction not found.");
            return;
        }
        
        System.out.println("✅ Transaction found:");
        displayTransactionDetails(transaction);
    }
    
    private static void viewMonthlySummary() {
        System.out.println("\n📅 === MONTHLY SUMMARY ===");
        
        int month = getIntInput("📅 Enter month (1-12): ");
        int year = getIntInput("📅 Enter year: ");
        
        if (month < 1 || month > 12) {
            System.out.println("❌ Invalid month. Please enter a value between 1 and 12.");
            return;
        }
        
        double totalIncome = transactionManager.getTotalIncome(month, year);
        double totalExpenses = transactionManager.getTotalExpenses(month, year);
        double balance = totalIncome - totalExpenses;
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.printf("║           %s            ║%n", centerText(monthName, 25));
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.printf("║ 💰 Total Income:     $%,10.2f     ║%n", totalIncome);
        System.out.printf("║ 💸 Total Expenses:   $%,10.2f     ║%n", totalExpenses);
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.printf("║ 💹 Net Balance:      $%,10.2f     ║%n", balance);
        System.out.println("╚═══════════════════════════════════════╝");
        
        if (balance > 0) {
            System.out.println("✅ You saved money this month! 🎉");
        } else if (balance < 0) {
            System.out.println("⚠️ You overspent this month. Consider reviewing your expenses.");
        } else {
            System.out.println("➖ You broke even this month.");
        }
    }
    
    private static void viewIncomeByCategory() {
        System.out.println("\n💰 === INCOME BY CATEGORY ===");
        
        int month = getIntInput("📅 Enter month (1-12): ");
        int year = getIntInput("📅 Enter year: ");
        
        Map<String, Double> incomeByCategory = transactionManager.getIncomeByCategory(month, year);
        
        if (incomeByCategory.isEmpty()) {
            System.out.println("📭 No income recorded for " + month + "/" + year);
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        System.out.printf("%n🏷️ Income Categories - %s:%n", monthName);
        System.out.println("═".repeat(40));
        
        double total = 0;
        for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
            System.out.printf("💰 %-20s $%,10.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println("═".repeat(40));
        System.out.printf("💹 %-20s $%,10.2f%n", "TOTAL INCOME", total);
    }
    
    private static void viewExpensesByCategory() {
        System.out.println("\n💸 === EXPENSES BY CATEGORY ===");
        
        int month = getIntInput("📅 Enter month (1-12): ");
        int year = getIntInput("📅 Enter year: ");
        
        Map<String, Double> expensesByCategory = transactionManager.getExpensesByCategory(month, year);
        
        if (expensesByCategory.isEmpty()) {
            System.out.println("📭 No expenses recorded for " + month + "/" + year);
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        System.out.printf("%n🏷️ Expense Categories - %s:%n", monthName);
        System.out.println("═".repeat(40));
        
        double total = 0;
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            System.out.printf("💸 %-20s $%,10.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println("═".repeat(40));
        System.out.printf("💹 %-20s $%,10.2f%n", "TOTAL EXPENSES", total);
    }
    
    private static void viewRecentTransactions() {
        System.out.println("\n📋 === RECENT TRANSACTIONS ===");
        
        int count = getIntInput("📊 How many recent transactions to show? (default 10): ");
        if (count <= 0) count = 10;
        
        List<Transaction> recent = transactionManager.getRecentTransactions(count);
        
        if (recent.isEmpty()) {
            System.out.println("📭 No transactions found.");
            return;
        }
        
        System.out.printf("%n📋 Last %d transactions:%n", recent.size());
        System.out.println("═".repeat(80));
        System.out.printf("%-12s %-15s %-25s %-10s %-10s%n", "Date", "Type", "Description", "Amount", "Category");
        System.out.println("═".repeat(80));
        
        for (Transaction t : recent) {
            String type = t instanceof Income ? "💰 Income" : "💸 Expense";
            System.out.printf("%-12s %-15s %-25s $%,8.2f %-10s%n",
                t.getDate().format(DateTimeFormatter.ofPattern("MMM dd")),
                type,
                truncate(t.getDescription(), 25),
                t.getAmount(),
                t.getCategory());
        }
    }
    
    private static void viewTransactionsByDateRange() {
        System.out.println("\n📆 === TRANSACTIONS BY DATE RANGE ===");
        
        LocalDate startDate = getDateInput("📅 Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = getDateInput("📅 Enter end date (yyyy-MM-dd): ");
        
        if (startDate.isAfter(endDate)) {
            System.out.println("❌ Start date cannot be after end date.");
            return;
        }
        
        List<Transaction> transactions = transactionManager.getTransactionsByDateRange(startDate, endDate);
        
        if (transactions.isEmpty()) {
            System.out.printf("📭 No transactions found between %s and %s%n", 
                startDate.format(DISPLAY_FORMATTER), endDate.format(DISPLAY_FORMATTER));
            return;
        }
        
        System.out.printf("%n📋 Transactions from %s to %s (%d found):%n", 
            startDate.format(DISPLAY_FORMATTER), endDate.format(DISPLAY_FORMATTER), transactions.size());
        System.out.println("═".repeat(85));
        
        double totalIncome = 0, totalExpenses = 0;
        
        for (Transaction t : transactions) {
            String type = t instanceof Income ? "💰" : "💸";
            System.out.printf("%s %s - %-30s $%,8.2f [%s]%n",
                type,
                t.getDate().format(DISPLAY_FORMATTER),
                truncate(t.getDescription(), 30),
                t.getAmount(),
                t.getCategory());
            
            if (t instanceof Income) {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }
        
        System.out.println("═".repeat(85));
        System.out.printf("💰 Total Income:  $%,10.2f%n", totalIncome);
        System.out.printf("💸 Total Expenses: $%,10.2f%n", totalExpenses);
        System.out.printf("💹 Net Balance:   $%,10.2f%n", totalIncome - totalExpenses);
    }
    
    private static void viewYearlyOverview() {
        System.out.println("\n📈 === YEARLY OVERVIEW ===");
        
        int year = getIntInput("📅 Enter year: ");
        
        double totalIncome = transactionManager.getTotalIncomeForYear(year);
        double totalExpenses = transactionManager.getTotalExpensesForYear(year);
        double balance = totalIncome - totalExpenses;
        
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.printf("║              YEAR %d SUMMARY            ║%n", year);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ 💰 Total Income:      $%,15.2f ║%n", totalIncome);
        System.out.printf("║ 💸 Total Expenses:    $%,15.2f ║%n", totalExpenses);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ 💹 Net Savings:       $%,15.2f ║%n", balance);
        System.out.println("╚══════════════════════════════════════════╝");
        
        // Monthly breakdown
        Map<Integer, Double> monthlyIncome = transactionManager.getMonthlyIncome(year);
        Map<Integer, Double> monthlyExpenses = transactionManager.getMonthlyExpenses(year);
        
        if (!monthlyIncome.isEmpty() || !monthlyExpenses.isEmpty()) {
            System.out.println("\n📊 Monthly Breakdown:");
            System.out.println("Month      Income      Expenses     Balance");
            System.out.println("════════════════════════════════════════════");
            
            for (int month = 1; month <= 12; month++) {
                double income = monthlyIncome.getOrDefault(month, 0.0);
                double expenses = monthlyExpenses.getOrDefault(month, 0.0);
                double monthBalance = income - expenses;
                
                if (income > 0 || expenses > 0) {
                    String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMM"));
                    System.out.printf("%-8s $%,8.2f  $%,8.2f  $%,8.2f%n", 
                        monthName, income, expenses, monthBalance);
                }
            }
        }
    }
    
    private static void monthlyTrendAnalysis() {
        System.out.println("\n📈 === MONTHLY TREND ANALYSIS ===");
        
        int year = getIntInput("📅 Enter year for analysis: ");
        
        Map<Integer, Double> monthlyIncome = transactionManager.getMonthlyIncome(year);
        Map<Integer, Double> monthlyExpenses = transactionManager.getMonthlyExpenses(year);
        
        if (monthlyIncome.isEmpty() && monthlyExpenses.isEmpty()) {
            System.out.printf("📭 No data found for year %d%n", year);
            return;
        }
        
        System.out.printf("\n📊 %d Monthly Trends:%n", year);
        System.out.println("═".repeat(60));
        
        double avgIncome = monthlyIncome.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double avgExpenses = monthlyExpenses.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        
        System.out.printf("📊 Average Monthly Income:  $%,10.2f%n", avgIncome);
        System.out.printf("📊 Average Monthly Expenses: $%,10.2f%n", avgExpenses);
        System.out.printf("📊 Average Monthly Savings:  $%,10.2f%n", avgIncome - avgExpenses);
        
        // Find best and worst months
        if (!monthlyIncome.isEmpty()) {
            int bestIncomeMonth = monthlyIncome.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
            double bestIncomeAmount = monthlyIncome.get(bestIncomeMonth);
            
            System.out.printf("🏆 Best Income Month: %s ($%,.2f)%n", 
                YearMonth.of(year, bestIncomeMonth).format(DateTimeFormatter.ofPattern("MMMM")),
                bestIncomeAmount);
        }
        
        if (!monthlyExpenses.isEmpty()) {
            int highestExpenseMonth = monthlyExpenses.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
            double highestExpenseAmount = monthlyExpenses.get(highestExpenseMonth);
            
            System.out.printf("💸 Highest Expense Month: %s ($%,.2f)%n", 
                YearMonth.of(year, highestExpenseMonth).format(DateTimeFormatter.ofPattern("MMMM")),
                highestExpenseAmount);
        }
    }
    
    private static void categorySpendingAnalysis() {
        System.out.println("\n💹 === CATEGORY SPENDING ANALYSIS ===");
        
        int month = getIntInput("📅 Enter month (1-12): ");
        int year = getIntInput("📅 Enter year: ");
        
        Map<String, Double> expenses = transactionManager.getExpensesByCategory(month, year);
        
        if (expenses.isEmpty()) {
            System.out.println("📭 No expense data found for the specified period.");
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        double total = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        
        System.out.printf("%n💹 Spending Analysis - %s:%n", monthName);
        System.out.println("═".repeat(50));
        
        expenses.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (entry.getValue() / total) * 100;
                System.out.printf("💸 %-20s $%,8.2f (%4.1f%%)%n", 
                    entry.getKey(), entry.getValue(), percentage);
            });
        
        System.out.println("═".repeat(50));
        System.out.printf("💹 %-20s $%,8.2f (100.0%%)%n", "TOTAL", total);
    }
    
    private static void yearToDateSummary() {
        System.out.println("\n📊 === YEAR-TO-DATE SUMMARY ===");
        
        int year = LocalDate.now().getYear();
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate today = LocalDate.now();
        
        List<Transaction> ytdTransactions = transactionManager.getTransactionsByDateRange(startOfYear, today);
        
        if (ytdTransactions.isEmpty()) {
            System.out.printf("📭 No transactions found for %d year-to-date.%n", year);
            return;
        }
        
        double ytdIncome = ytdTransactions.stream()
            .filter(t -> t instanceof Income)
            .mapToDouble(Transaction::getAmount)
            .sum();
        
        double ytdExpenses = ytdTransactions.stream()
            .filter(t -> t instanceof Expense)
            .mapToDouble(Transaction::getAmount)
            .sum();
        
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.printf("║         %d YEAR-TO-DATE SUMMARY         ║%n", year);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ 💰 YTD Income:        $%,15.2f ║%n", ytdIncome);
        System.out.printf("║ 💸 YTD Expenses:      $%,15.2f ║%n", ytdExpenses);
        System.out.printf("║ 💹 YTD Net Savings:   $%,15.2f ║%n", ytdIncome - ytdExpenses);
        System.out.printf("║ 📊 Total Transactions: %,15d ║%n", ytdTransactions.size());
        System.out.println("╚══════════════════════════════════════════╝");
        
        double dailyAvgIncome = ytdIncome / today.getDayOfYear();
        double dailyAvgExpense = ytdExpenses / today.getDayOfYear();
        
        System.out.printf("%n📈 Daily Averages:%n");
        System.out.printf("💰 Average Daily Income:  $%,8.2f%n", dailyAvgIncome);
        System.out.printf("💸 Average Daily Expense: $%,8.2f%n", dailyAvgExpense);
        System.out.printf("💹 Average Daily Savings: $%,8.2f%n", dailyAvgIncome - dailyAvgExpense);
    }
    
    private static void spendingInsights() {
        System.out.println("\n💡 === SPENDING INSIGHTS ===");
        
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        LocalDate today = LocalDate.now();
        
        List<Transaction> recentTransactions = transactionManager.getTransactionsByDateRange(oneMonthAgo, today);
        
        if (recentTransactions.isEmpty()) {
            System.out.println("📭 Not enough recent data for insights.");
            return;
        }
        
        // Calculate insights
        double avgTransactionAmount = recentTransactions.stream()
            .mapToDouble(Transaction::getAmount)
            .average().orElse(0);
        
        long expenseCount = recentTransactions.stream()
            .filter(t -> t instanceof Expense)
            .count();
        
        long incomeCount = recentTransactions.stream()
            .filter(t -> t instanceof Income)
            .count();
        
        System.out.println("\n💡 Recent Insights (Last 30 days):");
        System.out.println("═".repeat(45));
        System.out.printf("📊 Total Transactions: %d%n", recentTransactions.size());
        System.out.printf("💸 Expense Transactions: %d (%.1f%%)%n", 
            expenseCount, (expenseCount * 100.0) / recentTransactions.size());
        System.out.printf("💰 Income Transactions: %d (%.1f%%)%n", 
            incomeCount, (incomeCount * 100.0) / recentTransactions.size());
        System.out.printf("💹 Average Transaction: $%.2f%n", avgTransactionAmount);
        
        // Most frequent category
        Map<String, Long> categoryFrequency = recentTransactions.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                Transaction::getCategory,
                java.util.stream.Collectors.counting()));
        
        if (!categoryFrequency.isEmpty()) {
            String mostFrequentCategory = categoryFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
            
            System.out.printf("🏆 Most Active Category: %s%n", mostFrequentCategory);
        }
    }
    
    private static void addNewCategory() {
        System.out.println("\n🏷️ === ADD NEW CATEGORY ===");
        
        System.out.println("1. 💰 Add Income Category");
        System.out.println("2. 💸 Add Expense Category");
        
        int choice = getIntInput("➤ Enter choice: ");
        System.out.print("🏷️ Enter new category name: ");
        String categoryName = scanner.nextLine().trim();
        
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
    
    private static void createBackup() {
        System.out.println("\n💾 === CREATE BACKUP ===");
        
        transactionManager.createBackup();
        System.out.println("✅ Backup created successfully!");
    }
    
    private static void showDataLocation() {
        System.out.println("\n📁 === DATA LOCATION ===");
        System.out.println("📁 Your data is stored at:");
        System.out.println("   " + transactionManager.getStorageLocation());
        System.out.println("\n💡 This location contains:");
        System.out.println("   📄 transactions.json - All your transaction data");
        System.out.println("   📄 categories.json - Your custom categories");
        System.out.println("   📁 backups/ - Backup files");
    }
    
    // Helper Methods
    private static void showNumberedCategories(List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, categories.get(i));
        }
    }
    
    private static String getCategoryInput(String prompt, List<String> categories) {
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
    
    private static void displayTransactionSummary(Transaction transaction) {
        System.out.println("\n📋 Transaction Details:");
        System.out.println("═".repeat(40));
        System.out.printf("🆔 ID:          %s%n", transaction.getId());
        System.out.printf("📅 Date:        %s%n", transaction.getDate().format(DISPLAY_FORMATTER));
        System.out.printf("📝 Description: %s%n", transaction.getDescription());
        System.out.printf("💰 Amount:      $%,.2f%n", transaction.getAmount());
        System.out.printf("🏷️ Category:    %s%n", transaction.getCategory());
        System.out.printf("📊 Type:        %s%n", transaction.getTransactionType());
    }
    
    private static void displayTransactionDetails(Transaction transaction) {
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
    
    private static boolean confirmAction(String prompt) {
        System.out.print(prompt);
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
    
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, width - text.length() - padding));
    }
    
    private static String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
    
    private static int getIntInput(String prompt) {
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
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount.");
            }
        }
    }
    
    private static LocalDate getDateInput(String prompt) {
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
    
    private static String generateId() {
        return "TXN_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    private static void findTransaction() {
        System.out.println("\n🔍 === FIND TRANSACTION ===");
        
        System.out.print("🔍 Enter transaction ID: ");
        String id = scanner.nextLine().trim();
        
        Transaction transaction = transactionManager.findTransactionById(id);
        if (transaction == null) {
            System.out.println("❌ Transaction not found.");
            return;
        }
        
        System.out.println("✅ Transaction found:");
        displayTransactionDetails(transaction);
    }
}
