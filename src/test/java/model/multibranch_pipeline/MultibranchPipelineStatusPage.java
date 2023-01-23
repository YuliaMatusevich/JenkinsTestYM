package model.multibranch_pipeline;

import model.RenameItemPage;
import model.base.BaseStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MultibranchPipelineStatusPage extends BaseStatusPage<MultibranchPipelineStatusPage, MultibranchPipelineStatusSideMenuComponent> {

    @FindBy(linkText = "Rename")
    private WebElement renameButton;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableEnableButton;

    @FindBy(id = "enable-project")
    private WebElement warningMessage;

    @FindBy(xpath = "//img[@title='Multibranch Pipeline']")
    private WebElement projectIcon;

    @Override
    protected MultibranchPipelineStatusSideMenuComponent createSideMenuComponent() {
        return new MultibranchPipelineStatusSideMenuComponent(getDriver(), this);
    }

    public MultibranchPipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public RenameItemPage<MultibranchPipelineStatusPage> clickRenameSideMenu() {
        renameButton.click();

        return new RenameItemPage<>(getDriver(), new MultibranchPipelineStatusPage(getDriver()));
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

}
