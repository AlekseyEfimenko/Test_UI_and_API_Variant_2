package com.pages.forms;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectForm extends Form {
    private static final By PROJECT_NAME_ID = id("projectName");
    private static final By SAVE_PROJECT_XPATH = xpath("//*[@type = 'submit']");
    private static final By SUCCESS_CLASS_NAME = className("alert alert-success");

    public ProjectForm() {
        super(className("modal-dialog"), "Add project form");
    }

    public void enterText(String project) {
        getElementFactory().getTextBox(PROJECT_NAME_ID, "Field to enter project name").type(project);
    }

    public void clickSaveButton() {
        getElementFactory().getButton(SAVE_PROJECT_XPATH, "Save project button").click();
    }

    public boolean elementIsDisplayed() {
        return getElementFactory().getLabel(SUCCESS_CLASS_NAME, "Success save project text").state().isDisplayed();
    }
}
