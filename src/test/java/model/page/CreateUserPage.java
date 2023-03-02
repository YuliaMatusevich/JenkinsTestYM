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

    @Step("Input name '{name}' into 'Username' field")
    public CreateUserPage setUsername(String name) {
        username.sendKeys(name);

        return this;
    }

    @Step("Input password '{name}' into 'Password' field")
    public CreateUserPage setPassword(String name) {
        password.sendKeys(name);

        return this;
    }

    @Step("Input password '{name}' into 'Confirm password' field")
    public CreateUserPage confirmPassword(String name) {
        confirmPassword.sendKeys(name);

        return this;
    }

    @Step("Input full name '{name}' into 'Full name' field")
    public CreateUserPage setFullName(String name) {
        fullName.sendKeys(name);

        return this;
    }

    @Step("Input email '{name}' into 'E-mail address' field")
    public CreateUserPage setEmail(String name) {
        email.sendKeys(name);

        return this;
    }

    @Step("Click on 'Create User' button")
    public ManageUsersPage clickCreateUserButton() {
        createUserButton.click();

        return new ManageUsersPage(getDriver());
    }

    @Step("Click on 'Create User' button and get error message")
    public String clickCreateUserAndGetErrorMessage() {
        createUserButton.click();

        return getWait(2).until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }
}
