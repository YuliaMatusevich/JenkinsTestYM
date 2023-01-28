package model.status_side_menu_component;

import model.base.side_menu.BaseStatusSideMenuComponent;
import model.status_pages.PipelineStatusPage;
import org.openqa.selenium.WebDriver;

public class PipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<PipelineStatusPage> {

    public PipelineStatusSideMenuComponent(WebDriver driver, PipelineStatusPage statusPage) {
        super(driver, statusPage);
    }
}
