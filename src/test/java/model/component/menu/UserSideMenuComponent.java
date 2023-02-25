package model.component.menu;

import model.page.BuildsUserPage;
import model.page.ConfigureUserPage;
import model.page.DeletePage;
import model.page.HomePage;
import model.component.base.BaseSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserSideMenuComponent extends BaseSideMenuComponent {

    @FindBy(xpath = "//div[@id='tasks']//span[contains(., 'Delete')]")
    private WebElement delete;

    @FindBy(linkText = "Configure")
    private WebElement configure;

    @FindBy(linkText = "Builds")
    private WebElement builds;

    public UserSideMenuComponent(WebDriver driver) {
        super(driver);
    }

    public DeletePage<HomePage> clickDelete() {
        delete.click();

        return new DeletePage<>(getDriver(), new HomePage(getDriver()));
    }

    public ConfigureUserPage clickConfigure() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configure)).click();

        return new ConfigureUserPage(getDriver());
    }

    public BuildsUserPage clickBuilds() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(builds)).click();

        return new BuildsUserPage(getDriver());
    }
}