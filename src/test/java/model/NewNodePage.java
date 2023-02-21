package model;

import io.qameta.allure.Step;
import model.base.MainBasePage;
import model.config_pages.NodeConfigPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewNodePage extends MainBasePage {
    @FindBy(id = "name")
    private WebElement setName;

    @FindBy(className = "jenkins-radio__label")
    private WebElement permanentAgentRadioButton;

    @FindBy(id = "ok")
    private WebElement createButton;

    public NewNodePage(WebDriver driver) {
        super(driver);
    }

    @Step("Set name '{name}' for New Node")
    public NewNodePage setName(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(setName)).sendKeys(name);
        return this;
    }

    @Step("Select 'Permanent Agent' radio button")
    public NewNodePage selectPermanentAgent() {
        permanentAgentRadioButton.click();
        return this;
    }

    @Step("Click 'Create' button")
    public NodeConfigPage clickCreateButton() {
        createButton.click();
        return new NodeConfigPage(getDriver());
    }
}
