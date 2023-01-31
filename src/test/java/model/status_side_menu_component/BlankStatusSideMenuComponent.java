package model.status_side_menu_component;

import model.base.BaseConfigPage;
import model.base.BaseStatusPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class BlankStatusSideMenuComponent<StatusPage extends BaseStatusPage<?, ?>, ConfigPage extends BaseConfigPage<?, ?>> extends BaseStatusSideMenuComponent<StatusPage, ConfigPage> {

    @Override
    protected ConfigPage createConfigPage() {
        return null;
    }

    public BlankStatusSideMenuComponent(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }
}
