package com.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertFalse;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.data.Keys;
import com.data.Values;
import com.pages.NexagePage;
import com.pages.ProjectPage;
import com.pages.forms.CreatedProjectPage;
import com.pages.forms.ProjectForm;
import com.pojo.Test;
import com.utils.ApiUtils;
import com.utils.Config;
import com.utils.DataUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TestSteps {
    private final Browser browser = AqualityServices.getBrowser();
    private final ApiUtils apiUtils = ApiUtils.getInstance();
    private final Logger logger = Logger.getInstance();
    private final ProjectPage projectPage = new ProjectPage();
    private final NexagePage nexagePage = new NexagePage();
    private final CreatedProjectPage createdProject = new CreatedProjectPage();
    private final Config config = Config.getInstance();
    private ProjectForm projectForm;
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

    public void navigateToSite(String url, String login, String password) {
        browser.goTo(String.format("http://%1$s:%2$s@%3$s", login, password, url));
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
        apiUtils.postRequest(target, Keys.PROJECT_ID.getKey(), value);
        testFromApi = DataUtils.getInstance().getListOfTests();
    }

    public void assertTestsFromApiEqualsToTestsFromSite() {
        logger.info("Checking if the list of tests from the first page of site equals to the list of tests from Api");
        assertEquals(nexagePage.getTestNames(), getListOfTestNamesFromApi());
    }

    public void navigateBack() {
        browser.goBack();
    }

    public void addProject(String project) {
        logger.info("Adding new project");
        projectPage.clickAddButton();
        projectForm = new ProjectForm();
        projectForm.enterText(project);
        projectForm.clickSaveButton();
    }

    public void assertProjectIsCreated() {
        logger.info("Checking if the project created successful");
        assertFalse(projectForm.elementIsDisplayed());
    }

    public void closeProjectForm() {
        logger.info("Closing Add-project form");
        browser.executeScript("document.querySelector('.modal').style.display='none'");
        browser.executeScript("document.querySelector('.modal-backdrop').style.display='none'");
    }

    public void assertProjectAddFormIsClosed() {
        logger.info("Checking if the form for adding new project is disappeared");
        assertFalse(projectForm.state().isDisplayed());
    }

    public void refreshPage() {
        browser.refresh();
    }

    public void assertNewProjectIsInList(String name) {
        logger.info("Checking if new project is displayed in the list of projects");
        assertTrue(projectPage.findProject(name) > 0);
    }

    public void navigateToCreatedProject() {
        logger.info("Navigate to new project");
        projectPage.clickNewProject();
    }

    public String addTest(String target, Map<String, String> parameters) {
        logger.info("Add new test to project ang get id of new test");
        apiUtils.postRequest(target, parameters);
        return apiUtils.getBody();
    }

    public void assertAddedTestIsPresent(String testId) {
        logger.info("Checking if new test is present on the page");
        assertEquals(createdProject.elementIsPresent(testId), ScenarioContext.getContext(Context.TEST_NAME));
    }

    public void addLogs(String target, Map<String, String> parameters) {
        logger.info("Add logs of new test");
        apiUtils.postRequest(target, parameters);
    }

    public void addScreenshot(String target, Map<String, String> parameters) {
        logger.info("Add screenshot of current page");
        getScreenshot();
        apiUtils.postRequest(target, parameters);
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

    private void getScreenshot() {
        ScenarioContext.setContext(Context.SCREENSHOT, ((TakesScreenshot) browser.getDriver()).getScreenshotAs(OutputType.BASE64));
    }
}
