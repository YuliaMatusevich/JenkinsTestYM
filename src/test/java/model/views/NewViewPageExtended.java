package model.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewViewPageExtended extends NewViewPage<NewViewPageExtended> {

    @FindBy(css = "label[for='hudson.model.ProxyView']")
    private WebElement includeGlobalView;

    public NewViewPageExtended(WebDriver driver) {
        super(driver);
    }

    public NewViewPageExtended selectIncludeGlobalView() {
        includeGlobalView.click();

        return this;
    }
}
