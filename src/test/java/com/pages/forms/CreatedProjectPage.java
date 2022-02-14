package com.pages.forms;

import static org.openqa.selenium.By.xpath;
import aquality.selenium.forms.Form;
import com.cucumber.Context;
import com.cucumber.ScenarioContext;
import org.openqa.selenium.By;

public class CreatedProjectPage extends Form {
    private static final String NEW_TEST_LOCATOR = "//table[@class = 'table']//a[@href = 'testInfo?testId=%1$s']";
    private static final String PROJECT_NAME = ScenarioContext.getContext(Context.PROJECT_NAME);
    private static final By CREATE_PROJECT_XPATH = xpath(String.format("//script[@type = 'text/javascript']/preceding::li[1][.='%1$s']", PROJECT_NAME));

    public CreatedProjectPage() {
        super(CREATE_PROJECT_XPATH, String.format("%1$s page", PROJECT_NAME));
    }

    public String elementIsPresent(String testId) {
        return getElementFactory()
                .getLink(xpath(String.format(NEW_TEST_LOCATOR, testId)), "New Test")
                .getText();
    }
}
