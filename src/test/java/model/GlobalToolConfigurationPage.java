package model;

import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class GlobalToolConfigurationPage extends MainBasePage {

    @FindBy(xpath = "//button[text()='Add Maven']")
    private WebElement addMavenButton;

    @FindBy(css = "input[checkurl$='MavenInstallation/checkName']")
    private WebElement mavenTitleField;

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement applyButton;

    @FindBy(xpath = "//input[contains(@checkurl,'MavenInstallation/checkName')]/parent::div/following-sibling::div")
    private WebElement errorArea;

    @FindBy(id = "footer")
    private WebElement footerElement;

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
}
