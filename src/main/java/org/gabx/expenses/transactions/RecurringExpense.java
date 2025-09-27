package org.gabx.expenses.transactions;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RecurringExpense extends Expense {
    public enum Frequency {
        DAILY("Daily"),
        WEEKLY("Weekly"), 
        FOUR_WEEKS("4 Weeks"),
        MONTHLY("Monthly"),
        QUARTERLY("Quarterly"),
        YEARLY("Yearly");
        
        private final String displayName;
        
        Frequency(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    
    private Frequency frequency;
    private LocalDate nextDueDate;
    private LocalDate startDate;
    private boolean isActive;
    private String originalRecurringId; // ID of the original recurring expense template
    
    // Constructor
    public RecurringExpense() {
        super();
        this.isActive = true;
        this.startDate = LocalDate.now();
        this.nextDueDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Frequency getFrequency() {
        return frequency;
    }
    
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
        updateNextDueDate();
    }
    
    public LocalDate getNextDueDate() {
        return nextDueDate;
    }
    
    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        updateNextDueDate();
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public String getOriginalRecurringId() {
        return originalRecurringId;
    }
    
    public void setOriginalRecurringId(String originalRecurringId) {
        this.originalRecurringId = originalRecurringId;
    }
    
    @Override
    public String getTransactionType() {
        return "RECURRING_EXPENSE";
    }
    
    // Calculate next due date based on frequency
    public void updateNextDueDate() {
        if (frequency == null || startDate == null) {
            return;
        }
        
        LocalDate current = nextDueDate != null ? nextDueDate : startDate;
        
        switch (frequency) {
            case DAILY:
                nextDueDate = current.plusDays(1);
                break;
            case WEEKLY:
                nextDueDate = current.plusWeeks(1);
                break;
            case MONTHLY:
                nextDueDate = current.plusMonths(1);
                break;
            case QUARTERLY:
                nextDueDate = current.plusMonths(3);
                break;
            case YEARLY:
                nextDueDate = current.plusYears(1);
                break;
        }
    }
    
    // Create a regular expense instance from this recurring expense
    public Expense createExpenseInstance() {
        Expense expense = new Expense();
        expense.setId(generateExpenseId());
        expense.setAmount(this.getAmount());
        expense.setDescription(this.getDescription());
        expense.setCategory(this.getCategory());
        expense.setDate(this.getNextDueDate());
        return expense;
    }
    
    // Generate a unique ID for the expense instance
    private String generateExpenseId() {
        return "RE_" + this.getId() + "_" + System.currentTimeMillis();
    }
    
    // Check if the recurring expense is due
    public boolean isDue(LocalDate checkDate) {
        return isActive && (nextDueDate.isEqual(checkDate) || nextDueDate.isBefore(checkDate));
    }
    
    // Get all due dates up to a certain date
    public List<LocalDate> getDueDatesUpTo(LocalDate endDate) {
        List<LocalDate> dueDates = new ArrayList<>();
        LocalDate current = startDate;
        
        while (!current.isAfter(endDate) && isActive) {
            if (!current.isBefore(nextDueDate)) {
                dueDates.add(current);
            }
            current = getNextDateForFrequency(current);
        }
        
        return dueDates;
    }
    
    // Get the next date for a given frequency
    private LocalDate getNextDateForFrequency(LocalDate date) {
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
    
    // Calculate total amount for a date range
    public double calculateTotalForDateRange(LocalDate start, LocalDate end) {
        if (!isActive) return 0.0;
        
        long count = 0;
        LocalDate current = startDate;
        
        while (!current.isAfter(end) && !current.isBefore(start)) {
            count++;
            current = getNextDateForFrequency(current);
        }
        
        return count * getAmount();
    }
    
    // Static method to get all frequency options
    public static List<Frequency> getAvailableFrequencies() {
        return Arrays.asList(Frequency.values());
    }
    
    // Override toString for better display
    @Override
    public String toString() {
        return String.format("RecurringExpense{id='%s', description='%s', amount=%.2f, frequency=%s, nextDue=%s, active=%s}", 
            getId(), getDescription(), getAmount(), frequency, nextDueDate, isActive);
    }
}
