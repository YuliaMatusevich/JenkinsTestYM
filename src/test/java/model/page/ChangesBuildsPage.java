package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangesBuildsPage extends MainBasePage {

    public ChangesBuildsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "main-panel")
    private WebElement mainPanelArea;

    @Step("Get 'Changes' page text")
    public String getPageText() {

        return mainPanelArea.getText();
    }
}
