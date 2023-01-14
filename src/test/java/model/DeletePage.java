package model;

import model.base.BasePage;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeletePage<ParentPage extends BasePage> extends MainBasePage {

    @FindBy(id = "yui-gen1-button")
    private WebElement yesButton;

    private final ParentPage parentPage;

    public DeletePage(WebDriver driver, ParentPage parentPage) {
        super(driver);
        this.parentPage = parentPage;
    }

    public ParentPage clickYes() {
        yesButton.click();

        return parentPage;
    }
}
