package model;

import model.base.BaseStatusPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class BlankStatusSideMenuComponent<StatusPage extends BaseStatusPage<?, ?>> extends BaseStatusSideMenuComponent<StatusPage> {

    public BlankStatusSideMenuComponent(WebDriver driver, StatusPage statusPage) {
        super(driver, statusPage);
    }
}
