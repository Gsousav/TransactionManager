package org.gabx.expenses.ui;

public class MenuSystem {
    
    public static void displayMainMenu() {
        System.out.println("\n╔═══════════════ MAIN MENU ═══════════════╗");
        System.out.println("║ 1. 📝 Add Transactions                  ║");
        System.out.println("║ 2. 👀 View Transactions                 ║");
        System.out.println("║ 3. 📊 Reports & Analysis                ║");
        System.out.println("║ 4. ⚙️  Management & Settings            ║");
        System.out.println("║ 5. 🚪 Exit                               ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void displayTransactionMenu() {
        System.out.println("\n╔═══════════ TRANSACTION MENU ════════════╗");
        System.out.println("║ 1. 💰 Add Income                        ║");
        System.out.println("║ 2. 💸 Add Expense                       ║");
        System.out.println("║ 3. ❌ Remove Transaction                ║");
        System.out.println("║ 4. 🔍 Find Transaction by ID            ║");
        System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void displayViewingMenu() {
        System.out.println("\n╔═════════════ VIEWING MENU ══════════════╗");
        System.out.println("║ 1. 📅 Monthly Summary                   ║");
        System.out.println("║ 2. 📊 Income by Category                ║");
        System.out.println("║ 3. 📊 Expenses by Category              ║");
        System.out.println("║ 4. 📋 Recent Transactions               ║");
        System.out.println("║ 5. 📆 Transactions by Date Range        ║");
        System.out.println("║ 6. 📈 Yearly Overview                   ║");
        System.out.println("║ 7. ⬅️  Back to Main Menu                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void displayReportsMenu() {
        System.out.println("\n╔═════════════ REPORTS MENU ══════════════╗");
        System.out.println("║ 1. 📈 Monthly Trend Analysis            ║");
        System.out.println("║ 2. 💹 Category Spending Analysis        ║");
        System.out.println("║ 3. 📊 Year-to-Date Summary               ║");
        System.out.println("║ 4. 💡 Spending Insights                 ║");
        System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void displayManagementMenu() {
        System.out.println("\n╔═══════════ MANAGEMENT MENU ═════════════╗");
        System.out.println("║ 1. 🏷️  Add New Category                 ║");
        System.out.println("║ 2. 💾 Create Backup                     ║");
        System.out.println("║ 3. 📁 Show Data Location                ║");
        System.out.println("║ 4. 📊 Transaction Summary               ║");
        System.out.println("║ 5. ⬅️  Back to Main Menu                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public static void displayAppHeader() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     Personal Transaction Tracker      ║");
        System.out.println("║        Your Financial Command Center   ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}
