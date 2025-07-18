# Saucedemo Automation Using Java

This repository contains an automated test suite for the SauceDemo web application. The project is designed to verify
the functionality of the e-commerce platform using Selenium WebDriver, TestNG, and Java. It provides end-to-end coverage
for critical features like login, product browsing, cart operations, and checkout.

## 🛠️ Setup & Installation

### 🔧 Prerequisites

Make sure the following tools are installed on your system before running the tests:

| Tool                | Version                    | Descirption                       |
|---------------------|----------------------------|-----------------------------------|
| Java                | 21+                        | Required to compile and run tests |
| Git                 | Latest                     | To clone the repository           |
| Browser Chrome/Edge | Latest version recommended | -                                 |

Java 21 Required to compile and run tests
Maven 3.6+ For build and dependency management
Git Latest To clone the repository
Browser Chrome/Edge Latest version recommended

1. 📥 Clone the repo:

```bash
git clone https://github.com/This-swapnil/saucedemo-automation-java.git
```

2. Navigate to project folder:

```bash
cd saucedemo-automation-java
```

3. 📦 Install dependencies:

```bash
mvn clean install
```

4. ▶️ Run Test Suite Using master.xml

```bash 
run master.xml
```

## 📌 Features Covered

- ✅ Valid & Invalid Login Scenarios

- 🛒 Add/Remove Products from Cart

- 🔎 Product Details Page Verification

- 📦 Checkout Flow Automation

- 🧾 Order Summary Validation

- 🚫 Error Handling and Edge Cases

- 📸 Screenshot capture on failure

- 🔧 Configurable test environment

## 🚀 Technologies Used

- Language: Java

- Automation Framework: Selenium WebDriver

- Test Framework: TestNG

- Design Pattern: Page Object Model (POM)

- Reporting: ExtentReports

- Logging: Log4j

## 📊 Test Reports & Screenshots

After execution, test reports will be generated under the reports/ and /screenshot directory. Reports include pass/fail
status, screenshots, and test logs.