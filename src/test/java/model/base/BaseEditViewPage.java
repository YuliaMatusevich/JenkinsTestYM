package model.base;
import model.views.ViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseEditViewPage extends MainBasePage {

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    public BaseEditViewPage(WebDriver driver) {
        super(driver);
    }

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }
}
