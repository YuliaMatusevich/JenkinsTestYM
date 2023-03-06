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

    public MultiConfigurationProjectStatusPage fillDescription(String desc) {
        getWait(5).until(ExpectedConditions.visibilityOf(description));
        description.sendKeys(desc);

        return this;
    }

    public MultiConfigurationProjectStatusPage clickSave() {
        saveDescriptionButton.click();

        return this;
    }

    public String getNameMultiConfigProject(String name) {

        return getDriver().findElement(By.xpath("//li[@class='item']//a[@href='/job/" + name + "/']")).getText();
    }

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
        getWait(3).until(ExpectedConditions.elementToBeClickable(enableButton));
        enableButton.click();
        getWait(3).until(ExpectedConditions.visibilityOf(disableButton));

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectStatusPage clickBuildHistoryPageNavigationNewestBuild() {
        buildHistoryPageNavigation.click();

        return this;
    }

    public boolean iconProjectDisabledIsDisplayed() {
        getWait(10).until(ExpectedConditions.visibilityOf(iconProjectDisabled));

        return iconProjectDisabled.isDisplayed();
    }

    public boolean iconProjectEnabledIsDisplayed() {
        getWait(10).until(ExpectedConditions.visibilityOf(iconProjectEnabled));

        return iconProjectEnabled.isDisplayed();
    }

    public String getTextDisabledWarning() {

        return disabledWarning.getText();
    }

    public boolean disableButtonIsDisplayed() {

        return disableButton.isDisplayed();
    }

    public boolean configurationMatrixIsDisplayed() {

        return configurationMatrixTable.isDisplayed();
    }

    public boolean NewestBuildIsDisplayed() {

        return buildHistoryPageNavigation.isDisplayed();
    }
}