package com.pages.forms;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.By.cssSelector;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectForm extends Form {
    private static final By PROJECT_FORM_CLASS = className("modal-dialog");
    private static final By PROJECT_NAME_ID = id("projectName");
    private static final By SAVE_PROJECT_XPATH = xpath("//*[@type = 'submit']");
    private static final By SUCCESS_CSS = cssSelector(".alert-success");

    public ProjectForm() {
        super(PROJECT_FORM_CLASS, "Add project form");
    }

    public void enterText(String project) {
        getElementFactory().getTextBox(PROJECT_NAME_ID, "Field to enter project name").type(project);
    }

    public void clickSaveButton() {
        getElementFactory().getButton(SAVE_PROJECT_XPATH, "Save project button").click();
    }

    public boolean elementIsDisplayed() {
        return getElementFactory().getLabel(SUCCESS_CSS, "Success save project text").state().isDisplayed();
    }
}
