package org.gabx.expenses.persistence;

import org.gabx.expenses.transactions.Transaction;
import org.gabx.expenses.transactions.Income;
import org.gabx.expenses.transactions.Expense;
import org.gabx.expenses.transactions.RecurringExpense;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class DataPersistenceService {
    private static final String DATA_FILE = "transactions.json";
    private static final String CATEGORIES_FILE = "categories.json";
    private static final String RECURRING_FILE = "recurring_expenses.json";
    private static final String DATA_DIRECTORY = System.getProperty("user.home") + File.separator + "bin" + File.separator + ".expense-tracker";
    
    private final ObjectMapper objectMapper;
    private final File dataFile;
    private final File categoriesFile;
    private final File recurringFile;
    private final File dataDir;
    
    public DataPersistenceService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        
        // Create data directory if it doesn't exist
        this.dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        this.dataFile = new File(dataDir, DATA_FILE);
        this.categoriesFile = new File(dataDir, CATEGORIES_FILE);
        this.recurringFile = new File(dataDir, RECURRING_FILE);
        
        // Ensure files exist
        createFileIfNotExists(dataFile);
        createFileIfNotExists(categoriesFile);
        createFileIfNotExists(recurringFile);
    }
    
    private void createFileIfNotExists(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                // Initialize with empty content
                if (file.equals(dataFile)) {
                    objectMapper.writeValue(file, new ArrayList<TransactionData>());
                } else if (file.equals(categoriesFile)) {
                    Map<String, List<String>> defaultCategories = new HashMap<>();
                    defaultCategories.put("income", List.of("Salary", "Freelance", "Investment", "Gift", "Allowance"));
                    defaultCategories.put("expense", List.of("Groceries", "Transport", "Entertainment", "Utilities", "Healthcare", "Clothing", "Restaurants", "Education"));
                    objectMapper.writeValue(file, defaultCategories);
                } else if (file.equals(recurringFile)) {
                    objectMapper.writeValue(file, new ArrayList<RecurringExpenseData>());
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating file " + file.getName() + ": " + e.getMessage());
        }
    }
    
    public void saveTransactions(List<TransactionData> transactions) {
        try {
            objectMapper.writeValue(dataFile, transactions);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
    
    public List<Transaction> loadTransactions() {
        try {
            if (dataFile.length() == 0) {
                return new ArrayList<>();
            }
            
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, TransactionData.class);
            List<TransactionData> transactionDataList = objectMapper.readValue(dataFile, listType);
            
            List<Transaction> transactions = new ArrayList<>();
            for (TransactionData data : transactionDataList) {
                Transaction transaction;
                if ("INCOME".equals(data.transactionType)) {
                    transaction = new Income();
                } else {
                    Expense expense = new Expense();
                    if (data.originalRecurringId != null) {
                        expense.setOriginalRecurringId(data.originalRecurringId);
                    }
                    transaction = expense;
                }
                transaction.setId(data.id);
                transaction.setAmount(data.amount);
                transaction.setDate(data.date);
                transaction.setDescription(data.description);
                transaction.setCategory(data.category);
                transactions.add(transaction);
            }
            
            return transactions;
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void saveCategories(List<String> incomeCategories, List<String> expenseCategories) {
        try {
            Map<String, List<String>> categories = new HashMap<>();
            categories.put("income", new ArrayList<>(incomeCategories));
            categories.put("expense", new ArrayList<>(expenseCategories));
            objectMapper.writeValue(categoriesFile, categories);
        } catch (IOException e) {
            System.err.println("Error saving categories: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, List<String>> loadCategories() {
        try {
            if (categoriesFile.length() == 0) {
                Map<String, List<String>> defaultCategories = new HashMap<>();
                defaultCategories.put("income", new ArrayList<>(List.of("Salary", "Freelance", "Investment", "Gift", "Allowance")));
                defaultCategories.put("expense", new ArrayList<>(List.of("Groceries", "Transport", "Entertainment", "Utilities", "Healthcare", "Clothing", "Restaurants", "Education")));
                return defaultCategories;
            }
            
            return objectMapper.readValue(categoriesFile, Map.class);
        } catch (IOException e) {
            System.err.println("Error loading categories: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    public void saveRecurringExpenses(List<RecurringExpense> recurringExpenses) {
        try {
            List<RecurringExpenseData> dataList = recurringExpenses.stream()
                .map(RecurringExpenseData::new)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            objectMapper.writeValue(recurringFile, dataList);
        } catch (IOException e) {
            System.err.println("Error saving recurring expenses: " + e.getMessage());
        }
    }
    
    public List<RecurringExpense> loadRecurringExpenses() {
        try {
            if (recurringFile.length() == 0) {
                return new ArrayList<>();
            }
            
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, RecurringExpenseData.class);
            List<RecurringExpenseData> recurringDataList = objectMapper.readValue(recurringFile, listType);
            
            List<RecurringExpense> recurringExpenses = new ArrayList<>();
            for (RecurringExpenseData data : recurringDataList) {
                RecurringExpense recurringExpense = new RecurringExpense();
                recurringExpense.setId(data.id);
                recurringExpense.setAmount(data.amount);
                recurringExpense.setDate(data.date);
                recurringExpense.setDescription(data.description);
                recurringExpense.setCategory(data.category);
                recurringExpense.setFrequency(data.frequency);
                recurringExpense.setNextDueDate(data.nextDueDate);
                recurringExpense.setStartDate(data.startDate);
                recurringExpense.setActive(data.isActive);
                recurringExpense.setOriginalRecurringId(data.originalRecurringId);
                recurringExpenses.add(recurringExpense);
            }
            
            return recurringExpenses;
        } catch (IOException e) {
            System.err.println("Error loading recurring expenses: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public void createBackup() {
        try {
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            File backupDir = new File(dataDir, "backups");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }
            
            File backupFile = new File(backupDir, "transactions_backup_" + timestamp + ".json");
            objectMapper.writeValue(backupFile, loadTransactions());
            
            System.out.println("Backup created: " + backupFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
    
    public String getDataLocation() {
        return dataDir.getAbsolutePath();
    }
    
    // Helper class for JSON serialization
    public static class TransactionData {
        public String id;
        public double amount;
        public java.time.LocalDate date;
        public String description;
        public String category;
        public String transactionType;
        public String originalRecurringId;
        
        public TransactionData() {}
        
        public TransactionData(Transaction transaction) {
            this.id = transaction.getId();
            this.amount = transaction.getAmount();
            this.date = transaction.getDate();
            this.description = transaction.getDescription();
            this.category = transaction.getCategory();
            this.transactionType = transaction.getTransactionType();
            if (transaction instanceof Expense) {
                this.originalRecurringId = ((Expense) transaction).getOriginalRecurringId();
            }
        }
    }
    
    // Helper class for recurring expense JSON serialization
    public static class RecurringExpenseData {
        public String id;
        public double amount;
        public java.time.LocalDate date;
        public String description;
        public String category;
        public String transactionType;
        public RecurringExpense.Frequency frequency;
        public java.time.LocalDate nextDueDate;
        public java.time.LocalDate startDate;
        public boolean isActive;
        public String originalRecurringId;
        
        public RecurringExpenseData() {}
        
        public RecurringExpenseData(RecurringExpense recurringExpense) {
            this.id = recurringExpense.getId();
            this.amount = recurringExpense.getAmount();
            this.date = recurringExpense.getDate();
            this.description = recurringExpense.getDescription();
            this.category = recurringExpense.getCategory();
            this.transactionType = recurringExpense.getTransactionType();
            this.frequency = recurringExpense.getFrequency();
            this.nextDueDate = recurringExpense.getNextDueDate();
            this.startDate = recurringExpense.getStartDate();
            this.isActive = recurringExpense.isActive();
            this.originalRecurringId = recurringExpense.getOriginalRecurringId();
        }
    }
}
