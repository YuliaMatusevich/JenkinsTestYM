package model;

import io.qameta.allure.Step;
import model.base.MainBasePage;
import model.base.side_menu.UserSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfigureUserPage extends MainBasePage {

    @FindBy(css = "input[name='_.fullName']")
    private WebElement inputFieldFullUserName;

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    @FindBy(id = "yui-gen2-button")
    private WebElement buttonAddNewToken;

    public ConfigureUserPage(WebDriver driver) {
        super(driver);
    }

    public UserSideMenuComponent getSideMenu() {
        return new UserSideMenuComponent(getDriver());
    }

    @Step("Clear 'Full Name' field")
    public ConfigureUserPage clearFullNameField() {
        inputFieldFullUserName.clear();

        return this;
    }

    @Step("Set '{name}' into 'Full Name' field")
    public ConfigureUserPage setFullName(String name) {
        inputFieldFullUserName.sendKeys(name);

        return this;
    }

    @Step("Click on 'Save' button")
    public StatusUserPage clickSaveButton() {
        saveButton.click();

        return new StatusUserPage(getDriver());
    }

    public String getAddNewTokenButtonName() {

        return buttonAddNewToken.getText();
    }
}
