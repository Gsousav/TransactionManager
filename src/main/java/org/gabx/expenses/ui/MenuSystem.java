package org.gabx.expenses.ui;

public class MenuSystem {
    private static final String[] MAIN_MENU_ITEMS = {
        "1. [ADD] Add Transactions",
        "2. [VIEW] View Transactions", 
        "3. [REPORTS] Reports & Analysis",
        "4. [MANAGE] Management & Settings",
        "5. [EXIT] Exit Application"
    };
    
    private static final String[] TRANSACTION_MENU_ITEMS = {
        "1. [INCOME] Add Income Transaction",
        "2. [EXPENSE] Add Expense Transaction",
        "3. [RECURRING] Add Recurring Expense",
        "4. [REMOVE] Remove Transaction",
        "5. [FIND] Find Transaction by ID",
        "6. [BACK] Back to Main Menu"
    };
    
    private static final String[] VIEWING_MENU_ITEMS = {
        "1. [MONTHLY] Monthly Summary",
        "2. [INCOME] Income by Category",
        "3. [EXPENSES] Expenses by Category",
        "4. [RECENT] Recent Transactions",
        "5. [DATE] Transactions by Date Range",
        "6. [YEARLY] Yearly Overview",
        "7. [RECURRING] Recurring Expenses",
        "8. [BACK] Back to Main Menu"
    };
    
    private static final String[] REPORTS_MENU_ITEMS = {
        "1. [TREND] Monthly Trend Analysis",
        "2. [CATEGORY] Category Spending Analysis",
        "3. [YTD] Year-to-Date Summary",
        "4. [INSIGHTS] Spending Insights",
        "5. [BACK] Back to Main Menu"
    };
    
    private static final String[] RECURRING_EXPENSE_MENU_ITEMS = {
        "1. [ADD] Add Recurring Expense",
        "2. [VIEW] View All Recurring Expenses",
        "3. [ACTIVE] View Active Recurring Expenses",
        "4. [UPCOMING] View Upcoming Recurring Expenses",
        "5. [REMOVE] Remove Recurring Expense",
        "6. [TOGGLE] Toggle Recurring Expense Status",
        "7. [PROCESS] Process Due Recurring Expenses",
        "8. [OVERDUE] Process Overdue Recurring Expenses",
        "9. [SUMMARY] Recurring Expense Summary",
        "10. [BACK] Back to Main Menu"
    };
    
    private static final String[] MANAGEMENT_MENU_ITEMS = {
        "1. [CATEGORY] Add New Category",
        "2. [BACKUP] Create Backup",
        "3. [LOCATION] Show Data Location",
        "4. [SUMMARY] Transaction Summary",
        "5. [BACK] Back to Main Menu"
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
        System.out.println(DisplayUtils.createLargeHeaderBox("PERSONAL TRANSACTION TRACKER", "Your Financial Command Center"));
    }
}
