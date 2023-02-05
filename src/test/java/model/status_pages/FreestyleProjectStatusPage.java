package model.status_pages;

import model.ChangesBuildsPage;
import model.HomePage;
import model.base.BaseStatusPage;
import model.config_pages.FreestyleProjectConfigPage;
import model.status_side_menu_component.FreestyleProjectStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class FreestyleProjectStatusPage extends BaseStatusPage<FreestyleProjectStatusPage, FreestyleProjectStatusSideMenuComponent> {

    @FindBy(xpath = "//li[@class='item'][last()-1]")
    private WebElement breadcrumbsParentFolderLink;

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescriptionText;

    @FindBy(id = "yui-gen2")
    private WebElement buttonSave;

    @FindBy(css = ".collapse")
    private WebElement buttonOpenBuildHistoryOnSidePanel;

    @FindBy(css = "#no-builds")
    private WebElement buildsInformationOnSidePanel;

    @FindBy(css = ".build-row-cell")
    private List<WebElement> buildRowsOnBuildsInformation;

    @FindBy(linkText = "Build Now")
    private WebElement buttonBuildNowOnSidePanel;

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @FindBy(linkText = "Build Now")
    private WebElement buttonBuildNow;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(id = "enable-project")
    private WebElement warningForm;

    @FindBy(name = "Submit")
    private WebElement disableProjectBtn;

    @FindBy(linkText = "Changes")
    private WebElement linkChanges;

    @FindBy(linkText = "Edit description")
    private WebElement buttonEditDescription;

    @FindBy(css = ".pane.build-details a")
    private WebElement buildDateTimeField;

    @Override
    protected FreestyleProjectStatusSideMenuComponent createSideMenuComponent() {
        return new FreestyleProjectStatusSideMenuComponent(getDriver(), this);
    }

    public FreestyleProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public FolderStatusPage clickParentFolderInBreadcrumbs() {
        breadcrumbsParentFolderLink.click();

        return new FolderStatusPage(getDriver());
    }

    public FreestyleProjectStatusPage clickDisableProjectBtn() {
        disableProjectBtn.click();

        return this;
    }

    public FreestyleProjectStatusPage inputAndSaveDescriptionText(String description) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).clear();
        fieldDescriptionText.sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
    }

    public HomePage confirmAlertAndDeleteProject() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public FreestyleProjectStatusPage openBuildHistoryOnSidePanel() {
        if (buttonOpenBuildHistoryOnSidePanel.getAttribute("title").equals("expand")) {
            buttonOpenBuildHistoryOnSidePanel.click();
        }

        return this;
    }

    public int countBuildsOnSidePanel() {
        getWait(10).until(ExpectedConditions.attributeContains(buildsInformationOnSidePanel, "style", "display"));
        int countBuilds = 0;
        if (buildsInformationOnSidePanel.getAttribute("style").equals("display: none;")) {
            countBuilds = buildRowsOnBuildsInformation.size();
        }

        return countBuilds;
    }

    public FreestyleProjectStatusPage clickBuildNowOnSidePanel() {
        buttonBuildNowOnSidePanel.click();
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformationOnSidePanel, "style", "display: none;"));

        return this;
    }

    public HomePage clickButtonBuildNowAndRedirectToDashboardAfterBuildCompleted() {
        buttonBuildNow.click();
        getWait(60).until(ExpectedConditions.not(ExpectedConditions
                .attributeContains(buildStatusIcon, "tooltip", "progress")));
        getBreadcrumbs().clickDashboard();

        return new HomePage(getDriver());
    }

    public String getWarningMsg() {
        return warningForm.getText().substring(0, warningForm.getText().indexOf("\n"));
    }

    public ChangesBuildsPage clickLinkChanges() {
        linkChanges.click();

        return new ChangesBuildsPage(getDriver());
    }

    public FreestyleProjectStatusPage clickButtonEditDescription() {
        buttonEditDescription.click();

        return this;
    }

    public FolderStatusPage confirmAlertAndDeleteProjectFromFolder() {
        getDriver().switchTo().alert().accept();

        return new FolderStatusPage(getDriver());
    }

    public String getBuildDateTime(){
        getWait(20).until(ExpectedConditions.visibilityOf((buildDateTimeField)));

        return buildDateTimeField.getText();
    }
}
