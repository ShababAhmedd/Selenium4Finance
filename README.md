# Daily Finance Automation Framework

This is a Selenium-based test automation project using Java, TestNG, and the Page Object Model (POM) design pattern. It automates critical user workflows on [Daily Finance App](https://dailyfinance.roadtocareer.net/), including registration, password reset, profile updates, adding daily costs, search box, admin verification, and data scraping.


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
- **Gradle** – Project and dependency management
- **REST Assured** – For Gmail API integration and email validation
- **Allure** – For test reporting
- **Git & GitHub** – Version control


##  Setup Instructions

1. **Clone the repository**

   ```bash
      git clone https://github.com/your-username/daily-finance-automation.git
      cd daily-finance-automation
   ```

 2. **Open the project in IntelliJ IDEA or your preferred IDE**

   - Select “Import Project”
   - Choose **Gradle** as the build system
  
3. **Let Gradle resolve all dependencies**
   - Dependencies are managed in `build.gradle`
   - Gradle will automatically download them on when "Sync All Gradle Projects" is clicked.

4. **Configure Gmail API Token**

   - Open `src/test/resources/config.properties`
   - Add your Gmail access token like this:
     ```base
        g_token = your_generated_gmail_token
     ```

##  Running the Tests
- To execute the entire regression suite, run:
   ```bash
   gradle clean test -PsuiteName="regressionSuite.xml"
   ```

- To run with admin credentials (securely passed via CLI):
   ```bash
   gradle clean test -PsuiteName="regressionSuite.xml" -Pemail="admin@test.com" -Ppassword="admin123"
   ```
- Individual test classes can also be run via CLI or IDE.
