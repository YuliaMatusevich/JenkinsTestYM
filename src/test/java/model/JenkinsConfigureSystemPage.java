package model;

import io.qameta.allure.Step;
import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class JenkinsConfigureSystemPage extends MainBasePage {

    @FindBy(name = "primaryView")
    private WebElement defaultView;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//textarea[@name='system_message']")
    private WebElement setSystemMessage;

    public JenkinsConfigureSystemPage(WebDriver driver) {
        super(driver);
    }

    public JenkinsConfigureSystemPage selectDefaultView(String nameView) {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), defaultView);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(defaultView)).click();
        getDriver().findElement(By.xpath(String.format("//option[@value='%s']", nameView))).click();

        return this;
    }

    @Step("Click 'Save' button")
    public JenkinsConfigureSystemPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    @Step("Enter text in 'System Message' field")
    public JenkinsConfigureSystemPage setSystemMessage(String text) {
        setSystemMessage.clear();
        setSystemMessage.sendKeys(text);

        return this;
    }
}
