package model.views;

import model.base.BaseEditViewPage;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewPage<Self extends NewViewPage<?>> extends MainBasePage {

    @FindBy(id = "name")
    private WebElement viewName;

    @FindBy(css = ".error")
    private WebElement validationErrorMessage;

    @FindBy(css = "label[for='hudson.model.ListView']")
    private WebElement listView;

    @FindBy(css = "label[for='hudson.model.MyView']")
    private WebElement myView;

    @FindBy(id = "ok")
    private WebElement createButton;

    public NewViewPage(WebDriver driver) {
        super(driver);
    }

    public Self setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return (Self)this;
    }

    public String getValidationErrorMessage() {
        return getWait(5).until(ExpectedConditions.visibilityOf(validationErrorMessage)).getText();
    }

    public Self selectListView() {
        listView.click();

        return (Self)this;
    }

    public Self selectMyView() {
        myView.click();

        return (Self)this;
    }

    public <T extends BaseEditViewPage> T clickCreateButton(T editViewPage) {
        createButton.click();

        return editViewPage;
    }
}
