package com.steps;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.utils.ApiUtils;
import org.openqa.selenium.Cookie;
import org.testng.Assert;

public class TestSteps {
    private final Browser browser = AqualityServices.getBrowser();
    private final ApiUtils apiUtils = ApiUtils.getInstance();
    private final Logger logger = Logger.getInstance();

    public String getToken(String target) {
        logger.info("Getting token from api-request");
        apiUtils.postRequest(target);
        return apiUtils.getBody();
    }

    public void assertTokenIsGenerated(String body, int statusCode) {
        logger.info("Checking if token is generated");
        assertStatusCodeIsCorrect(statusCode);
        assertBodyIsNotEmpty(body);
    }

    public void navigateToSite(String url) {
        browser.goTo(url);
        browser.waitForPageToLoad();
    }

    private void assertBodyIsNotEmpty(String body) {
        logger.info("Checking if request body is not empty");
        Assert.assertNotEquals(apiUtils.getBody(), body);
    }

    private void assertStatusCodeIsCorrect(int statusCode) {
        logger.info(String.format("Checking if status code of request equals to %1$s", statusCode));
        Assert.assertEquals(apiUtils.getStatusCode(), statusCode);
    }
}
