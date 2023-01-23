package model.pipeline;

import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class PipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<PipelineStatusPage> {

    public PipelineStatusSideMenuComponent(WebDriver driver, PipelineStatusPage statusPage) {
        super(driver, statusPage);
    }
}
