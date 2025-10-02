package org.gabx.expenses.manager;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.persistence.DataPersistenceService;
import java.util.stream.Collectors;
import java.util.ArrayList; 
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.Comparator;

public class TransactionManager {
    private List<Transaction> transactions;
    private DataPersistenceService persistenceService;
    
    public TransactionManager() {
        this.persistenceService = new DataPersistenceService();
        this.transactions = persistenceService.loadTransactions();
        loadCategories();
        
        System.out.println("Loaded " + transactions.size() + " transactions from storage.");
        System.out.println("Data stored in: " + persistenceService.getDataLocation());
    }
    
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveData();
    }
    
    public void removeTransaction(String id) {
        transactions.removeIf(t -> t.getId().equals(id));
        saveData();
    }
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
            .filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))
            .sorted(Comparator.comparing(Transaction::getDate).reversed())
            .collect(Collectors.toList());
    }
    
    public List<Transaction> getRecentTransactions(int count) {
        return transactions.stream()
            .sorted(Comparator.comparing(Transaction::getDate).reversed())
            .limit(count)
            .collect(Collectors.toList());
    }
    
    public Transaction findTransactionById(String id) {
        return transactions.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public double getTotalIncome(int month, int year) {
        return transactions.stream()
            .filter(t -> t instanceof Income)
            .filter(t -> isInMonth(t.getDate(), month, year))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }

    public double getTotalExpenses(int month, int year) {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .filter(t -> isInMonth(t.getDate(), month, year))
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public double getTotalIncomeForYear(int year) {
        return transactions.stream()
            .filter(t -> t instanceof Income)
            .filter(t -> t.getDate().getYear() == year)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public double getTotalExpensesForYear(int year) {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .filter(t -> t.getDate().getYear() == year)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public double getTotalIncome() {
        return transactions.stream()
            .filter(t -> t instanceof Income)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public double getTotalExpenses() {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .mapToDouble(Transaction::getAmount)
            .sum();
    }
    
    public Map<String, Double> getIncomeByCategory(int month, int year) {
        return transactions.stream()
            .filter(t -> t instanceof Income)
            .filter(t -> isInMonth(t.getDate(), month, year))
            .collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.summingDouble(Transaction::getAmount)
            ));
    }
    
    public Map<String, Double> getExpensesByCategory(int month, int year) {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .filter(t -> isInMonth(t.getDate(), month, year))
            .collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.summingDouble(Transaction::getAmount)
            ));
    }
    
    public Map<Integer, Double> getMonthlyIncome(int year) {
        return transactions.stream()
            .filter(t -> t instanceof Income)
            .filter(t -> t.getDate().getYear() == year)
            .collect(Collectors.groupingBy(
                t -> t.getDate().getMonthValue(),
                Collectors.summingDouble(Transaction::getAmount)
            ));
    }
    
    public Map<Integer, Double> getMonthlyExpenses(int year) {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .filter(t -> t.getDate().getYear() == year)
            .collect(Collectors.groupingBy(
                t -> t.getDate().getMonthValue(),
                Collectors.summingDouble(Transaction::getAmount)
            ));
    }
    
    public void createBackup() {
        persistenceService.createBackup();
    }
    
    public String getStorageLocation() {
        return persistenceService.getDataLocation();
    }
    
    private void saveData() {
        // Convert transactions to data format for JSON
        List<DataPersistenceService.TransactionData> dataList = transactions.stream()
            .map(DataPersistenceService.TransactionData::new)
            .collect(Collectors.toList());
        
        persistenceService.saveTransactions(dataList);
        saveCategories();
    }
    
    private void loadCategories() {
        Map<String, List<String>> categories = persistenceService.loadCategories();
        if (categories.containsKey("income")) {
            Income.setCategories(categories.get("income"));
        }
        if (categories.containsKey("expense")) {
            Expense.setCategories(categories.get("expense"));
        }
    }
    
    private void saveCategories() {
        persistenceService.saveCategories(
            Income.getCategories(),
            Expense.getCategories()
        );
    }

    private boolean isInMonth(LocalDate date, int month, int year) {
        return date.getMonthValue() == month && date.getYear() == year;
    }
    
    // Statistics methods
    public void printTransactionSummary() {
        System.out.println("\n=== TRANSACTION SUMMARY ===");
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("Income Transactions: " + transactions.stream().filter(t -> t instanceof Income).count());
        System.out.println("Expense Transactions: " + transactions.stream().filter(t -> t instanceof Expense).count());
        
        if (!transactions.isEmpty()) {
            LocalDate earliest = transactions.stream().map(Transaction::getDate).min(LocalDate::compareTo).orElse(LocalDate.now());
            LocalDate latest = transactions.stream().map(Transaction::getDate).max(LocalDate::compareTo).orElse(LocalDate.now());
            System.out.println("Date Range: " + earliest + " to " + latest);
        }
        System.out.println();
    }
}
