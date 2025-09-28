package org.gabx.expenses.ui;

public class MenuSystem {
    private static final String[] MAIN_MENU_ITEMS = {
        "1. 📝 Add Transactions",
        "2. 👀 View Transactions", 
        "3. 📊 Reports & Analysis",
        "4. ⚙️  Management & Settings",
        "5. 🚪 Exit"
    };
    
    private static final String[] TRANSACTION_MENU_ITEMS = {
        "1. 💰 Add Income",
        "2. 💸 Add Expense",
        "3. 🔄 Add Recurring Expense",
        "4. ❌ Remove Transaction",
        "5. 🔍 Find Transaction by ID",
        "6. ⬅️  Back to Main Menu"
    };
    
    private static final String[] VIEWING_MENU_ITEMS = {
        "1. 📅 Monthly Summary",
        "2. 📊 Income by Category",
        "3. 📊 Expenses by Category",
        "4. 📋 Recent Transactions",
        "5. 📆 Transactions by Date Range",
        "6. 📈 Yearly Overview",
        "7. 🔄 Recurring Expenses",
        "8. ⬅️  Back to Main Menu"
    };
    
    private static final String[] REPORTS_MENU_ITEMS = {
        "1. 📈 Monthly Trend Analysis",
        "2. 💹 Category Spending Analysis",
        "3. 📊 Year-to-Date Summary",
        "4. 💡 Spending Insights",
        "5. ⬅️  Back to Main Menu"
    };
    
    private static final String[] RECURRING_EXPENSE_MENU_ITEMS = {
        "1. 🔄 Add Recurring Expense",
        "2. 📋 View All Recurring Expenses",
        "3. ✅ View Active Recurring Expenses",
        "4. 📅 View Upcoming Recurring Expenses",
        "5. ❌ Remove Recurring Expense",
        "6. 🔄 Toggle Recurring Expense Status",
        "7. ⚡ Process Due Recurring Expenses",
        "8. ⚠️  Process Overdue Recurring Expenses",
        "9. 📊 Recurring Expense Summary",
        "10. ⬅️  Back to Main Menu"
    };
    
    private static final String[] MANAGEMENT_MENU_ITEMS = {
        "1. 🏷️  Add New Category",
        "2. 💾 Create Backup",
        "3. 📁 Show Data Location",
        "4. 📊 Transaction Summary",
        "5. ⬅️  Back to Main Menu"
    };
    
    public static void displayMainMenu() {
        System.out.println(DisplayUtils.createMenuBox("MAIN MENU", MAIN_MENU_ITEMS));
    }
    
    public static void displayTransactionMenu() {
        System.out.println(DisplayUtils.createMenuBox("TRANSACTION MENU", TRANSACTION_MENU_ITEMS));
    }
    
    public static void displayViewingMenu() {
        System.out.println(DisplayUtils.createMenuBox("VIEWING MENU", VIEWING_MENU_ITEMS));
    }
    
    public static void displayReportsMenu() {
        System.out.println(DisplayUtils.createMenuBox("REPORTS MENU", REPORTS_MENU_ITEMS));
    }
    
    public static void displayRecurringExpenseMenu() {
        System.out.println(DisplayUtils.createMenuBox("RECURRING EXPENSE MENU", RECURRING_EXPENSE_MENU_ITEMS));
    }
    
    public static void displayManagementMenu() {
        System.out.println(DisplayUtils.createMenuBox("MANAGEMENT MENU", MANAGEMENT_MENU_ITEMS));
    }
    
    public static void displayAppHeader() {
        System.out.println(DisplayUtils.createHeaderBox("Personal Transaction Tracker", "Your Financial Command Center"));
    }
}
