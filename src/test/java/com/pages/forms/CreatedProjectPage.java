package com.pages.forms;

import static org.openqa.selenium.By.xpath;
import aquality.selenium.forms.Form;
import com.utils.Config;

public class CreatedProjectPage extends Form {
    private static final String PROJECT_NAME = Config.getInstance().getProperties("project");

    public CreatedProjectPage() {
        super(xpath(String.format("//script[@type = 'text/javascript']/preceding::li[1][.='%1$s']", PROJECT_NAME)), String.format("%1$s page", PROJECT_NAME));
    }

    public String elementIsPresent(String testId) {
        return getElementFactory()
                .getLink(xpath(String.format("//table[@class = 'table']//a[@href = 'testInfo?testId=%1$s']", testId)), "New Test")
                .getText();
    }
}
