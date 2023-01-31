package model;

import model.base.BaseStatusPage;
import model.base.MainBasePage;
import model.base.side_menu.HomeSideMenuComponent;
import model.config_pages.FolderConfigPage;
import model.config_pages.FreestyleProjectConfigPage;
import model.config_pages.PipelineConfigPage;
import model.status_pages.*;
import model.views.NewViewPage;
import model.views.ViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

import static runner.TestUtils.scrollToElement;

public class HomePage extends MainBasePage {

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(linkText = "Configure")
    private WebElement configureDropDownMenu;

    @FindBy(linkText = "Rename")
    private WebElement renameDropDownMenu;

    @FindBy(xpath = "//li[@index='2']")
    private WebElement deleteButtonInDropDownMenu;

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(css = ".tabBar>.tab>a.addTab")
    private WebElement addViewLink;

    @FindBy(xpath = "//span[text()='Move']")
    private WebElement moveButtonDropdown;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> viewList;

    @FindBy(xpath = "//span[contains(@class, 'build-status-icon')]/span/child::*")
    private WebElement buildStatusIcon;

    @FindBy(xpath = "(//a[@class='yuimenuitemlabel'])[3]/span")
    private WebElement buildNowButton;

    @FindBy(css = "#projectstatus th")
    private List<WebElement> listJobTableHeaders;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomeSideMenuComponent getSideMenu() {
        return new HomeSideMenuComponent(getDriver());
    }

    public NewViewPage<?> clickAddViewLink() {
        addViewLink.click();

        return new NewViewPage<>(getDriver(), null);
    }

    public List<String> getJobNamesList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getNumberOfJobsContainingString(String string) {

        return (int) jobList
                .stream()
                .filter(element -> element.getText().contains(string))
                .count();
    }

    public List<String> getViewList() {
        return viewList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public FreestyleProjectStatusPage clickFreestyleProjectName() {
        getWait(10).until(ExpectedConditions.visibilityOfAllElements(jobList)).get(0).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectStatusPage clickFreestyleProjectName(String name) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(By.linkText(name))).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    public RenameItemPage<PipelineStatusPage> clickRenamePipelineDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new PipelineStatusPage(getDriver()));
    }

    public RenameItemPage<MultiConfigurationProjectStatusPage> clickRenameMultiConfigurationDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new MultiConfigurationProjectStatusPage(getDriver()));
    }

    public RenameItemPage<FolderStatusPage> clickRenameFolderDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new FolderStatusPage(getDriver()));
    }

    public RenameItemPage<MultibranchPipelineStatusPage> clickRenameMultibranchPipelineDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(renameDropDownMenu)).click();

        return new RenameItemPage<>(getDriver(), new MultibranchPipelineStatusPage(getDriver()));
    }

    public ConfigurationGeneralPage clickConfigDropDownMenu() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new ConfigurationGeneralPage(getDriver());
    }

    public PipelineStatusPage clickPipelineProjectName() {
        jobList.get(0).click();

        return new PipelineStatusPage(getDriver());
    }

    public DeletePage<HomePage> clickDeleteDropDownMenu() {
        getWait(3).until(ExpectedConditions.elementToBeClickable(deleteButtonInDropDownMenu));
        deleteButtonInDropDownMenu.click();

        return new DeletePage<>(getDriver(), this);
    }

    public HomePage clickJobDropDownMenu(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(
                "//tr[@id='job_%s']//button[@class='jenkins-menu-dropdown-chevron']", name)))).click();

        return this;
    }

    public MultibranchPipelineStatusPage clickJobMBPipeline(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }

    public PipelineConfigPage clickConfigureDropDownMenu() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new PipelineConfigPage(getDriver());
    }

    public String getHeaderText() {

        return getWait(10).until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public HomePage clickJobDropdownMenu(String folderName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + folderName + "/']/button"))).click();

        return this;
    }

    public FolderStatusPage clickFolder(String folderName) {
        getDriver().findElement(By.xpath("//span[text()='" + folderName + "']")).click();

        return new FolderStatusPage(getDriver());
    }

    public FolderConfigPage clickConfigureDropDownMenuForFolder() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FolderConfigPage(getDriver());
    }

    public String getJobBuildStatus(String name) {
        return getDriver().findElement(By.id(String.format("job_%s", name)))
                .findElement(By.xpath(".//*[name()='svg']")).getAttribute("tooltip");
    }

    public MultiConfigurationProjectStatusPage clickMultiConfigurationProject(String name) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.linkText(name))).click();
        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public <T extends BaseStatusPage<T, ?>> MovePage<T> clickMoveButtonDropdown(T baseStatusPage) {
        getWait(5).until(ExpectedConditions.visibilityOf(moveButtonDropdown));
        scrollToElement(getDriver(), moveButtonDropdown);
        moveButtonDropdown.click();
        return new MovePage<>(getDriver(), baseStatusPage);
    }

    public String getBuildDurationTime() {
        if (getJobBuildStatus().equals("Success")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[4]")).getText();
        } else if (getJobBuildStatus().equals("Failed")) {

            return getDriver().findElement(By.xpath("//tr[contains(@class, 'job-status')]/td[5]")).getText();
        }

        return null;
    }

    public String getJobBuildStatus() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public ViewPage clickView(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/view/%s/']", name))).click();

        return new ViewPage(getDriver());
    }

    public String getLastSuccessText(String name) {

        return getDriver().findElement(By.xpath(String.format("//*[@id='job_%s']/td[4]", name))).getText();
    }

    public String getJobName(String name) {

        return getDriver().findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
    }

    public String getStatusBuildText() {

        return buildStatusIcon.getAttribute("tooltip");
    }

    public HomePage movePointToCheckBox() {
        getAction().moveToElement(buildStatusIcon).perform();

        return this;
    }

    public FreestyleProjectConfigPage clickConfigDropDownMenuFreestyle() {
        getWait(6).until(ExpectedConditions.elementToBeClickable(configureDropDownMenu)).click();

        return new FreestyleProjectConfigPage(getDriver());
    }

    public HomePage clickProjectDropdownMenu(String projectName) {
        getWait(5).until(ExpectedConditions
                .elementToBeClickable(By.xpath("//a[@href='job/" + projectName + "/']/button"))).click();

        return this;
    }

    public boolean buildNowButtonIsDisplayed() {

        return getWait(5).until(ExpectedConditions.visibilityOf(buildNowButton)).isDisplayed();
    }

    public OrgFolderStatusPage clickOrgFolder(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new OrgFolderStatusPage(getDriver());
    }

    public PipelineStatusPage clickPipelineJob(String name) {
        getDriver().findElement(By.xpath("//span[text()='" + name + "']")).click();

        return new PipelineStatusPage(getDriver());
    }

    public String getJobListAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : jobList) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }

    public int getJobTableHeadersSize() {
        return listJobTableHeaders.size();
    }
}