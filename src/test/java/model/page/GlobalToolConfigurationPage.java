package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestDataUtils;
import runner.TestUtils;

import java.util.List;

public class GlobalToolConfigurationPage extends MainBasePage {

    @FindBy(xpath = "//button[text()='Add Maven']")
    private WebElement addMavenButton;

    @FindBy(css = "input[checkurl$='MavenInstallation/checkName']")
    private WebElement mavenTitleField;

    @FindBy(css = "input[checkurl$='MavenInstallation/checkName']")
    private List<WebElement> mavenNamesList;

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//input[contains(@checkurl,'MavenInstallation/checkName')]/parent::div/following-sibling::div")
    private WebElement errorArea;

    @FindBy(id = "footer")
    private WebElement footerElement;

    @FindBy(xpath = "//div[contains(text(),'List of Maven installations on this system')]")
    private WebElement listOfMavenInstallationsAreaTextNotice;

    @FindBy(xpath = "//button[text() = 'Maven installations...']")
    private WebElement mavenInstallationsButton;

    @FindBy(name= "_.id")
    private WebElement mavenVersionDropdownField;

    @FindBy(xpath = "//button[@title = 'Delete Maven']")
    private WebElement deleteMavenSign;

    @FindBy(xpath = "//button[text() = 'Save']")
    private WebElement saveButton;

    public GlobalToolConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public GlobalToolConfigurationPage clickFirstAddMavenButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addMavenButton));
        getWait(5).until(ExpectedConditions.visibilityOf(footerElement));
        addMavenButton.click();

        return this;
    }

    public GlobalToolConfigurationPage setFirstMavenTitleField(String name) {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(mavenTitleField));
        mavenTitleField.click();
        mavenTitleField.sendKeys(name);

        return this;
    }

    public GlobalToolConfigurationPage clickApplyButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(applyButton));
        getWait(5).until(ExpectedConditions.elementToBeClickable(applyButton)).click();

        return this;
    }

    public String getErrorAreaText() {

        return getWait(5).until(ExpectedConditions.visibilityOf(errorArea)).getText();
    }

    @Step("Set Maven version in Global Tools Configuration with new Maven name '{name}'")
    public GlobalToolConfigurationPage setMavenVersionWithNewName(String mavenName) {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(applyButton));
        if(!(listOfMavenInstallationsAreaTextNotice.isDisplayed())) {
            mavenInstallationsButton.click();
        } addMavenButton.click();

        TestUtils.scrollToElement_PlaceInCenter(getDriver(), mavenVersionDropdownField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(mavenVersionDropdownField));
        mavenTitleField.sendKeys(mavenName);

        return this;
    }

    @Step ("Click 'Save' button on the 'Global Configuration Page'")
    public GlobalToolConfigurationPage clickSaveButton() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(saveButton));
        getWait(5).until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return this;
    }
    @Step("Delete all the Maven versions installed on the 'Global Configuration Page'")
    public GlobalToolConfigurationPage deleteAllMavenInstallations() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(applyButton));
        if(!(listOfMavenInstallationsAreaTextNotice.isDisplayed())) {
            mavenInstallationsButton.click();
            int numberOfMavensInstalled = mavenNamesList.size();
            for (int i = 0; i <= numberOfMavensInstalled - 1; i++){
                TestUtils.scrollToElement_PlaceInCenter(getDriver(),deleteMavenSign);
                getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(deleteMavenSign));
                deleteMavenSign.click();
                applyButton.click();
            }
        }
        clickSaveButton();
        return this;
    }
}
