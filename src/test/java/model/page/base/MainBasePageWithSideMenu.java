package model.page.base;

import io.qameta.allure.Step;
import model.component.base.BaseSideMenuComponent;
import org.openqa.selenium.WebDriver;

public abstract class MainBasePageWithSideMenu<SideMenu extends BaseSideMenuComponent> extends MainBasePage {

    protected abstract SideMenu createSideMenuComponent();

    public MainBasePageWithSideMenu(WebDriver driver) {
        super(driver);
    }

    @Step("Switch to side menu")
    public SideMenu getSideMenu() {
        return createSideMenuComponent();
    }
}
