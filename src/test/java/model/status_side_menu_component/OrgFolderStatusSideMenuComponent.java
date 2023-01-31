package model.status_side_menu_component;

import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.OrgFolderConfigPage;
import model.status_pages.OrgFolderStatusPage;
import org.openqa.selenium.WebDriver;

public class OrgFolderStatusSideMenuComponent extends BaseStatusSideMenuComponent<OrgFolderStatusPage, OrgFolderConfigPage> {

    @Override
    protected OrgFolderConfigPage createConfigPage() {
        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusSideMenuComponent(WebDriver driver, OrgFolderStatusPage statusPage) {
        super(driver, statusPage);
    }
}
