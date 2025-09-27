package org.gabx.expenses.transactions;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Expense extends Transaction {
    private String expenseCategory;
    private static List<String> expenseCategories = new ArrayList<>(
        Arrays.asList("Groceries", "Transport", "Entertainment", "Utilities", 
                     "Healthcare", "Clothing", "Restaurants", "Education")
    );
    
    @Override
    public String getTransactionType() {
        return "EXPENSE";
    }
    
    @Override
    public void setCategory(String category) {
        if (!expenseCategories.contains(category)) {
            addExpenseCategory(category);
        }
        this.expenseCategory = category;
    }
    
    @Override
    public String getCategory() {
        return this.expenseCategory;
    }
    
    @Override
    public List<String> getAvailableCategories() {
        return new ArrayList<>(expenseCategories);
    }
    
    // This method should be static and public
    public static void addExpenseCategory(String newCategory) {
        if(!expenseCategories.contains(newCategory)) {
            expenseCategories.add(newCategory);
        } else {
            System.out.println(newCategory + " is already an expense category!");
        }
    }
    
    public static List<String> getCategories() {
        return new ArrayList<>(expenseCategories);
    }
    
    public static void setCategories(List<String> categories) {
        expenseCategories = new ArrayList<>(categories);
    } 
}
