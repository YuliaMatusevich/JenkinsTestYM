package model.base;

import model.base.side_menu.BaseSideMenuFrame;
import org.openqa.selenium.WebDriver;

public abstract class MainBasePageWithSideMenu<SideMenu extends BaseSideMenuFrame> extends MainBasePage {

    protected abstract SideMenu createSideMenuFrame();

    public MainBasePageWithSideMenu(WebDriver driver) {
        super(driver);
    }

    public SideMenu getSideMenu() {
        return createSideMenuFrame();
    }
}
