package model.base.side_menu;

import model.base.BasePage;
import org.openqa.selenium.WebDriver;

public abstract class BaseSideMenuWithGenericComponent<Page extends BasePage> extends BaseSideMenuComponent {

    protected final Page page;

    public BaseSideMenuWithGenericComponent(WebDriver driver, Page page) {
        super(driver);
        this.page = page;
    }
}
