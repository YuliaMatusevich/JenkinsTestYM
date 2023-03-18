package model.page.base;

import io.qameta.allure.Step;
import model.page.view.ViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseEditViewPage extends MainBasePage {

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    public BaseEditViewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Ok' Button")
    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }
}
