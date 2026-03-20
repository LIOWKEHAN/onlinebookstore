package com.bookstore.tests;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseUiTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = System.getProperty("baseUrl", "http://localhost:8080/onlinebookstore");

    @BeforeEach
    public void setUp() {
        String driverPath = "C:\\EdgeWebDriver\\msedgedriver.exe";

        if (!new File(driverPath).exists()) {
            throw new RuntimeException("EdgeDriver not found at: " + driverPath);
        }

        System.setProperty("webdriver.edge.driver", driverPath);

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void openHomePage() {
        driver.get(baseUrl);
    }

    protected void clickHomeLoginLink() {
        WebElement loginLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.linkText("Login"))
        );
        loginLink.click();
    }

    protected void openCustomerLoginPage() {
        openHomePage();
        clickHomeLoginLink();
    }

    protected void openAdminLoginPage() {
        openCustomerLoginPage();
        clickAdminLoginSwitcher();
    }

    protected void clickAdminLoginSwitcher() {
        List<By> possibleSelectors = List.of(
            By.linkText("Admin Login"),
            By.partialLinkText("Admin"),
            By.xpath("//a[contains(text(),'Admin')]"),
            By.xpath("//button[contains(text(),'Admin')]"),
            By.xpath("//input[@type='button' and contains(@value,'Admin')]"),
            By.xpath("//input[@type='submit' and contains(@value,'Admin')]")
        );

        for (By selector : possibleSelectors) {
            try {
                WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(selector)
                );
                element.click();
                return;
            } catch (Exception ignored) {
            }
        }

        throw new NoSuchElementException("Could not find Admin Login switch button/link on customer login page.");
    }

    protected void loginAsAdmin() {
        openAdminLoginPage();

        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        WebElement passwordField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("password"))
        );

        usernameField.clear();
        usernameField.sendKeys("Admin");

        passwordField.clear();
        passwordField.sendKeys("Admin");

        passwordField.submit();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}