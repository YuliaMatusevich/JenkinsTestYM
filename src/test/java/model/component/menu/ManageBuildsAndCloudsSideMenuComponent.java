package model.component.menu;

import io.qameta.allure.Step;
import model.page.NewNodePage;
import model.component.base.BaseSideMenuComponent;
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
