package model.base.side_menu;

import io.qameta.allure.Step;
import model.NewNodePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageBuildsAndCloudsSideMenuComponent extends BaseSideMenuComponent {
    @FindBy(xpath = "//a[@href='new']")
    private WebElement newNode;

    public ManageBuildsAndCloudsSideMenuComponent(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'New Node' menu link")
    public NewNodePage clickNewNode() {
        newNode.click();
        return new NewNodePage(getDriver());
    }
}
