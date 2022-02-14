package com.pages;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.By.cssSelector;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectPage extends Form {
    private static final By PROJECT_PAGE_XPATH = xpath("//div[@class = 'panel-heading' and contains(text(), 'Available projects')]");
    private final static By NEXAGE_XPATH = xpath("//div[@class = 'list-group']/a[. = 'Nexage']");
    private final static By ADD_PROJECT_XPATH = xpath("//button[@data-target = '#addProject']");
    private static final By PROJECTS_CSS = cssSelector(".list-group a");
    private final ILabel nexageLbl = getElementFactory().getLabel(NEXAGE_XPATH, "Link to Nexage page");
    private List<ILink> names = new ArrayList<>();

    public ProjectPage() {
        super(PROJECT_PAGE_XPATH, "Project page");
    }

    public void navigateToNexagePage() {
        nexageLbl.click();
    }

    public String getProjectId() {
        String attr = nexageLbl.getAttribute("href");
        return attr.substring(attr.length() - 1);
    }

    public void clickAddButton() {
        getElementFactory().getButton(ADD_PROJECT_XPATH, "Add project button").click();
    }

    public int findProject(String name) {
        List<ILink> projects = getElementFactory().findElements(PROJECTS_CSS, "Projects", ElementType.LINK);
        names = projects.
                stream()
                .filter(link -> link.getText().equals(name))
                .collect(Collectors.toList());
        return names.size();
    }

    public void clickNewProject() {
        names.get(0).click();
    }
}
