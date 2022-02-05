package com.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import com.data.Keys;
import com.data.Values;
import com.pages.NexagePage;
import com.pages.ProjectPage;
import com.pojo.Test;
import com.utils.ApiUtils;
import com.utils.Config;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestSteps {
    private static final String LOGIN = Config.getInstance().getProperties("login");
    private static final String PASSWORD = Config.getInstance().getProperties("password");
    private final Browser browser = AqualityServices.getBrowser();
    private final ApiUtils apiUtils = ApiUtils.getInstance();
    private final Logger logger = Logger.getInstance();
    private final ProjectPage projectPage = new ProjectPage();
    private final NexagePage nexagePage = new NexagePage();
    private final Config config = Config.getInstance();
    private List<Test> testFromApi = new ArrayList<>();

    public String getToken(String target) {
        logger.info("Getting token from api-request");
        apiUtils.postRequest(target, Keys.VARIANT.getKey(), Values.V_VARIANT.getValue());
        return apiUtils.getBody();
    }

    public void assertTokenIsGenerated(String body, int statusCode) {
        logger.info("Checking if token is generated");
        assertStatusCodeIsCorrect(statusCode);
        assertBodyIsNotEmpty(body);
    }

    public void navigateToSite(String url) {
        browser.goTo(String.format("http://%1$s:%2$s@%3$s", LOGIN, PASSWORD, url));
        browser.waitForPageToLoad();
    }

    public void assertProjectPageIsOpen() {
        logger.info("Checking if Project page is open");
        assertTrue(projectPage.state().isDisplayed());
    }

    public void goToNexagePage() {
        logger.info("Navigate to Nexage page");
        projectPage.navigateToNexagePage();
    }

    public void assertTestIsSortedDesc() {
        logger.info("Checking if the list of tests is sorted by date desc");
        List<String> list = nexagePage.getListOfStartTime();
        config.reverseList(list);
        assertTrue(config.isSorted(list));
    }

    public String getProjectId() {
        return projectPage.getProjectId();
    }

    public void getTests(String target, String value) {
        logger.info("Getting list of tests from Api");
        apiUtils.postRequest(target, Keys.PROJECTID.getKey(), value);
        testFromApi = apiUtils.getListOfTests();
    }

    public void assertTestsFromApiEqualsToTestsFromSite() {
        logger.info("Checking if the list of tests from the first page of site equals to the list of tests from Api");
        assertEquals(nexagePage.getTestNames(), getListOfTestNamesFromApi());
    }

    private List<String> getListOfTestNamesFromApi() {
        List<String> testName = new ArrayList<>();
        sortListByStartTime().forEach(test -> testName.add(test.getName()));
        return testName;
    }

    private List<Test> sortListByStartTime() {
        List<Test> sortedTests = filterTestsFromRequest();
        sortedTests.sort(Comparator.comparing(Test::getStartTime));
        config.reverseList(sortedTests);
        return sortedTests;
    }

    private List<Test> filterTestsFromRequest() {
        List<String> testNames = nexagePage.getTestNames();
        List<Test> filterTests = new ArrayList<>();
        for (Test test : testFromApi) {
            if (testNames.contains(test.getName())) {
                filterTests.add(test);
            }
        }
        return filterTests;
    }

    private void assertBodyIsNotEmpty(String body) {
        logger.info("Checking if request body is not empty");
        assertNotEquals(apiUtils.getBody(), body);
    }

    private void assertStatusCodeIsCorrect(int statusCode) {
        logger.info(String.format("Checking if status code of request equals to %1$s", statusCode));
        assertEquals(apiUtils.getStatusCode(), statusCode);
    }
}
