package com.steps.tests;

import static com.cucumber.ScenarioContext.setContext;
import static com.cucumber.ScenarioContext.getContext;
import static com.utils.Config.getInstance;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.data.EndPoints;
import com.data.StatusCode;
import com.utils.DataUtils;
import com.utils.TestResultListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestResultListener.class)
public class TestUiAndApi extends BaseTest{
    private static final String VARIANT = getInstance().getProperties("variant");
    private static final String EMPTY_BODY = "{}";
    private static final String START_PAGE = DataUtils.getInstance().getStartURL();
    private static final String PROJECT_NAME = getInstance().getRandomString(10);
    private static final String LOGIN = getInstance().getProperties("login");
    private static final String PASSWORD = getInstance().getProperties("password");
    private final DataUtils dataUtils = DataUtils.getInstance();

    @Test
    public void testSite() {
        setContext(Context.TEST_NAME, this.getClass().getName());
        setContext(Context.TOKEN, steps.getToken(EndPoints.TOKEN_TARGET.getValue(), VARIANT));
        steps.assertTokenIsGenerated(EMPTY_BODY, StatusCode.SUCCESS.getValue());

        steps.navigateToSite(START_PAGE, LOGIN, PASSWORD);
        steps.assertProjectPageIsOpen();
        setContext(Context.PROJECT_ID, steps.getProjectId());

        steps.goToNexagePage();
        steps.assertTestIsSortedDesc();
        steps.getTests(EndPoints.GET_TESTS.getValue(), ScenarioContext.getContext(Context.PROJECT_ID));
        steps.assertTestsFromApiEqualsToTestsFromSite();

        steps.navigateBack();
        setContext(Context.PROJECT_NAME, PROJECT_NAME);
        steps.addProject(PROJECT_NAME);
        steps.assertProjectIsCreated();
        steps.closeProjectForm();
        steps.assertProjectAddFormIsClosed();
        steps.refreshPage();
        steps.assertNewProjectIsInList(PROJECT_NAME);

        steps.navigateToCreatedProject();
        setContext(Context.TEST_ID, steps.addTest(EndPoints.ADD_TEST.getValue(), dataUtils.getParametersForTestAdd()));
        steps.assertAddedTestIsPresent(getContext(Context.TEST_ID));
    }

    @AfterTest
    public void sendLogs() {
        steps.addLogs(EndPoints.ADD_LOGS.getValue(), dataUtils.getParametersForLogsAdd());
        steps.addScreenshot(EndPoints.ADD_CONTENT.getValue(), dataUtils.getParametersForScreenshotAdd());
    }
}
