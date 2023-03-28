package model.component.base;

import io.qameta.allure.Step;
import model.page.DeletePage;
import model.page.HomePage;
import model.page.MovePage;
import model.page.base.BaseConfigPage;
import model.page.base.BaseStatusPage;
import model.page.status.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseStatusSideMenuComponent<StatusPage extends BaseStatusPage<?, ?>, ConfigPage extends BaseConfigPage<?, ?>>
        extends BaseSideMenuWithGenericComponent<StatusPage> implements IMenu<StatusPage, ConfigPage> {

    @FindBy(linkText = "Move")
    private WebElement move;

    public abstract ConfigPage getConfigPage();

    public BaseStatusSideMenuComponent(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }

    public StatusPage getStatusPage() {
        return page;
    }

    @Step("Click 'Delete' on the side menu")
    public StatusPage clickDeleteToMyStatusPage() {
        return clickDelete(page);
    }

    @Step("Click 'Delete' on the side menu")
    public DeletePage<HomePage> clickDeleteToHomePage() {
        return clickDelete(new DeletePage<>(getDriver(), new HomePage(getDriver())));
    }

    @Step("Click on 'Delete Project' button")
    public DeletePage<FolderStatusPage> clickDeleteToFolder() {
        return clickDelete(new DeletePage<>(getDriver(), new FolderStatusPage(getDriver())));
    }

    @Step("Click on the 'Move' on the side menu")
    public MovePage<StatusPage> clickMove() {
        move.click();

        return new MovePage<>(getDriver(), page);
    }
}