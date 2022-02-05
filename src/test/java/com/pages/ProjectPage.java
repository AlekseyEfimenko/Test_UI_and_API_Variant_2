package com.pages;

import static org.openqa.selenium.By.xpath;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectPage extends Form {
    private final static By NEXAGE_XPATH = xpath("//div[@class = 'list-group']/a[. = 'Nexage']");
    private final ILabel nexageLbl = getElementFactory().getLabel(NEXAGE_XPATH, "Link to Nexage page");

    public ProjectPage() {
        super(xpath("//div[@class = 'panel-heading' and contains(text(), 'Available projects')]"), "Project page");
    }

    public void navigateToNexagePage() {
        nexageLbl.click();
    }

    public String getProjectId() {
        String attr = nexageLbl.getAttribute("href");
        return attr.substring(attr.length() - 1);
    }
}
