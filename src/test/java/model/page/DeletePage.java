package model.page;

import io.qameta.allure.Step;
import model.page.base.BasePage;
import model.page.base.MainBasePage;
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

    @Step("Click on Yes button in the Delete page")
    public ParentPage clickYes() {
        yesButton.click();

        return parentPage;
    }
}
