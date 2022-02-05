package com.steps.tests;

import static com.cucumber.ScenarioContext.setContext;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import com.utils.Config;
import org.testng.annotations.Test;

public class TestUiAndApi extends BaseTest{
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final String EMPTY_BODY = "{}";
    private static final String TOKEN_TARGET = "token/get";
    private static final String TESTS = "test/get/json";
    private static final String START_PAGE = Config.getInstance().getStartURL();

    @Test
    public void testSite() {
        setContext(Context.TOKEN, steps.getToken(TOKEN_TARGET));
        steps.assertTokenIsGenerated(EMPTY_BODY, SUCCESS_STATUS_CODE);

        steps.navigateToSite(START_PAGE);
        steps.assertProjectPageIsOpen();
        setContext(Context.PROJET_ID, steps.getProjectId());

        steps.goToNexagePage();
        steps.assertTestIsSortedDesc();
        steps.getTests(TESTS, ScenarioContext.getContext(Context.PROJET_ID).toString());
        steps.assertTestsFromApiEqualsToTestsFromSite();
    }
}
