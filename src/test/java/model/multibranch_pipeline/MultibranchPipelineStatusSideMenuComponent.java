package model.multibranch_pipeline;

import model.base.side_menu.BaseStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusSideMenuComponent extends BaseStatusSideMenuComponent<MultibranchPipelineStatusPage> {

    @FindBy(linkText = "Configure")
    private WebElement configure;

    public MultibranchPipelineStatusSideMenuComponent(WebDriver driver, MultibranchPipelineStatusPage statusPage) {
        super(driver, statusPage);
    }

    public MultibranchPipelineConfigPage clickConfigure() {
        configure.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }
}
