package model.status_side_menu_component;

import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.MultibranchPipelineConfigPage;
import model.status_pages.MultibranchPipelineStatusPage;
import org.openqa.selenium.WebDriver;

public class MultibranchPipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<MultibranchPipelineStatusPage, MultibranchPipelineConfigPage> {

    @Override
    protected MultibranchPipelineConfigPage createConfigPage() {
        return new MultibranchPipelineConfigPage(getDriver());
    }

    public MultibranchPipelineStatusSideMenuComponent(WebDriver driver, MultibranchPipelineStatusPage statusPage) {
        super(driver, statusPage);
    }
}
