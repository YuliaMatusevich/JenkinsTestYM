package model.base.side_menu;

import model.DeletePage;
import model.HomePage;
import model.RenameItemPage;
import model.base.BasePage;
import model.base.BaseStatusPage;
import model.folder.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseStatusSideMenuComponent<StatusPage extends BaseStatusPage<?, ?>> extends BaseSideMenuWithGenericComponent<StatusPage> {

    @FindBy(linkText = "Rename")
    private WebElement rename;

    @FindBy(xpath = "//div[@id='tasks']//span[contains(., 'Delete')]")
    private WebElement delete;

    public BaseStatusSideMenuComponent(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }

    public RenameItemPage<StatusPage> clickRename() {
        rename.click();

        return new RenameItemPage<>(getDriver(), page);
    }

    private <T extends BasePage> T clickDelete(T toPage) {
        delete.click();

        return toPage;
    }

    public StatusPage clickDeleteToMyStatusPage() {
        return clickDelete(page);
    }

    public DeletePage<HomePage> clickDeleteToHomePage() {
        return clickDelete(new DeletePage<>(getDriver(), new HomePage(getDriver())));
    }

    public DeletePage<FolderStatusPage> clickDeleteToFolder() {
        return clickDelete(new DeletePage<>(getDriver(), new FolderStatusPage(getDriver())));
    }
}
