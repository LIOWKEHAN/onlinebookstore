package com.bookstore.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class UserFlowTest extends BaseUiTest {

    @Test
    public void homepageLoads() {
        openHomePage();
        String pageSource = driver.getPageSource().toLowerCase();
        assertTrue(pageSource.contains("book") || pageSource.contains("online") || pageSource.contains("store"));
    }

    @Test
    public void loginPageOpens() {
        openCustomerLoginPage();
        assertTrue(driver.findElement(By.name("username")).isDisplayed());
        assertTrue(driver.findElement(By.name("password")).isDisplayed());
    }
}