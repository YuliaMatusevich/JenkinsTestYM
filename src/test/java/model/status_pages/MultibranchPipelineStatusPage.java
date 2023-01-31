package model.status_pages;

import model.base.BaseStatusPage;
import model.config_pages.MultibranchPipelineConfigPage;
import model.status_side_menu_component.MultibranchPipelineStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BaseStatusPage<MultibranchPipelineStatusPage, MultibranchPipelineStatusSideMenuComponent> {

    @FindBy(id = "yui-gen1-button")
    private WebElement disableEnableButton;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//img[@title='Multibranch Pipeline']")
    private WebElement projectIcon;

    @FindBy(linkText = "Configure the project")
    private WebElement linkConfigureTheProject;

    @Override
    protected MultibranchPipelineStatusSideMenuComponent createSideMenuComponent() {
        return new MultibranchPipelineStatusSideMenuComponent(getDriver(), this);
    }

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineStatusPage clickDisableEnableButton() {
        disableEnableButton.click();

        return this;
    }

    public String getWarningMessage() {
        return warningMessage.getText().split(" \n")[0];
    }

    public String getAttributeProjectIcon() {
        return projectIcon.getAttribute("class");
    }

    public boolean isDisableButtonPresent() {
        return disableEnableButton.getText().contains("Disable Multibranch Pipeline");
    }

    public MultibranchPipelineConfigPage clickLinkConfigureTheProject() {
        linkConfigureTheProject.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }
}
