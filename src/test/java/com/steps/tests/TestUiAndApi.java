package com.steps.tests;

import static com.cucumber.ScenarioContext.setContext;
import static com.utils.Config.getInstance;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.utils.DataUtils;
import org.testng.annotations.Test;

public class TestUiAndApi extends BaseTest{
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final String EMPTY_BODY = "{}";
    private static final String TOKEN_TARGET = "token/get";
    private static final String GET_TESTS = "test/get/json";
    private static final String ADD_TEST = "test/put";
    private static final String START_PAGE = DataUtils.getInstance().getStartURL();
    private static final String PROJECT_NAME = getInstance().getProperties("project");
    private static final String LOGIN = getInstance().getProperties("login");
    private static final String PASSWORD = getInstance().getProperties("password");

    @Test
    public void testSite() {
        setContext(Context.TEST_NAME, this.getClass().getName());
        setContext(Context.TOKEN, steps.getToken(TOKEN_TARGET));
        steps.assertTokenIsGenerated(EMPTY_BODY, SUCCESS_STATUS_CODE);

        steps.navigateToSite(START_PAGE, LOGIN, PASSWORD);
        steps.assertProjectPageIsOpen();
        setContext(Context.PROJET_ID, steps.getProjectId());

        steps.goToNexagePage();
        steps.assertTestIsSortedDesc();
        steps.getTests(GET_TESTS, ScenarioContext.getContext(Context.PROJET_ID));
        steps.assertTestsFromApiEqualsToTestsFromSite();

        steps.navigateBack();
        steps.addProject(PROJECT_NAME);
        steps.assertProjectIsCreated();
        steps.closeProjectForm();
        steps.assertProjectAddFormIsClosed();
        steps.refreshPage();
        steps.assertNewProjectIsInList(PROJECT_NAME);

        steps.navigateToCreatedProject();
        setContext(Context.TEST_ID, steps.addTest(ADD_TEST, DataUtils.getInstance().getParameters()));
    }
}
