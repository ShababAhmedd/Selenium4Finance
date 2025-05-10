# Daily Finance Automation Framework

This is a Selenium-based test automation project using Java, TestNG, and the Page Object Model (POM) design pattern. It automates critical user workflows on [Daily Finance App](https://dailyfinance.roadtocareer.net/), including registration, password reset, profile updates, admin verification, and data scraping.


##  Features Covered

-  User registration with dynamic Gmail ID
-  Email confirmation via Gmail API
-  Password reset with negative and positive test cases
-  Login with updated credentials
-  Profile update with email change
-  Admin login and verification of updated user
-  Registration of users from CSV file
-  Web table data scraping and writing to a text file
-  All tests follow the POM (Page Object Model) structure


##  Tech Stack Used

- **Java** – Programming language
- **Selenium WebDriver** – For browser automation
- **TestNG** – Test framework for structuring and executing tests
- **Maven** – Project and dependency management
- **Apache Commons CSV** – For reading user data from CSV files
- **REST Assured** – For Gmail API integration and email validation
- **Allure** – For test reporting
- **Git & GitHub** – Version control


##  Setup Instructions

1. **Clone the repository**

```bash
   git clone https://github.com/your-username/daily-finance-automation.git
   cd daily-finance-automation
```
 2. **Import the Project into Your IDE**

- Open **IntelliJ IDEA** or **Eclipse**
- Import the project as a **Maven project**
