package model.base;

import model.base.side_menu.BaseConfigSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseConfigPage<StatusPage extends BaseStatusPage<?, ?>, Self extends BaseConfigPage<?, ?, ?>, ConfigSideMenuComponent extends BaseConfigSideMenuComponent<Self>> extends MainBasePageWithSideMenu<ConfigSideMenuComponent> {

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(name = "Apply")
    private WebElement applyButton;

    protected abstract StatusPage createStatusPage();

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    public StatusPage clickSaveButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return createStatusPage();
    }

    public Self clickApplyButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(applyButton)).click();

        return (Self)this;
    }
}
