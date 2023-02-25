package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateUserPage extends MainBasePage {

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(name = "password1")
    private WebElement password;

    @FindBy(name = "password2")
    private WebElement confirmPassword;

    @FindBy(name = "fullname")
    private WebElement fullName;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(id = "yui-gen1-button")
    private WebElement createUserButton;

    @FindBy(css = ".error")
    private WebElement errorMessage;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    @Step("Set '{name}' in 'Username' field")
    public CreateUserPage setUsername(String name) {
        username.sendKeys(name);

        return this;
    }

    @Step("Set '{name}' in 'Password' field")
    public CreateUserPage setPassword(String name) {
        password.sendKeys(name);

        return this;
    }

    @Step("Set '{name}' in 'Confirm password' field")
    public CreateUserPage confirmPassword(String name) {
        confirmPassword.sendKeys(name);

        return this;
    }

    @Step("Set '{name}' in 'Full name' field")
    public CreateUserPage setFullName(String name) {
        fullName.sendKeys(name);

        return this;
    }

    public CreateUserPage setEmail(String name) {
        email.sendKeys(name);

        return this;
    }

    public ManageUsersPage clickCreateUserButton() {
        createUserButton.click();

        return new ManageUsersPage(getDriver());
    }

    @Step("Click 'Create User' button and get error message '{errorMessage}'")
    public String clickCreateUserAndGetErrorMessage() {
        createUserButton.click();

        return getWait(2).until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }
}
