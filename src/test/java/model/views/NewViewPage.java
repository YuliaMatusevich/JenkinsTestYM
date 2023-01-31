package model.views;

import model.base.BaseEditViewPage;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewPage<EditViewPage extends BaseEditViewPage> extends MainBasePage {

    @FindBy(id = "name")
    private WebElement viewName;

    @FindBy(css = "label[for='hudson.model.ProxyView']")
    private WebElement globalViewType;

    @FindBy(css = "label[for='hudson.model.ListView']")
    private WebElement listViewType;

    @FindBy(css = "label[for='hudson.model.MyView']")
    private WebElement myViewType;

    @FindBy(id = "ok")
    private WebElement createButton;

    @FindBy(css = ".error")
    private WebElement errorMessageViewAlreadyExist;

    private final EditViewPage editViewPage;

    public NewViewPage(WebDriver driver, EditViewPage editViewPage) {
        super(driver);
        this.editViewPage = editViewPage;
    }

    public NewViewPage<EditGlobalViewPage> selectGlobalViewType() {
        globalViewType.click();

        return new NewViewPage<>(getDriver(), new EditGlobalViewPage(getDriver()));
    }

    public NewViewPage<EditListViewPage> selectListViewType() {
        listViewType.click();

        return new NewViewPage<>(getDriver(), new EditListViewPage(getDriver()));
    }

    public NewViewPage<EditMyViewPage> selectMyViewType() {
        myViewType.click();

        return new NewViewPage<>(getDriver(), new EditMyViewPage(getDriver()));
    }

    public NewViewPage<?> setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    public EditViewPage clickCreateButton() {
        createButton.click();

        return editViewPage;
    }

    public String getErrorMessageViewAlreadyExist() {

        return getWait(5).until(ExpectedConditions.visibilityOf(
                errorMessageViewAlreadyExist)).getText();
    }
}
