package model.component.menu.status;

import model.component.base.BaseStatusSideMenuComponent;
import model.page.config.OrgFolderConfigPage;
import model.page.status.OrgFolderStatusPage;
import org.openqa.selenium.WebDriver;

public class OrgFolderStatusSideMenuComponent extends BaseStatusSideMenuComponent<OrgFolderStatusPage, OrgFolderConfigPage> {

    @Override
    public OrgFolderConfigPage getConfigPage() {
        return new OrgFolderConfigPage(getDriver());
    }

    public OrgFolderStatusSideMenuComponent(WebDriver driver, OrgFolderStatusPage statusPage) {
        super(driver, statusPage);
    }
}