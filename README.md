# Selenium Test Automation Framework

FrameworkOne is a modular Selenium automation testing framework built using Java, TestNG, and Maven.
It follows Object-Oriented Programming (OOP) principles to promote reusability, maintainability, and scalability.

This framework uses:

Selenium WebDriver 4.x → browser automation

TestNG → test execution, reporting, annotations

Maven → dependency management, build lifecycle

Logback + SLF4J → logging support (ready for extension)


🛠 Dependencies

Defined in pom.xml:

Selenium Java 4.35.0 → browser automation

TestNG 7.8.0 → test framework

Logback + SLF4J → logging

Maven Compiler Plugin → compiles at Java 8

---

## 📌 Project Structure  

<img width="767" height="356" alt="Screenshot 2025-09-09 at 11 20 26 PM" src="https://github.com/user-attachments/assets/2953891c-a8d8-4965-9638-dea5922cde9c" />

🧩 OOP Concepts in FrameworkOne

1️⃣ Inheritance

TestLogin extends BaseTest

Shared logic (browser setup, teardown) is inherited from BaseTest.

Reduces duplication: all test classes can extend BaseTest.

2️⃣ Encapsulation

PageDriver encapsulates the WebDriver instance using ThreadLocal.

Exposes only getDriver() and setDriver().

Test classes don’t deal with raw driver lifecycle.

3️⃣ Composition

BaseTest uses (has-a) WebDriver instance.

TestLogin uses PageDriver to retrieve driver.

Composition allows multiple utilities (logging, waits, page objects) to be plugged in.

4️⃣ Abstraction (via TestNG Annotations)

@BeforeClass and @AfterClass abstract setup/teardown logic.

Test authors just write business tests without worrying about driver initialization.



⚙️ How It Works

Execution Flow

testng.xml triggers TestLogin class.

BaseTest.setUp() runs first (@BeforeClass) → launches Chrome, stores driver in PageDriver.

TestLogin.loginTest() executes → retrieves driver and runs test steps.

BaseTest.tearDown() runs last (@AfterClass) → quits browser.

Thread Safety

ThreadLocal<WebDriver> ensures driver isolation across parallel test runs.

Each thread/test gets its own WebDriver instance.



### `pom.xml`
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.selenium.automation</groupId>
    <artifactId>frameworkone</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    ...
</project>
```

---

### `BaseTest.java`
```java
package frameworkone.base.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    WebDriver driver = null;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        PageDriver.setDriver(driver);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
```

---

### `TestLogin.java`
```java
package frameworkone.base.test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import frameworkone.base.classes.BaseTest;
import frameworkone.base.classes.PageDriver;

public class TestLogin extends BaseTest {

    WebDriver driver = null;

    @Test
    public void loginTest() {
        driver = PageDriver.getDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        System.out.println(driver.getTitle().toString());
    }
}
```

---

### `PageDriver.java`
```java
package frameworkone.base.classes;

import org.openqa.selenium.WebDriver;

public class PageDriver {
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        threadDriver.set(driver);
    }
}
```

---

### `testng.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="FrameWorkOne">
    <test name="LoginTest">
        <classes>
            <class name="frameworkone.base.test.TestLogin"/>
        </classes>
    </test>
</suite>
```

---

## 🚀 How to Run  
1. Clone this repo  
2. Import into Eclipse/IntelliJ as a **Maven Project**  
3. Run tests with:  
   ```bash
   mvn clean test
   ```

Running from Eclipse

Right-click testng.xml → Run As → TestNG Suite
(make sure you’ve installed the TestNG Eclipse plugin)

--- 
