package com.steps.tests;

import static com.cucumber.ScenarioContext.setContext;
import com.cucumber.Context;
import com.steps.TestSteps;
import com.utils.BrowserManager;
import com.utils.Config;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseTest {
    private static final int SESSION_ID_LENGTH = 20;
    private final BrowserManager browserManager = new BrowserManager();
    protected TestSteps steps = new TestSteps();

    @BeforeSuite
    public void setUpDriver() throws UnknownHostException {
        setContext(Context.SESSION_ID, Config.getInstance().getRandomString(SESSION_ID_LENGTH));
        setContext(Context.HOST, InetAddress.getLocalHost().getHostName());
        browserManager.maximizeWindow();
        setContext(Context.BROWSER, browserManager.getBrowserNameAndVersion());
    }

    @BeforeMethod
    public void setMethodName(Method method) {
        setContext(Context.METHOD_NAME, method.getName());
    }

    @AfterSuite
    public void closeDriver() {
        browserManager.quitBrowser();
    }
}
