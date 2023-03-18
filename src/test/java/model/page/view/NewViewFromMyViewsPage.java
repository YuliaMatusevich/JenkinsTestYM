package model.page.view;

import io.qameta.allure.Step;
import model.page.base.BaseEditViewPage;
import model.page.base.BaseNewViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewFromMyViewsPage<EditViewPage extends BaseEditViewPage> extends BaseNewViewPage {

    @FindBy(css = "label[for='hudson.model.ProxyView']")
    private WebElement globalViewType;

    private final EditViewPage editViewPage;

    public NewViewFromMyViewsPage(WebDriver driver, EditViewPage editViewPage) {
        super(driver);
        this.editViewPage = editViewPage;
    }

    public NewViewFromMyViewsPage<EditGlobalViewPage> selectGlobalViewType() {
        globalViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditGlobalViewPage(getDriver()));
    }

    @Step("Select list view type in New View From my Views Page")
    public NewViewFromMyViewsPage<EditListViewPage> selectListViewType() {
        listViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditListViewPage(getDriver()));
    }

    @Step("Select 'My View' type on the 'New View' Page going From 'My Views' Page")
    public NewViewFromMyViewsPage<EditMyViewPage> selectMyViewType() {
        myViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditMyViewPage(getDriver()));
    }
    @Step("Set view name '{name}' in New View From my Views Page")
    public NewViewFromMyViewsPage<EditViewPage> setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    @Step("Click on 'Create Button'")
    public EditViewPage clickCreateButton() {
        createButton.click();

        return editViewPage;
    }
}
