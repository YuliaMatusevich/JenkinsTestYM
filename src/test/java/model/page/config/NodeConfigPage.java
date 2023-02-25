package model.page.config;

import io.qameta.allure.Step;
import model.page.ManageNodesAndCloudsPage;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.TestUtils;

public class NodeConfigPage extends MainBasePage {
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    public NodeConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Save' button")
    public ManageNodesAndCloudsPage clickSaveButton() {
        TestUtils.scrollToElement(getDriver(), saveButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(saveButton)).click();
        return new ManageNodesAndCloudsPage(getDriver());
    }
}
