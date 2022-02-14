package com.pages;

import static org.openqa.selenium.By.xpath;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.List;

public class NexagePage extends Form {
    private static final By NEXAGE_PAGE_XPATH = xpath("//script[@type = 'text/javascript']/preceding::li[1][.='Nexage']");
    private static final By START_TIME_XPATH = xpath("//table[@class = 'table']//tr/td[4]");
    private static final By TEST_NAME_XPATH = xpath("//table[@class = 'table']//a[contains(@href, 'Info?testId')]");

    public NexagePage() {
        super(NEXAGE_PAGE_XPATH, "Nexage page");
    }

    public List<String> getListOfStartTime() {
        List<ITextBox> startTimeTbxs = getElementFactory().findElements(START_TIME_XPATH, ElementType.TEXTBOX);
        return getListOfElementsText(startTimeTbxs);
    }

    public List<String> getTestNames() {
        List<ITextBox> testNameTbxs = getElementFactory().findElements(TEST_NAME_XPATH, ElementType.TEXTBOX);
        return getListOfElementsText(testNameTbxs);
    }

    private <T extends IElement> List<String> getListOfElementsText(List<T> list) {
        List<String> elementText = new ArrayList<>();
        list.forEach(element -> elementText.add(element.getText()));
        return elementText;
    }
}
