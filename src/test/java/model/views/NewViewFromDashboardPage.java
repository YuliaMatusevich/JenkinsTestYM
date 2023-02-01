package model.views;

import model.base.BaseEditViewPage;
import model.base.BaseNewViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewFromDashboardPage<EditViewPage extends BaseEditViewPage> extends BaseNewViewPage {

    @FindBy(id = "name")
    private WebElement viewName;

    @FindBy(css = "label[for='hudson.model.ListView']")
    private WebElement listViewType;

    @FindBy(css = "label[for='hudson.model.MyView']")
    private WebElement myViewType;

    @FindBy(id = "ok")
    private WebElement createButton;

    private final EditViewPage editViewPage;

    public NewViewFromDashboardPage(WebDriver driver, EditViewPage editViewPage) {
        super(driver);
        this.editViewPage = editViewPage;
    }

    public NewViewFromDashboardPage<EditListViewPage> selectListViewType() {
        listViewType.click();

        return new NewViewFromDashboardPage<>(getDriver(), new EditListViewPage(getDriver()));
    }

    public NewViewFromDashboardPage<EditMyViewPage> selectMyViewType() {
        myViewType.click();

        return new NewViewFromDashboardPage<>(getDriver(), new EditMyViewPage(getDriver()));
    }

    public NewViewFromDashboardPage<EditViewPage> setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    public EditViewPage clickCreateButton() {
        createButton.click();

        return editViewPage;
    }
}
