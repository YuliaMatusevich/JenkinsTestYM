package model.page.status;

import io.qameta.allure.Step;
import model.page.NewItemPage;
import model.page.base.BaseStatusPage;
import model.component.menu.status.FolderStatusSideMenuComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class FolderStatusPage extends BaseStatusPage<FolderStatusPage, FolderStatusSideMenuComponent> {

    @FindBy(css = "#breadcrumbs li a")
    private List<WebElement> topMenuList;

    @FindBy(xpath = "//li[@class='item'][last()]//button")
    private WebElement breadcrumbsThisFolderToggleDropdown;

    @FindBy(css = "li.yuimenuitem[index='1']")
    private WebElement newItemInDropDown;

    @FindBy(xpath = "//tr/td[3]/a/span[1]")
    private List<WebElement> jobList;

    @FindBy(linkText = "Create a job")
    private WebElement createJob;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(css = ".jenkins-input")
    private WebElement inputFieldDescription;

    @FindBy(xpath = "//div[@id='main-panel']/h1")
    private WebElement folderNameHeader;

    @FindBy(id = "empty-state-block")
    private WebElement emptyStateBlock;

    @FindBy(id = "systemmessage")
    private WebElement systemMessage;

    @Override
    protected FolderStatusSideMenuComponent createSideMenuComponent() {
        return new FolderStatusSideMenuComponent(getDriver(), this);
    }

    public FolderStatusPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the 'New Item' dropdown menu within the breadcrumbs of this folder")
    public NewItemPage<?> clickNewItemDropdownThisFolderInBreadcrumbs() {
        getWait(5).until(ExpectedConditions.visibilityOf(breadcrumbsThisFolderToggleDropdown)).click();
        getWait(5).until(ExpectedConditions.visibilityOf(newItemInDropDown)).click();

        return new NewItemPage<>(getDriver(), null);
    }

    @Step("Get list of job names")
    public List<String> getJobList() {
        return getWait(5).until(ExpectedConditions.visibilityOfAllElements(jobList))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Click on 'Create a job' link")
    public NewItemPage<?> clickCreateJob() {
        createJob.click();

        return new NewItemPage<>(getDriver(), null);
    }

    @Step("Click on 'Submit' button")
    public FolderStatusPage clickSubmitButton() {
        submitButton.click();

        return new FolderStatusPage(getDriver());
    }

    public List<String> getTopMenuLinkText() {
        return topMenuList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get name of the folder")
    public String getFolderName() {
        String[] namesBlock = getDriver().findElement(By.id("main-panel")).getText().split("\n");

        return namesBlock[1];
    }

    public String getFolderNameHeader() {
        return folderNameHeader.getText().trim();
    }

    @Step("Input '{description} into the 'Description' field")
    public FolderStatusPage setDescription(String description) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(inputFieldDescription));
        inputFieldDescription.sendKeys(description);

        return this;
    }

    @Step("Click on Freestyle project name '{name}'")
    public FreestyleProjectStatusPage clickProject(String name) {
        getDriver().findElement(By.linkText(name)).click();

        return new FreestyleProjectStatusPage(getDriver());
    }

    @Step("Click on Organization Folder name '{name}' on the Folder page")
    public OrgFolderStatusPage clickOrgFolder(String name) {
        getDriver().findElement(By.xpath("//span[text()= '" + name + "']")).click();

        return new OrgFolderStatusPage(getDriver());
    }

    @Step("Select the 'Multibranch Pipeline' item named '{multibranchPipelineName}'")
    public MultibranchPipelineStatusPage clickMultibranchPipeline(String multibranchPipelineName) {
        getDriver().findElement(By.xpath("//span[text()='" + multibranchPipelineName + "']")).click();

        return new MultibranchPipelineStatusPage(getDriver());
    }

    public WebElement getEmptyStateBlock() {

        return emptyStateBlock;
    }

    @Step("Get 'System Message' from folder")
    public String getSystemMessageFromFolder() {
        return systemMessage.getText();
    }
}