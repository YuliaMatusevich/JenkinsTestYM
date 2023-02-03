package model.status_side_menu_component;

import model.ConsoleOutputPage;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.MultiConfigurationProjectConfigPage;
import model.status_pages.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MultiConfigurationProjectSideMenuComponent extends BaseStatusSideMenuComponent<MultiConfigurationProjectStatusPage, MultiConfigurationProjectConfigPage> {

    @FindBy(linkText = "Build Now")
    private WebElement buildNow;

    @FindBy(css = "#no-builds")
    private WebElement buildHistory;

    @FindBy(css = ".build-row-cell")
    private List<WebElement> buildRowsOnBuildHistory;

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @Override
    protected MultiConfigurationProjectConfigPage createConfigPage() {
        return new MultiConfigurationProjectConfigPage(getDriver());
    }

    public MultiConfigurationProjectSideMenuComponent(WebDriver driver, MultiConfigurationProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public MultiConfigurationProjectStatusPage clickBuildNow() {
        buildNow.click();
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildHistory, "style", "display: none;"));

        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public int countBuildsInBuildHistory() {
        getWait(10).until(ExpectedConditions.attributeContains(buildHistory, "style", "display"));
        int countBuilds = 0;
        if (buildHistory.getAttribute("style").equals("display: none;")) {
            countBuilds = buildRowsOnBuildHistory.size();
        }

        return countBuilds;
    }

    public ConsoleOutputPage clickBuildIcon() {
        getWait(20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='build-icon']"))).click();

        return new ConsoleOutputPage(getDriver());
    }
}