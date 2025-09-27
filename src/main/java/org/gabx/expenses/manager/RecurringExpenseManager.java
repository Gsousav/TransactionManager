package org.gabx.expenses.manager;

import org.gabx.expenses.transactions.*;
import org.gabx.expenses.persistence.DataPersistenceService;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RecurringExpenseManager {
    private List<RecurringExpense> recurringExpenses;
    private TransactionManager transactionManager;
    private DataPersistenceService persistenceService;
    
    public RecurringExpenseManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.persistenceService = new DataPersistenceService();
        this.recurringExpenses = persistenceService.loadRecurringExpenses();
        
        System.out.println("Loaded " + recurringExpenses.size() + " recurring expenses from storage.");
    }
    
    // Add a new recurring expense
    public void addRecurringExpense(RecurringExpense recurringExpense) {
        recurringExpenses.add(recurringExpense);
        saveData();
    }
    
    // Remove a recurring expense
    public void removeRecurringExpense(String id) {
        recurringExpenses.removeIf(re -> re.getId().equals(id));
        saveData();
    }
    
    // Get all recurring expenses
    public List<RecurringExpense> getAllRecurringExpenses() {
        return new ArrayList<>(recurringExpenses);
    }
    
    // Get active recurring expenses
    public List<RecurringExpense> getActiveRecurringExpenses() {
        return recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .collect(Collectors.toList());
    }
    
    // Find recurring expense by ID
    public RecurringExpense findRecurringExpenseById(String id) {
        return recurringExpenses.stream()
            .filter(re -> re.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    // Get recurring expenses due on a specific date
    public List<RecurringExpense> getRecurringExpensesDueOn(LocalDate date) {
        return recurringExpenses.stream()
            .filter(re -> re.isDue(date))
            .collect(Collectors.toList());
    }
    
    // Get recurring expenses due within a date range
    public List<RecurringExpense> getRecurringExpensesDueBetween(LocalDate startDate, LocalDate endDate) {
        return recurringExpenses.stream()
            .filter(re -> re.isActive())
            .filter(re -> {
                LocalDate nextDue = re.getNextDueDate();
                return !nextDue.isBefore(startDate) && !nextDue.isAfter(endDate);
            })
            .collect(Collectors.toList());
    }
    
    // Get upcoming recurring expenses (next 30 days by default)
    public List<RecurringExpense> getUpcomingRecurringExpenses(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);
        
        return recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .filter(re -> {
                LocalDate nextDue = re.getNextDueDate();
                return !nextDue.isBefore(today) && !nextDue.isAfter(futureDate);
            })
            .sorted(Comparator.comparing(RecurringExpense::getNextDueDate))
            .collect(Collectors.toList());
    }
    
    // Process recurring expenses and generate regular expenses
    public List<Expense> processRecurringExpenses(LocalDate processDate) {
        List<Expense> generatedExpenses = new ArrayList<>();
        List<RecurringExpense> dueExpenses = getRecurringExpensesDueOn(processDate);
        
        for (RecurringExpense recurringExpense : dueExpenses) {
            Expense expense = recurringExpense.createExpenseInstance();
            expense.setOriginalRecurringId(recurringExpense.getId());
            transactionManager.addTransaction(expense);
            generatedExpenses.add(expense);
            
            // Update the next due date
            recurringExpense.updateNextDueDate();
        }
        
        if (!generatedExpenses.isEmpty()) {
            saveData();
        }
        
        return generatedExpenses;
    }
    
    // Process all overdue recurring expenses
    public List<Expense> processOverdueRecurringExpenses() {
        List<Expense> generatedExpenses = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        List<RecurringExpense> overdueExpenses = recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .filter(re -> re.getNextDueDate().isBefore(today))
            .collect(Collectors.toList());
        
        for (RecurringExpense recurringExpense : overdueExpenses) {
            // Generate expenses for each overdue period
            LocalDate current = recurringExpense.getNextDueDate();
            while (!current.isAfter(today)) {
                Expense expense = recurringExpense.createExpenseInstance();
                expense.setDate(current);
                expense.setOriginalRecurringId(recurringExpense.getId());
                transactionManager.addTransaction(expense);
                generatedExpenses.add(expense);
                
                current = getNextDateForFrequency(current, recurringExpense.getFrequency());
            }
            
            // Update the next due date
            recurringExpense.setNextDueDate(current);
        }
        
        if (!generatedExpenses.isEmpty()) {
            saveData();
        }
        
        return generatedExpenses;
    }
    
    // Get total monthly recurring expenses
    public double getTotalMonthlyRecurringExpenses() {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate monthEnd = today.withDayOfMonth(today.lengthOfMonth());
        
        return recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .mapToDouble(re -> re.calculateTotalForDateRange(monthStart, monthEnd))
            .sum();
    }
    
    // Get recurring expenses by category
    public Map<String, Double> getRecurringExpensesByCategory() {
        return recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .collect(Collectors.groupingBy(
                RecurringExpense::getCategory,
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
    
    // Get recurring expenses by frequency
    public Map<RecurringExpense.Frequency, Double> getRecurringExpensesByFrequency() {
        return recurringExpenses.stream()
            .filter(RecurringExpense::isActive)
            .collect(Collectors.groupingBy(
                RecurringExpense::getFrequency,
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
    
    // Update a recurring expense
    public void updateRecurringExpense(String id, RecurringExpense updatedExpense) {
        RecurringExpense existing = findRecurringExpenseById(id);
        if (existing != null) {
            int index = recurringExpenses.indexOf(existing);
            updatedExpense.setId(id); // Preserve the original ID
            recurringExpenses.set(index, updatedExpense);
            saveData();
        }
    }
    
    // Toggle active status of a recurring expense
    public void toggleRecurringExpenseStatus(String id) {
        RecurringExpense expense = findRecurringExpenseById(id);
        if (expense != null) {
            expense.setActive(!expense.isActive());
            saveData();
        }
    }
    
    // Get summary statistics
    public void printRecurringExpenseSummary() {
        System.out.println("\n=== RECURRING EXPENSE SUMMARY ===");
        System.out.println("Total Recurring Expenses: " + recurringExpenses.size());
        System.out.println("Active Recurring Expenses: " + getActiveRecurringExpenses().size());
        System.out.println("Inactive Recurring Expenses: " + 
            (recurringExpenses.size() - getActiveRecurringExpenses().size()));
        
        if (!recurringExpenses.isEmpty()) {
            double totalMonthly = getTotalMonthlyRecurringExpenses();
            System.out.printf("Estimated Monthly Total: $%,.2f%n", totalMonthly);
            System.out.printf("Estimated Yearly Total: $%,.2f%n", totalMonthly * 12);
        }
        
        // Show upcoming expenses
        List<RecurringExpense> upcoming = getUpcomingRecurringExpenses(30);
        if (!upcoming.isEmpty()) {
            System.out.println("Upcoming in next 30 days: " + upcoming.size());
        }
        
        System.out.println();
    }
    
    // Helper method to get next date for frequency
    private LocalDate getNextDateForFrequency(LocalDate date, RecurringExpense.Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return date.plusDays(1);
            case WEEKLY:
                return date.plusWeeks(1);
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
    
    // Save recurring expenses to persistence
    private void saveData() {
        persistenceService.saveRecurringExpenses(recurringExpenses);
    }
}
