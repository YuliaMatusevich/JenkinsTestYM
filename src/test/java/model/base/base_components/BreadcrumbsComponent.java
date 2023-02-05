package model.base.base_components;

import model.HomePage;
import model.status_pages.FolderStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BreadcrumbsComponent extends BaseComponent {

    @FindBy(id = "breadcrumbs")
    private WebElement breadcrumbs;

    @FindBy(css = "#breadcrumbs li a")
    protected WebElement topMenuRoot;

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement parentFolderRoot;

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

    public FolderStatusPage clickParentFolder() {
        parentFolderRoot.click();

        return new FolderStatusPage(getDriver());
    }
}