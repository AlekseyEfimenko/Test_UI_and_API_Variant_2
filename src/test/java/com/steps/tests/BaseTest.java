package com.steps.tests;

import static com.cucumber.ScenarioContext.setContext;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import com.cucumber.Context;
import com.steps.TestSteps;
import com.utils.Config;
import org.openqa.selenium.Capabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseTest {
    private static final int SESSION_ID_LENGTH = 20;
    private Browser browser;
    protected TestSteps steps = new TestSteps();

    @BeforeSuite
    public void setUpDriver() throws UnknownHostException {
        setContext(Context.SESSION_ID, Config.getInstance().getRandomString(SESSION_ID_LENGTH));
        setContext(Context.HOST, InetAddress.getLocalHost().getHostName());
        browser = AqualityServices.getBrowser();
        browser.maximize();
        Capabilities cp = browser.getDriver().getCapabilities();
        setContext(Context.BROWSER, String.format("%1$s %2$s", cp.getBrowserName(), cp.getVersion()));
    }

    @BeforeMethod
    public void setMethodName(Method method) {
        setContext(Context.METHOD_NAME, method.getName());
    }

    @AfterSuite
    public void closeDriver() {
        browser.quit();
    }
}
