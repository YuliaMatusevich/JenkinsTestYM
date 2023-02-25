package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import model.component.menu.ManageBuildsAndCloudsSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ManageNodesAndCloudsPage extends MainBasePage {
    @FindBy(xpath = "//tbody//a[@class='jenkins-table__link model-link inside']")
    private List<WebElement> tableBody;

    public ManageNodesAndCloudsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Switch to side menu")
    public ManageBuildsAndCloudsSideMenuComponent getSideMenu() {
        return new ManageBuildsAndCloudsSideMenuComponent(getDriver());
    }

    @Step("Get Node titles")
    public List<String> getNodeTitles() {
        getWait(3).until(ExpectedConditions.visibilityOfAllElements(tableBody));
        return tableBody.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
