package model.page.status;

import io.qameta.allure.Step;
import model.page.HomePage;
import model.page.base.BaseStatusPage;
import model.component.menu.status.MultiConfigurationProjectSideMenuComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MultiConfigurationProjectStatusPage extends BaseStatusPage<MultiConfigurationProjectStatusPage, MultiConfigurationProjectSideMenuComponent> {

    @FindBy(name = "description")
    private WebElement description;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveDescriptionButton;

    @FindBy(xpath = "//button[@id='yui-gen1-button']")
    private WebElement disableButton;

    @FindBy(xpath = "//button[@id='yui-gen1-button']")
    private WebElement enableButton;

    @FindBy(xpath = "//span[@class='build-status-icon__wrapper icon-disabled icon-md']")
    private WebElement iconProjectDisabled;

    @FindBy(xpath = "//span[@class='build-status-icon__wrapper icon-nobuilt icon-md']")
    private WebElement iconProjectEnabled;

    @FindBy(id = "enable-project")
    private WebElement disabledWarning;

    @FindBy(xpath = "//div[@id='matrix']")
    private WebElement configurationMatrixTable;

    @FindBy(xpath = "//*[@id='buildHistoryPageNav']/div[1]/div")
    private WebElement buildHistoryPageNavigation;

    @Override
    protected MultiConfigurationProjectSideMenuComponent createSideMenuComponent() {
        return new MultiConfigurationProjectSideMenuComponent(getDriver(), this);
    }

    public MultiConfigurationProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    @Step("Input '{Description}' into the 'Description' field")
    public MultiConfigurationProjectStatusPage fillDescription(String desc) {
        getWait(5).until(ExpectedConditions.visibilityOf(description));
        description.sendKeys(desc);

        return this;
    }

    @Step("Click 'Save' button on the configuration project page")
    public MultiConfigurationProjectStatusPage clickSave() {
        saveDescriptionButton.click();

        return this;
    }

    @Step("Get name of the project")
    public String getNameMultiConfigProject(String name) {

        return getDriver().findElement(By.xpath("//li[@class='item']//a[@href='/job/" + name + "/']")).getText();
    }

    @Step("Confirm alert and click 'delete' button")
    public HomePage confirmAlertAndDeleteProject() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    @Step("Click 'Disable Project' button on job page")
    public MultiConfigurationProjectStatusPage clickDisableButton() {
        disableButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    @Step("Click 'Enable' button on job page")
    public MultiConfigurationProjectStatusPage clickEnableButton() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(enableButton));
        enableButton.click();

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    @Step("click the 'newest build' button on the navigation page of the build history page")
    public MultiConfigurationProjectStatusPage clickBuildHistoryPageNavigationNewestBuild() {
        buildHistoryPageNavigation.click();

        return this;
    }

    @Step("verify that icon 'project disabled' is displayed")
    public boolean iconProjectDisabledIsDisplayed() {
        getWait(10).until(ExpectedConditions.visibilityOf(iconProjectDisabled));

        return iconProjectDisabled.isDisplayed();
    }

    @Step("verify that icon 'project enabled' is displayed")
    public boolean iconProjectEnabledIsDisplayed() {
        getWait(10).until(ExpectedConditions.visibilityOf(iconProjectEnabled));

        return iconProjectEnabled.isDisplayed();
    }

    @Step("Get a warning text about project disabled")
    public String getTextDisabledWarning() {

        return disabledWarning.getText();
    }

    @Step("Verify that 'disable' button is displayed")
    public boolean disableButtonIsDisplayed() {

        return disableButton.isDisplayed();
    }

    @Step("verify that field 'configuration matrix' is displayed")
    public boolean configurationMatrixIsDisplayed() {

        return configurationMatrixTable.isDisplayed();
    }

    @Step("verify that button 'newest build' is displayed")
    public boolean NewestBuildIsDisplayed() {

        return buildHistoryPageNavigation.isDisplayed();
    }
}