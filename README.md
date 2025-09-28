# 💰 Personal Expense Tracker

A comprehensive command-line expense tracking application for personal finance management, built with Java 11 and Maven.

## 🚀 Features

### 📝 Transaction Management
- **Add Income**: Track your income sources with categories and descriptions
- **Add Expenses**: Record expenses with detailed categorization
- **Recurring Expenses**: Set up automatic recurring expenses with flexible frequencies
- **Transaction History**: View, search, and manage all your transactions

### 👀 Viewing & Analysis
- **Monthly Summary**: Get a complete overview of your monthly finances
- **Category Analysis**: View income and expenses broken down by category
- **Recent Transactions**: Quick access to your latest financial activity
- **Date Range Queries**: Filter transactions by specific date ranges
- **Yearly Overview**: Annual financial summaries and trends

### 📊 Reports & Insights
- **Monthly Trend Analysis**: Track spending patterns over time
- **Category Spending Analysis**: Identify your biggest expense categories
- **Year-to-Date Summary**: Comprehensive yearly financial overview
- **Spending Insights**: Get intelligent insights about your spending habits

### 🔄 Recurring Expenses
- **Flexible Frequencies**: Daily, Weekly, 4-Weekly, Monthly, Quarterly, and Yearly
- **Automatic Processing**: Generate regular expenses from recurring templates
- **Overdue Management**: Handle missed payments automatically
- **Status Management**: Activate/deactivate recurring expenses as needed

### ⚙️ Management & Settings
- **Custom Categories**: Add your own expense and income categories
- **Data Backup**: Create backups of your financial data
- **Data Location**: View where your data is stored
- **Transaction Summary**: Get comprehensive financial summaries

## 🛠️ Technology Stack

- **Java 11+**: Core application language
- **Maven**: Build and dependency management
- **Jackson**: JSON data persistence
- **JUnit 5**: Unit testing framework
- **Command-line Interface**: User-friendly terminal interface

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Terminal/Command Prompt

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/gabx/expenses.git
cd expenses
```

### 2. Build the Application
```bash
mvn clean compile
```

### 3. Run the Application
```bash
# Option 1: Using Maven
mvn exec:java

# Option 2: Using the JAR file
mvn package
java -jar target/expenses-tracker.jar
```

## 📁 Project Structure

```
src/
├── main/java/org/gabx/expenses/
│   ├── App.java                          # Main application entry point
│   ├── handlers/                         # Business logic handlers
│   │   ├── ManagementHandlers.java       # Data management operations
│   │   ├── RecurringExpenseHandlers.java # Recurring expense operations
│   │   ├── TransactionHandlers.java      # Transaction operations
│   │   └── ViewHandlers.java            # View and reporting operations
│   ├── manager/                          # Business logic managers
│   │   ├── RecurringExpenseManager.java  # Recurring expense management
│   │   └── TransactionManager.java       # Transaction management
│   ├── persistence/                      # Data persistence layer
│   │   └── DataPersistenceService.java  # JSON data storage
│   ├── transactions/                     # Transaction models
│   │   ├── Expense.java                  # Expense transaction model
│   │   ├── Income.java                   # Income transaction model
│   │   ├── RecurringExpense.java         # Recurring expense model
│   │   └── Transaction.java              # Base transaction model
│   └── ui/                              # User interface components
│       ├── DisplayUtils.java             # Display formatting utilities
│       ├── MenuSystem.java               # Menu system
│       └── UserInputHandler.java         # User input handling
└── test/java/org/gabx/expenses/         # Unit tests
    └── AppTest.java
```

## 💾 Data Storage

The application automatically saves your data in JSON format. Data is stored in:
- **Transactions**: `data/transactions.json`
- **Recurring Expenses**: `data/recurring_expenses.json`

Data is automatically saved after every operation, ensuring no data loss.

## 🎯 Usage Examples

### Adding a New Expense
1. Select "📝 Add Transactions" from the main menu
2. Choose "💸 Add Expense"
3. Enter amount, description, date, and category
4. Transaction is automatically saved

### Setting Up Recurring Expenses
1. Select "📝 Add Transactions" from the main menu
2. Choose "🔄 Add Recurring Expense"
3. Enter details and select frequency (Daily, Weekly, Monthly, etc.)
4. Set start date for when the recurring expense should begin
5. The system will automatically generate regular expenses

### Viewing Financial Reports
1. Select "📊 Reports & Analysis" from the main menu
2. Choose from various analysis options:
   - Monthly trend analysis
   - Category spending breakdown
   - Year-to-date summaries
   - Spending insights

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📦 Building for Distribution

Create an executable JAR with all dependencies:
```bash
mvn clean package
```

This creates `target/expenses-tracker.jar` which can be run with:
```bash
java -jar target/expenses-tracker.jar
```

## 🔧 Development

### Running in Development Mode
```bash
mvn clean compile exec:java
```

### Code Style
The project follows standard Java conventions and includes:
- Comprehensive error handling
- User-friendly input validation
- Clean separation of concerns
- Extensive commenting

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Gabx** - [GitHub](https://github.com/gsousav)

## 🙏 Acknowledgments

- Built with Java 11 and Maven
- Uses Jackson for JSON processing
- Inspired by the need for simple, effective personal finance tracking

---

**Happy Financial Tracking! 
