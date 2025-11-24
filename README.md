# 🧪 Selenium Automation Project – Automation Exercise Website

## 📌 Overview
This project is a Selenium WebDriver automation framework built using Java and the Page Object Model (POM) design pattern.
It covers complete test flows for the Automation Exercise
 web application, including Sign-Up, Sign-In, Cart, Checkout, and Sign-Out functionalities. Both Happy and Sad scenarios are included for full coverage.

## 🧱 Project Structure

```
📦 project-root
├── src
│   ├── main/java/PageObjects
│   │   ├── P01_SignUp.java          # Page Object for Sign-Up
│   │   ├── P02_SignIn.java          # Page Object for Sign-In
│   │   ├── P03_Cart.java            # Page Object for Cart
│   │   ├── P04_CheckOut.java        # Page Object for Checkout
│   │   └── P05_SignOut.java         # Page Object for Sign-Out
│   ├── test/java/TestCases
│   │   ├── T01_SignUp.java          # Test cases for Sign-Up flow
│   │   ├── T02_SignIn.java          # Test cases for Sign-In flow
│   │   ├── T03_Cart.java            # Test cases for Cart flow
│   │   ├── T04_CheckOut.java        # Test cases for Checkout flow
│   │   └── T05_SignOut.java         # Test cases for Sign-Out flow
│   └── test/resources
│       └── TestData.json            # Contains test data (user credentials, products, etc.)
├── SetUp
│   ├── SuperClass.java              # Base class for WebDriver setup
│   └── Listeners.java               # TestNG listeners for reporting and screenshots
├── TestNG_AllScenarios.xml          # Runs all scenarios
├── TestNG_HappyScenarios.xml        # Runs only Happy scenarios
├── TestNG_SadScenarios.xml          # Runs only Sad scenarios
├── pom.xml                           # Maven dependencies
└── README.md                         # Project documentation

```

## ⚙️ Installation and Setup
```
1️⃣ Clone the repository

git clone https://github.com/IbrahimMohamedFahmy/selenium_project_using_page_object_module_design_pattern.git
cd selenium_project_using_page_object_module_design_pattern

2️⃣ Install dependencies

mvn clean install


3️⃣ Run the tests

Run all scenarios:

mvn test -DsuiteXmlFile=TestNG_AllScenarios.xml


Run only Happy scenarios:

mvn test -DsuiteXmlFile=TestNG_HappyScenarios.xml


Run only Sad scenarios:

mvn test -DsuiteXmlFile=TestNG_SadScenarios.xml


🧠 Design Pattern: Page Object Model (POM)

Each page (Sign-Up, Sign-In, Cart, Checkout, Sign-Out) is represented as a separate class in PageObjects/ to:

Increase reusability

Simplify maintenance

Allow refactoring without breaking other flows

Reusable methods for interacting with web elements are stored in Page Object classes for DRY principles.

🧾 Reporting

Test results are captured via TestNG Listeners in Listeners.java.

Screenshots are taken on test failures automatically.

Can be integrated with reporting tools like Allure or ExtentReports for advanced reporting.

💡 Best Practices Followed

Using @BeforeMethod and @BeforeClass hooks for setup and configuration.

Using JSON fixture for test data management.

Applying assertions for functional validations.

Clear naming convention for test cases (T01, T02, …).

Following modular and scalable structure.

Added both Happy and Sad scenarios for full coverage.

🌐 Target Website
This framework is built for: Automation Exercise

🧑‍💻 Author
Ibrahim Mohamed Omran – QA Automation Engineer
Passionate about software testing, automation, and code quality.

📅 Last Updated
November 2025
