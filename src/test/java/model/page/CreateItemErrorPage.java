package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateItemErrorPage extends MainBasePage {

    @FindBy(xpath = "//div[@id = 'main-panel']/p")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@id='main-panel']/h1")
    private WebElement errorHeader;

    @FindBy(xpath = "//div[@id='error-description']//h2")
    private WebElement errorDescription;

    @FindBy(xpath = "//img[contains(@src,'rage.svg')]")
    private WebElement errorPicture;

    public CreateItemErrorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get error message")
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    @Step("Get error header")
    public String getErrorHeader() {
        return errorHeader.getText();
    }

    @Step("Get error text on CreateItemErrorPage")
    public String getErrorDescription() {
        return errorDescription.getText();
    }

    @Step("Check if error picture is displayed on CreateItemErrorPage")
    public Boolean isErrorPictureDisplayed() {
        return errorPicture.isDisplayed();
    }

    @Step("Get page URL")
    public String getPageUrl() {
        return getDriver().getCurrentUrl();
    }
}
