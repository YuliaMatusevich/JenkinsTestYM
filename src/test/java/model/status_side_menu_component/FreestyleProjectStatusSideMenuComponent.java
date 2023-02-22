package model.status_side_menu_component;

import io.qameta.allure.Step;
import model.BuildStatusPage;
import model.BuildWithParametersPage;
import model.ChangesBuildsPage;
import model.HomePage;
import model.base.base_components.BreadcrumbsComponent;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.FreestyleProjectConfigPage;
import model.status_pages.FreestyleProjectStatusPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FreestyleProjectStatusSideMenuComponent extends BaseStatusSideMenuComponent<FreestyleProjectStatusPage, FreestyleProjectConfigPage> {

    @FindBy(linkText = "Build with Parameters")
    private WebElement buildWithParameters;

    @FindBy(css = ".collapse")
    private WebElement chevronDownIconOnBuildHistory;

    @FindBy(css = "#no-builds")
    private WebElement buildsInformation;

    @FindBy(css = ".build-row-cell")
    private List<WebElement> buildRowsOnBuildsInformation;

    @FindBy(linkText = "Build Now")
    private WebElement buildNow;

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(linkText = "Changes")
    private WebElement changes;

    @FindBy(css = ".pane.build-details a")
    private WebElement buildDateTimeField;

    @Override
    protected FreestyleProjectConfigPage createConfigPage() {
        return new FreestyleProjectConfigPage(getDriver());
    }

    public FreestyleProjectStatusSideMenuComponent(WebDriver driver, FreestyleProjectStatusPage statusPage) {
        super(driver, statusPage);
    }

    public BuildWithParametersPage<FreestyleProjectStatusPage> clickBuildWithParameters() {
        buildWithParameters.click();

        return new BuildWithParametersPage<>(getDriver(), page);
    }

    public FreestyleProjectStatusPage openBuildHistory() {
        if (chevronDownIconOnBuildHistory.getAttribute("title").equals("expand")) {
            chevronDownIconOnBuildHistory.click();
        }

        return new FreestyleProjectStatusPage(getDriver());
    }

    public int countBuilds() {
        getWait(10).until(ExpectedConditions.attributeContains(buildsInformation, "style", "display"));
        int countBuilds = 0;
        if (buildsInformation.getAttribute("style").equals("display: none;")) {
            countBuilds = buildRowsOnBuildsInformation.size();
        }

        return countBuilds;
    }
    @Step("Click 'Build Now'")
    public FreestyleProjectStatusPage clickBuildNow() {
        buildNow.click();
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformation, "style", "display: none;"));

        return new FreestyleProjectStatusPage(getDriver());
    }

    public BreadcrumbsComponent getBreadcrumbs() {
        return new BreadcrumbsComponent(getDriver());
    }

    public HomePage clickBuildNowAndRedirectToDashboardAfterBuildCompleted() {
        buildNow.click();
        getWait(60).until(ExpectedConditions.not(ExpectedConditions
                .attributeContains(buildStatusIcon, "tooltip", "progress")));
        getBreadcrumbs().clickDashboard();

        return new HomePage(getDriver());
    }

    public ChangesBuildsPage clickChanges() {
        changes.click();

        return new ChangesBuildsPage(getDriver());
    }

    public String getBuildDateTime() {
        getWait(20).until(ExpectedConditions.visibilityOf((buildDateTimeField)));

        return buildDateTimeField.getText();
    }
    @Step("Click on the last build in 'Build History' on side menu")
    public BuildStatusPage clickBuildIconInBuildHistory() {
        getWait(60).until(ExpectedConditions.not(ExpectedConditions
                .attributeContains(buildStatusIcon, "tooltip", "progress")));
        buildStatusIcon.click();

        return new BuildStatusPage(getDriver());
    }

    @Step("Get build status")
    public FreestyleProjectStatusPage getBuildStatus() {
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformation, "style", "display: none;"));

        return new FreestyleProjectStatusPage(getDriver());
    }
}