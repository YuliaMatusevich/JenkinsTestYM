package model.views;

import model.base.BaseEditViewPage;
import model.base.BaseNewViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewViewFromMyViewsPage<EditViewPage extends BaseEditViewPage> extends BaseNewViewPage {

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

    private final EditViewPage editViewPage;

    public NewViewFromMyViewsPage(WebDriver driver, EditViewPage editViewPage) {
        super(driver);
        this.editViewPage = editViewPage;
    }

    public NewViewFromMyViewsPage<EditGlobalViewPage> selectGlobalViewType() {
        globalViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditGlobalViewPage(getDriver()));
    }

    public NewViewFromMyViewsPage<EditListViewPage> selectListViewType() {
        listViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditListViewPage(getDriver()));
    }

    public NewViewFromMyViewsPage<EditMyViewPage> selectMyViewType() {
        myViewType.click();

        return new NewViewFromMyViewsPage<>(getDriver(), new EditMyViewPage(getDriver()));
    }

    public NewViewFromMyViewsPage<EditViewPage> setViewName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(viewName)).sendKeys(name);

        return this;
    }

    public EditViewPage clickCreateButton() {
        createButton.click();

        return editViewPage;
    }
}
