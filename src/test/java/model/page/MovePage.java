package model.page;

import io.qameta.allure.Step;
import model.page.base.BaseStatusPage;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MovePage<StatusPage extends BaseStatusPage<?, ?>> extends MainBasePage {

    @FindBy(css = ".select.setting-input")
    private WebElement dropdown;

    @FindBy(xpath = "//button[text()='Move']")
    private WebElement moveButton;

    private final StatusPage statusPage;

    public MovePage(WebDriver driver, StatusPage statusPage) {
        super(driver);
        this.statusPage = statusPage;
    }

    @Step("Select folder name '{name}' in the drop down menu")
    public MovePage<StatusPage> selectFolder(String name) {
        new Select(dropdown).selectByVisibleText("Jenkins » " + name);

        return this;
    }

    @Step("Select Jenkins dashboard in the drop down menu")
    public MovePage<StatusPage> selectDashboardAsFolder() {
        new Select(dropdown).selectByVisibleText("Jenkins");

        return this;
    }

    @Step("Click on the 'Move' button to confirm the move")
    public StatusPage clickMoveButton() {
        moveButton.click();

        return statusPage;
    }
}
