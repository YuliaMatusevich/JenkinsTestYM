package model.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseNewViewPage extends MainBasePage {

    @FindBy(id = "name")
    protected WebElement viewName;

    @FindBy(css = "label[for='hudson.model.ListView']")
    protected WebElement listViewType;

    @FindBy(css = "label[for='hudson.model.MyView']")
    protected WebElement myViewType;

    @FindBy(id = "ok")
    protected WebElement createButton;

    @FindBy(css = ".error")
    private WebElement errorMessageViewAlreadyExist;

    public BaseNewViewPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessageViewAlreadyExist() {

        return getWait(5).until(ExpectedConditions.visibilityOf(
                errorMessageViewAlreadyExist)).getText();
    }
}
