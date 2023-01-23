package model.base.side_menu;

import model.RenameItemPage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseStatusSideMenuComponent<StatusPage extends BaseStatusPage<?, ?>> extends BaseSideMenuWithGenericComponent<StatusPage> {

    @FindBy(linkText = "Rename")
    private WebElement rename;

    public BaseStatusSideMenuComponent(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }

    public RenameItemPage<StatusPage> clickRename() {
        rename.click();

        return new RenameItemPage<>(getDriver(), page);
    }
}
