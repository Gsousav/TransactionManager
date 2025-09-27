package org.gabx.expenses.transactions;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Income extends Transaction {
    private String incomeCategory;
    private static List<String> incomeCategories = new ArrayList<>(
        Arrays.asList("Salary", "Freelance", "Investment", "Gift", "Allowance")
    );

    @Override
    public String getTransactionType() {
        return "INCOME";
    }

    @Override
    public void setCategory(String category) {
        if(!incomeCategories.contains(category)) {
            addIncomeCategory(category); // Allow adding new categories
        }
        this.incomeCategory = category;
    }

    @Override
    public String getCategory() {
        return incomeCategory;
    }

    @Override
    public List<String> getAvailableCategories() {
        return new ArrayList<>(incomeCategories);
    }

    public static void addIncomeCategory(String newCategory) {
        if(!incomeCategories.contains(newCategory)) {
            incomeCategories.add(newCategory);
        } else {
            System.out.println(newCategory + " is already an income category!");
        }
    }
    
    public static List<String> getCategories() {
        return new ArrayList<>(incomeCategories);
    }
    
    public static void setCategories(List<String> categories) {
        incomeCategories = new ArrayList<>(categories);
    }
}
