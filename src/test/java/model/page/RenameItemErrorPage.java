package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RenameItemErrorPage extends MainBasePage {

    @FindBy(xpath = "//div[@id = 'main-panel']/p")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@id='main-panel']/h1")
    private WebElement headerErrorMessage;

    public RenameItemErrorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    @Step("Get error message text")
    public String getHeadErrorMessage() {
        return headerErrorMessage.getText();
    }
}
