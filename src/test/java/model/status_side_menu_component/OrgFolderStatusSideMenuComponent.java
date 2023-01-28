package model.status_side_menu_component;

import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.OrgFolderConfigPage;
import model.status_pages.OrgFolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrgFolderStatusSideMenuComponent extends BaseStatusSideMenuComponent<OrgFolderStatusPage> {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    public OrgFolderStatusSideMenuComponent(WebDriver driver, OrgFolderStatusPage statusPage) {
        super(driver, statusPage);
    }

    public OrgFolderConfigPage clickConfigure() {
        configure.click();

        return new OrgFolderConfigPage(getDriver());
    }
}
