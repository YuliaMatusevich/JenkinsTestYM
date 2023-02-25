package model.page.base;

import io.qameta.allure.Step;
import model.component.BreadcrumbsComponent;
import model.component.FooterComponent;
import model.component.HeaderComponent;
import org.openqa.selenium.WebDriver;

public abstract class MainBasePage extends BasePage {

    public MainBasePage(WebDriver driver) {
        super(driver);
    }

    public FooterComponent getFooter() {
        return new FooterComponent(getDriver());
    }

    @Step("Switch to header")
    public HeaderComponent getHeader() {
        return new HeaderComponent(getDriver());
    }

    @Step("Switch to Breadcrumbs panel")
    public BreadcrumbsComponent getBreadcrumbs() {
        return new BreadcrumbsComponent(getDriver());
    }
}
