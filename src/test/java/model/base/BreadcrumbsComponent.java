package model.base;

import model.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

public class BreadcrumbsComponent extends BaseModel {
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
    public String getBreadcrumbsItemName(String name) {
        return getDriver()
                .findElement(By.xpath("//ul[@id='breadcrumbs']//a[@href='/user/admin/my-views/view/" + name + "/']"))
                .getText();
    }
}