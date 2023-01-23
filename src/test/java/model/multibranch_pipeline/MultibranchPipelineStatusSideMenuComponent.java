package model.multibranch_pipeline;

import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<MultibranchPipelineStatusPage> {

    public MultibranchPipelineStatusSideMenuComponent(WebDriver driver, MultibranchPipelineStatusPage statusPage) {
        super(driver, statusPage);
    }
}
