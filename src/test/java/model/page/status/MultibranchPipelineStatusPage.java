package model.page.status;

import io.qameta.allure.Step;
import model.page.base.BaseStatusPage;
import model.page.config.MultibranchPipelineConfigPage;
import model.component.menu.status.MultibranchPipelineStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BaseStatusPage<MultibranchPipelineStatusPage, MultibranchPipelineStatusSideMenuComponent> {

    @FindBy(id = "yui-gen1-button")
    private WebElement disableEnableButton;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//h1/*[1]")
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

    @Step("Click on the 'Disable/Enable' button to disable project")
    public MultibranchPipelineStatusPage clickDisableEnableButton() {
        disableEnableButton.click();

        return this;
    }

    @Step("Get warning message about disabled project")
    public String getWarningMessage() {
        return warningMessage.getText().split(" \n")[0];
    }

    @Step("Get attribute 'class' from project icon")
    public String getAttributeProjectIcon() {
        return projectIcon.getAttribute("class");
    }

    @Step("Is the 'Disable/Enable' button containing the text 'disable' at the moment")
    public boolean isDisableButtonPresent() {
        return disableEnableButton.getText().contains("Disable Multibranch Pipeline");
    }

    @Step("Click on the 'Configure the project' link on the Multibranch Pipeline status page")
    public MultibranchPipelineConfigPage clickLinkConfigureTheProject() {
        linkConfigureTheProject.click();

        return new MultibranchPipelineConfigPage(getDriver());
    }
}