package model.page;

import model.page.base.BaseStatusPage;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RenameItemPage<StatusPage extends BaseStatusPage<?, ?>> extends MainBasePage {
    @FindBy(id = "yui-gen1-button")
    private WebElement renameButton;

    @FindBy(css = "input[name='newName']")
    private WebElement fieldInputtingNewName;

    private final StatusPage statusPage;

    public RenameItemPage(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }

    public RenameItemPage<StatusPage> clearFieldAndInputNewName(String newName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(fieldInputtingNewName)).clear();
        fieldInputtingNewName.sendKeys(newName);

        return this;
    }

    public RenameItemErrorPage clickRenameButtonWithInvalidData() {
        renameButton.click();

        return new RenameItemErrorPage(getDriver());
    }

    public StatusPage clickRenameButton() {
        renameButton.click();

        return statusPage;
    }
}
