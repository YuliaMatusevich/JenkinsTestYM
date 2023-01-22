package model.organization_folder;

import model.base.side_menu.BaseConfigSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class OrgFolderConfigSideMenuComponent extends BaseConfigSideMenuComponent<OrgFolderConfigPage> {

    public OrgFolderConfigSideMenuComponent(WebDriver driver, OrgFolderConfigPage configPage) {
        super(driver, configPage);
    }
}
