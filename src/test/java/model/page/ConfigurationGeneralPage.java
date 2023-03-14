package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import model.page.status.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfigurationGeneralPage extends MainBasePage {
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(name = "_.displayNameOrNull")
    private WebElement displayName;

    @FindBy(name = "_.description")
    private WebElement description;

    public ConfigurationGeneralPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{name}' into the 'Display name' field")
    public ConfigurationGeneralPage setProjectName(String name) {
        getWait(5).until(ExpectedConditions.visibilityOf(displayName)).sendKeys(name);

        return this;
    }

    @Step("Input '{text}' into the 'Description' field")
    public ConfigurationGeneralPage setDescription(String text) {
        getWait(5).until(ExpectedConditions.visibilityOf(description)).sendKeys(text);

        return this;
    }

    @Step("Click 'Save' button")
    public FolderStatusPage clickSaveButton() {
        saveButton.click();

        return new FolderStatusPage(getDriver());
    }
}
