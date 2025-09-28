package org.gabx.expenses.handlers;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.manager.TransactionManager;
import org.gabx.expenses.ui.UserInputHandler;
import org.gabx.expenses.ui.DisplayUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewHandlers {
    private final TransactionManager transactionManager;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public ViewHandlers(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public void viewMonthlySummary() {
        DisplayUtils.printCenteredSection("MONTHLY SUMMARY");
        
        int month = UserInputHandler.getIntInput("[DATE] Enter month (1-12): ");
        int year = UserInputHandler.getIntInput("[DATE] Enter year: ");
        
        if (month < 1 || month > 12) {
            System.out.println("[ERROR] Invalid month. Please enter a value between 1 and 12.");
            return;
        }
        
        double totalIncome = transactionManager.getTotalIncome(month, year);
        double totalExpenses = transactionManager.getTotalExpenses(month, year);
        double balance = totalIncome - totalExpenses;
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        
        // Create monthly summary box
        int maxWidth = Math.max(monthName.length() + 4, 50);
        System.out.println("\n+" + "=".repeat(maxWidth - 2) + "+");
        System.out.println("|" + DisplayUtils.centerText(monthName, maxWidth - 2) + "|");
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.printf("| [INCOME] Total Income:      $%,15.2f |%n", totalIncome);
        System.out.printf("| [EXPENSE] Total Expenses:    $%,15.2f |%n", totalExpenses);
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.printf("| [BALANCE] Net Balance:       $%,15.2f |%n", balance);
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        
        if (balance > 0) {
            System.out.println("[SUCCESS] You saved money this month! [CELEBRATE]");
        } else if (balance < 0) {
            System.out.println("[WARNING] You overspent this month. Consider reviewing your expenses.");
        } else {
            System.out.println("[EVEN] You broke even this month.");
        }
    }
    
    public void viewIncomeByCategory() {
        DisplayUtils.printCenteredSection("INCOME BY CATEGORY");
        
        int month = UserInputHandler.getIntInput("[DATE] Enter month (1-12): ");
        int year = UserInputHandler.getIntInput("[DATE] Enter year: ");
        
        Map<String, Double> incomeByCategory = transactionManager.getIncomeByCategory(month, year);
        
        if (incomeByCategory.isEmpty()) {
            System.out.println("[EMPTY] No income recorded for " + month + "/" + year);
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        System.out.printf("%n[CATEGORY] Income Categories - %s:%n", monthName);
        System.out.println("=".repeat(45));
        
        double total = 0;
        for (Map.Entry<String, Double> entry : incomeByCategory.entrySet()) {
            System.out.printf("[INCOME] %-25s $%,10.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println("=".repeat(45));
        System.out.printf("[BALANCE] %-25s $%,10.2f%n", "TOTAL INCOME", total);
    }
    
    public void viewExpensesByCategory() {
        DisplayUtils.printCenteredSection("EXPENSES BY CATEGORY");
        
        int month = UserInputHandler.getIntInput("[DATE] Enter month (1-12): ");
        int year = UserInputHandler.getIntInput("[DATE] Enter year: ");
        
        Map<String, Double> expensesByCategory = transactionManager.getExpensesByCategory(month, year);
        
        if (expensesByCategory.isEmpty()) {
            System.out.println("[EMPTY] No expenses recorded for " + month + "/" + year);
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        System.out.printf("%n[CATEGORY] Expense Categories - %s:%n", monthName);
        System.out.println("=".repeat(45));
        
        double total = 0;
        for (Map.Entry<String, Double> entry : expensesByCategory.entrySet()) {
            System.out.printf("[EXPENSE] %-25s $%,10.2f%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println("=".repeat(45));
        System.out.printf("[BALANCE] %-25s $%,10.2f%n", "TOTAL EXPENSES", total);
    }
    
    public void viewRecentTransactions() {
        DisplayUtils.printCenteredSection("RECENT TRANSACTIONS");
        
        int count = UserInputHandler.getIntInput("[STATS] How many recent transactions to show? (default 10): ");
        if (count <= 0) count = 10;
        
        List<Transaction> recent = transactionManager.getRecentTransactions(count);
        
        if (recent.isEmpty()) {
            System.out.println("[EMPTY] No transactions found.");
            return;
        }
        
        System.out.printf("%n[LIST] Last %d transactions:%n", recent.size());
        System.out.println("=".repeat(85));
        System.out.printf("%-12s %-15s %-25s %-10s %-10s%n", "Date", "Type", "Description", "Amount", "Category");
        System.out.println("=".repeat(85));
        
        for (Transaction t : recent) {
            String type = t instanceof Income ? "[INCOME] Income" : "[EXPENSE] Expense";
            System.out.printf("%-12s %-15s %-25s $%,8.2f %-10s%n",
                t.getDate().format(DateTimeFormatter.ofPattern("MMM dd")),
                type,
                DisplayUtils.truncate(t.getDescription(), 25),
                t.getAmount(),
                t.getCategory());
        }
    }
    
    public void viewTransactionsByDateRange() {
        DisplayUtils.printCenteredSection("TRANSACTIONS BY DATE RANGE");
        
        LocalDate startDate = UserInputHandler.getDateInput("[DATE] Enter start date (yyyy-MM-dd): ");
        LocalDate endDate = UserInputHandler.getDateInput("[DATE] Enter end date (yyyy-MM-dd): ");
        
        if (startDate.isAfter(endDate)) {
            System.out.println("[ERROR] Start date cannot be after end date.");
            return;
        }
        
        List<Transaction> transactions = transactionManager.getTransactionsByDateRange(startDate, endDate);
        
        if (transactions.isEmpty()) {
            System.out.printf("[EMPTY] No transactions found between %s and %s%n", 
                startDate.format(DISPLAY_FORMATTER), endDate.format(DISPLAY_FORMATTER));
            return;
        }
        
        System.out.printf("%n[LIST] Transactions from %s to %s (%d found):%n", 
            startDate.format(DISPLAY_FORMATTER), endDate.format(DISPLAY_FORMATTER), transactions.size());
        System.out.println("=".repeat(85));
        
        double totalIncome = 0, totalExpenses = 0;
        
        for (Transaction t : transactions) {
            String type = t instanceof Income ? "[INCOME]" : "[EXPENSE]";
            System.out.printf("%s %s - %-30s $%,8.2f [%s]%n",
                type,
                t.getDate().format(DISPLAY_FORMATTER),
                DisplayUtils.truncate(t.getDescription(), 30),
                t.getAmount(),
                t.getCategory());
            
            if (t instanceof Income) {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }
        
        System.out.println("=".repeat(85));
        System.out.printf("[INCOME] Total Income:  $%,10.2f%n", totalIncome);
        System.out.printf("[EXPENSE] Total Expenses: $%,10.2f%n", totalExpenses);
        System.out.printf("[BALANCE] Net Balance:   $%,10.2f%n", totalIncome - totalExpenses);
    }
    
    public void viewYearlyOverview() {
        DisplayUtils.printCenteredSection("YEARLY OVERVIEW");
        
        int year = UserInputHandler.getIntInput("[DATE] Enter year: ");
        
        double totalIncome = transactionManager.getTotalIncomeForYear(year);
        double totalExpenses = transactionManager.getTotalExpensesForYear(year);
        double balance = totalIncome - totalExpenses;
        
        // Create yearly summary box
        String yearTitle = "YEAR " + year + " SUMMARY";
        int maxWidth = Math.max(yearTitle.length() + 4, 50);
        System.out.println("\n+" + "=".repeat(maxWidth - 2) + "+");
        System.out.println("|" + DisplayUtils.centerText(yearTitle, maxWidth - 2) + "|");
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.printf("| [INCOME] Total Income:      $%,15.2f |%n", totalIncome);
        System.out.printf("| [EXPENSE] Total Expenses:    $%,15.2f |%n", totalExpenses);
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.printf("| [BALANCE] Net Savings:       $%,15.2f |%n", balance);
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        
        // Monthly breakdown
        Map<Integer, Double> monthlyIncome = transactionManager.getMonthlyIncome(year);
        Map<Integer, Double> monthlyExpenses = transactionManager.getMonthlyExpenses(year);
        
        if (!monthlyIncome.isEmpty() || !monthlyExpenses.isEmpty()) {
            System.out.println("\n[STATS] Monthly Breakdown:");
            System.out.println("Month      Income      Expenses     Balance");
            System.out.println("==============================================");
            
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
    
    public void monthlyTrendAnalysis() {
        DisplayUtils.printCenteredSection("MONTHLY TREND ANALYSIS");
        
        int year = UserInputHandler.getIntInput("[DATE] Enter year for analysis: ");
        
        Map<Integer, Double> monthlyIncome = transactionManager.getMonthlyIncome(year);
        Map<Integer, Double> monthlyExpenses = transactionManager.getMonthlyExpenses(year);
        
        if (monthlyIncome.isEmpty() && monthlyExpenses.isEmpty()) {
            System.out.printf("[EMPTY] No data found for year %d%n", year);
            return;
        }
        
        System.out.printf("\n[STATS] %d Monthly Trends:%n", year);
        System.out.println("=".repeat(65));
        
        double avgIncome = monthlyIncome.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double avgExpenses = monthlyExpenses.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
        
        System.out.printf("[STATS] Average Monthly Income:  $%,12.2f%n", avgIncome);
        System.out.printf("[STATS] Average Monthly Expenses: $%,12.2f%n", avgExpenses);
        System.out.printf("[STATS] Average Monthly Savings:  $%,12.2f%n", avgIncome - avgExpenses);
        
        // Find best and worst months
        if (!monthlyIncome.isEmpty()) {
            int bestIncomeMonth = monthlyIncome.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
            double bestIncomeAmount = monthlyIncome.get(bestIncomeMonth);
            
            System.out.printf("[BEST] Best Income Month: %s ($%,.2f)%n", 
                YearMonth.of(year, bestIncomeMonth).format(DateTimeFormatter.ofPattern("MMMM")),
                bestIncomeAmount);
        }
        
        if (!monthlyExpenses.isEmpty()) {
            int highestExpenseMonth = monthlyExpenses.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
            double highestExpenseAmount = monthlyExpenses.get(highestExpenseMonth);
            
            System.out.printf("[EXPENSE] Highest Expense Month: %s ($%,.2f)%n", 
                YearMonth.of(year, highestExpenseMonth).format(DateTimeFormatter.ofPattern("MMMM")),
                highestExpenseAmount);
        }
    }
    
    public void categorySpendingAnalysis() {
        DisplayUtils.printCenteredSection("CATEGORY SPENDING ANALYSIS");
        
        int month = UserInputHandler.getIntInput("[DATE] Enter month (1-12): ");
        int year = UserInputHandler.getIntInput("[DATE] Enter year: ");
        
        Map<String, Double> expenses = transactionManager.getExpensesByCategory(month, year);
        
        if (expenses.isEmpty()) {
            System.out.println("[EMPTY] No expense data found for the specified period.");
            return;
        }
        
        String monthName = YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        double total = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        
        System.out.printf("%n[BALANCE] Spending Analysis - %s:%n", monthName);
        System.out.println("=".repeat(55));
        
        expenses.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (entry.getValue() / total) * 100;
                System.out.printf("[EXPENSE] %-25s $%,8.2f (%4.1f%%)%n", 
                    entry.getKey(), entry.getValue(), percentage);
            });
        
        System.out.println("=".repeat(55));
        System.out.printf("[BALANCE] %-25s $%,8.2f (100.0%%)%n", "TOTAL", total);
    }
    
    public void yearToDateSummary() {
        DisplayUtils.printCenteredSection("YEAR-TO-DATE SUMMARY");
        
        int year = LocalDate.now().getYear();
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate today = LocalDate.now();
        
        List<Transaction> ytdTransactions = transactionManager.getTransactionsByDateRange(startOfYear, today);
        
        if (ytdTransactions.isEmpty()) {
            System.out.printf("[EMPTY] No transactions found for %d year-to-date.%n", year);
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
        
        // Create YTD summary box
        String ytdTitle = year + " YEAR-TO-DATE SUMMARY";
        int maxWidth = Math.max(ytdTitle.length() + 4, 50);
        System.out.println("\n+" + "=".repeat(maxWidth - 2) + "+");
        System.out.println("|" + DisplayUtils.centerText(ytdTitle, maxWidth - 2) + "|");
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        System.out.printf("| [INCOME] YTD Income:        $%,15.2f |%n", ytdIncome);
        System.out.printf("| [EXPENSE] YTD Expenses:      $%,15.2f |%n", ytdExpenses);
        System.out.printf("| [BALANCE] YTD Net Savings:   $%,15.2f |%n", ytdIncome - ytdExpenses);
        System.out.printf("| [STATS] Total Transactions: %,15d |%n", ytdTransactions.size());
        System.out.println("+" + "=".repeat(maxWidth - 2) + "+");
        
        double dailyAvgIncome = ytdIncome / today.getDayOfYear();
        double dailyAvgExpense = ytdExpenses / today.getDayOfYear();
        
        System.out.printf("%n[TREND] Daily Averages:%n");
        System.out.printf("[INCOME] Average Daily Income:  $%,10.2f%n", dailyAvgIncome);
        System.out.printf("[EXPENSE] Average Daily Expense: $%,10.2f%n", dailyAvgExpense);
        System.out.printf("[BALANCE] Average Daily Savings: $%,10.2f%n", dailyAvgIncome - dailyAvgExpense);
    }
    
    public void spendingInsights() {
        DisplayUtils.printCenteredSection("SPENDING INSIGHTS");
        
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        LocalDate today = LocalDate.now();
        
        List<Transaction> recentTransactions = transactionManager.getTransactionsByDateRange(oneMonthAgo, today);
        
        if (recentTransactions.isEmpty()) {
            System.out.println("[EMPTY] Not enough recent data for insights.");
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
        
        System.out.println("\n[INSIGHT] Recent Insights (Last 30 days):");
        System.out.println("=".repeat(50));
        System.out.printf("[STATS] Total Transactions: %d%n", recentTransactions.size());
        System.out.printf("[EXPENSE] Expense Transactions: %d (%.1f%%)%n", 
            expenseCount, (expenseCount * 100.0) / recentTransactions.size());
        System.out.printf("[INCOME] Income Transactions: %d (%.1f%%)%n", 
            incomeCount, (incomeCount * 100.0) / recentTransactions.size());
        System.out.printf("[BALANCE] Average Transaction: $%.2f%n", avgTransactionAmount);
        
        // Most frequent category
        Map<String, Long> categoryFrequency = recentTransactions.stream()
            .collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.counting()));
        
        if (!categoryFrequency.isEmpty()) {
            String mostFrequentCategory = categoryFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey();
            
            System.out.printf("[BEST] Most Active Category: %s%n", mostFrequentCategory);
        }
    }
}
