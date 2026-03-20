package com.bookstore.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ReceiptFlowTest extends BaseUiTest {

    @Test
    public void applicationLandingPageLoads() {
        openHomePage();
        String pageSource = driver.getPageSource().toLowerCase();
        assertTrue(pageSource.contains("book") || pageSource.contains("store"));
    }
}