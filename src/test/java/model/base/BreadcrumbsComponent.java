package model.base;

import model.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BreadcrumbsComponent extends BaseComponent {

    @FindBy(id = "breadcrumbs")
    private WebElement breadcrumbs;

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

    public BreadcrumbsComponent(WebDriver driver) {
        super(driver);
    }

    public String getTextBreadcrumbs() {
        return breadcrumbs.getText();
    }

    public HomePage clickDashboard() {
        topMenuRoot.click();

        return new HomePage(getDriver());
    }
}