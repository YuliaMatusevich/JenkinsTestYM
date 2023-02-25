package model.status_side_menu_component;

import io.qameta.allure.Step;
import model.*;
import model.base.base_components.BreadcrumbsComponent;
import model.base.side_menu.BaseStatusSideMenuComponent;
import model.config_pages.FreestyleProjectConfigPage;
import model.status_pages.FreestyleProjectStatusPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

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

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private List<WebElement> buildStatusIconList;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*[last()]")
    private WebElement buildStatusIconLast;

    @FindBy(linkText = "Changes")
    private WebElement changes;

    @FindBy(css = ".pane.build-details a")
    private WebElement buildDateTimeField;

    @FindBy(xpath = "//span[text()='Workspace']")
    private WebElement workspace;

    @FindBy(xpath = "//div[contains(@class, 'pane build-name')]/a[last()]")
    private WebElement numberOfLastBuild;

    @FindBy(xpath = "//a[@class='model-link inside build-link display-name']")
    private List<WebElement> listOfBuildNames;

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
    public FreestyleProjectStatusPage clickBuildNowAndWaitSuccessStatus() {
        int countBuildsInList = buildStatusIconList.size() + 1;
        buildNow.click();
        getWait(20).until(ExpectedConditions.textToBePresentInElement(
                numberOfLastBuild, "#" + countBuildsInList));
        getWait(60).until(ExpectedConditions.attributeToBe(
                buildStatusIconLast, "tooltip", "Success &gt; Console Output"));

        return new FreestyleProjectStatusPage(getDriver());
    }

    @Step("Click 'Build Now' '{n}' times")
    public FreestyleProjectStatusPage clickBuildNowAndWaitStatusChangedNTimes(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            buildNow.click();
            getWait(60).until(ExpectedConditions.attributeContains(buildStatusIconLast, "tooltip", "&gt; Console Output"));
        }
        getWait(20).until(ExpectedConditions.textToBePresentInElement(
                numberOfLastBuild, "#" + n));

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

    @Step("Get 'Workspace' on side menu")
    public WorkspacePage clickWorkspace() {
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", workspace);

        return new WorkspacePage(getDriver());
    }

    @Step("Get a list of saved builds")
    public List<String> getListOfSavedBuilds() {
        return listOfBuildNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}