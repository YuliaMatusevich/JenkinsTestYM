package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PluginManagerPage extends MainBasePage {

    @FindBy(xpath = "//a[@href='./available']")
    private WebElement linkAvailable;

    @FindBy(xpath = "//a[@href='./installed']")
    private WebElement linkInstalled;

    @FindBy(id = "filter-box")
    private WebElement searchField;

    @FindBy(xpath = "//tr[@data-plugin-id='testng-plugin']//label")
    private WebElement checkBoxTestNGResults;

    @FindBy(id = "yui-gen1-button")
    private WebElement buttonInstallWithoutRestart;

    @FindBy(xpath = "//a[contains(text(), 'TestNG Results Plugin')]/parent::div/following-sibling::div")
    private WebElement resultField;

    public PluginManagerPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on 'Available' link in 'Plugin Manager' menu")
    public PluginManagerPage clickLinkAvailable() {
        linkAvailable.click();

        return this;
    }

    @Step("Input '{value}' in 'Filter' field")
    public PluginManagerPage setSearch(String value) {
        getWait(5).until(ExpectedConditions.visibilityOf(searchField)).sendKeys(value);

        return this;
    }

    @Step("Check checkBox to install 'TestNGResults'")
    public PluginManagerPage clickCheckBoxTestNGResults() {
        getWait(5).until(ExpectedConditions.visibilityOf(checkBoxTestNGResults)).click();

        return this;
    }

    @Step("Click on 'Install without restart' button")
    public UpdateCenterPage clickButtonInstallWithoutRestart() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonInstallWithoutRestart)).click();

        return new UpdateCenterPage(getDriver());
    }

    @Step("Get result field text")
    public String getResultFieldText() {

        return getWait(5).until(ExpectedConditions.visibilityOf(resultField)).getText();
    }

    @Step("Click on 'Installed' link in 'Plugin Manager' menu")
    public PluginManagerPage clickLinkInstalled() {
        linkInstalled.click();

        return this;
    }
}
