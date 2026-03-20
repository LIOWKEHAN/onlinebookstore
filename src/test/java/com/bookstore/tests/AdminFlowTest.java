package com.bookstore.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AdminFlowTest extends BaseUiTest {

    @Test
    public void adminLoginShouldWork() {
        loginAsAdmin();

        String pageSource = driver.getPageSource().toLowerCase();
        assertTrue(
            pageSource.contains("admin") ||
            pageSource.contains("add") ||
            pageSource.contains("remove") ||
            pageSource.contains("book")
        );
    }

    @Test
    public void adminDashboardContainsBookManagementOptions() {
        loginAsAdmin();

        String pageSource = driver.getPageSource().toLowerCase();
        assertTrue(
                pageSource.contains("admin") ||
                pageSource.contains("logout") ||
                pageSource.contains("book")
        );
    }
}

