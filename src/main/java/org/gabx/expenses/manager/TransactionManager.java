package org.gabx.expenses.manager;

import org.gabx.expenses.transactions.*;
import java.util.stream.Collectors;
import java.util.ArrayList; 
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

public class TransactionManager {
    private List<Transaction> transactions;

    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
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
    
    public Map<String, Double> getExpensesByCategory(int month, int year) {
        return transactions.stream()
            .filter(t -> t instanceof Expense)
            .filter(t -> isInMonth(t.getDate(), month, year))
            .collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.summingDouble(Transaction::getAmount)
        ));
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

    private boolean isInMonth(LocalDate date, int month, int year) {  // was "mont"
        return date.getMonthValue() == month && date.getYear() == year;
    }
}
