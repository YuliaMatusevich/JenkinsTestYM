package model;

import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangesBuildsPage extends MainBasePage {

    public ChangesBuildsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "main-panel")
    private WebElement mainPanelArea;

    public String getPageText() {

        return mainPanelArea.getText();
    }
}
