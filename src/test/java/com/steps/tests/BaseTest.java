package com.steps.tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.steps.TestSteps;
import org.openqa.selenium.Cookie;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    private Browser browser;
    protected TestSteps steps = new TestSteps();

    @BeforeSuite
    public void setUpDriver() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @AfterSuite
    public void closeDriver() {
        browser.quit();
    }
}
