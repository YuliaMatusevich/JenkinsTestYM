package model.page.view;

import io.qameta.allure.Step;
import model.page.base.BaseEditViewPage;
import model.page.base.BaseNewViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewFromDashboardPage<EditViewPage extends BaseEditViewPage> extends BaseNewViewPage {

    private final EditViewPage editViewPage;

    public NewViewFromDashboardPage(WebDriver driver, EditViewPage editViewPage) {
        super(driver);
        this.editViewPage = editViewPage;
    }

    @Step("Select 'List View' type")
    public NewViewFromDashboardPage<EditListViewPage> selectListViewType() {
        listViewType.click();

        return new NewViewFromDashboardPage<>(getDriver(), new EditListViewPage(getDriver()));
    }

    public NewViewFromDashboardPage<EditMyViewPage> selectMyViewType() {
        myViewType.click();

        return new NewViewFromDashboardPage<>(getDriver(), new EditMyViewPage(getDriver()));
    }

    @Step("Input view name '{name}' into 'Name field'")
    public NewViewFromDashboardPage<EditViewPage> setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    @Step("Click on 'Create' button")
    public EditViewPage clickCreateButton() {
        createButton.click();

        return editViewPage;
    }
}
