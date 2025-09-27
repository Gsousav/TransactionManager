package org.gabx.expenses.transactions;
import java.time.LocalDate;
import java.util.List;

public abstract class Transaction {
    protected String id;
    protected double amount;
    protected LocalDate date;
    protected String description;
    
    // Constructors
    public Transaction() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // Abstract methods
    public abstract String getTransactionType();
    public abstract void setCategory(String category);
    public abstract String getCategory();
    public abstract List<String> getAvailableCategories();
}
