package model.page;

import model.component.menu.UserSideMenuComponent;
import model.page.base.MainBasePageWithSideMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BuildsUserPage extends MainBasePageWithSideMenu<UserSideMenuComponent> {

    @FindBy(css = "div#main-panel > h1")
    private WebElement headerH1;

    public BuildsUserPage(WebDriver driver) {

        super(driver);
    }

    @Override
    protected UserSideMenuComponent createSideMenuComponent() {
        return new UserSideMenuComponent(getDriver());
    }

    public String getHeaderH1Text() {

        return headerH1.getText();
    }
}
