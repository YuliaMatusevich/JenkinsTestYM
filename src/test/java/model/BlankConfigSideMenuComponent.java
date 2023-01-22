package model;

import model.base.BaseConfigPage;
import model.base.side_menu.BaseConfigSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class BlankConfigSideMenuComponent<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseConfigSideMenuComponent<ConfigPage> {

    public BlankConfigSideMenuComponent(WebDriver driver, ConfigPage configPage) {
        super(driver, configPage);
    }
}
