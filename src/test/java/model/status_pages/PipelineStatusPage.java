package model.status_pages;

import model.BuildStatusPage;
import model.HomePage;
import model.base.BaseStatusPage;
import model.status_side_menu_component.PipelineStatusSideMenuComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class PipelineStatusPage extends BaseStatusPage<PipelineStatusPage, PipelineStatusSideMenuComponent> {

    @FindBy(xpath = "//div[@id='description']//a")
    private WebElement editDescriptionButton;

    @FindBy(xpath = "//div[@id='description']//textarea")
    private WebElement descriptionArea;

    @FindBy(xpath = "//div[@align='right']/span")
    private WebElement saveButton;

    @FindBy(id = "yui-gen1-button")
    private WebElement disableProjectButton;

    @FindBy(id = "yui-gen1")
    private WebElement enableProjectButton;

    @FindBy(className = "duration")
    private WebElement stageView;

    @FindBy(id = "enable-project")
    private WebElement messageDisabledProject;

    @FindBy(id = "description-link")
    private WebElement editDescriptionLink;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildLink;

    @Override
    protected PipelineStatusSideMenuComponent createSideMenuComponent() {
        return new PipelineStatusSideMenuComponent(getDriver(), this);
    }

    public PipelineStatusPage(WebDriver driver) {
        super(driver);
    }

    public PipelineStatusPage editDescription(String text) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(editDescriptionButton)).click();
        getWait(5).until(ExpectedConditions.elementToBeClickable(descriptionArea)).clear();
        descriptionArea.sendKeys(text);

        return this;
    }

    public PipelineStatusPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    public HomePage confirmAlertAndDeletePipeline() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public String getPipelineName() {

        return getNameText().substring(getNameText().indexOf(" ") + 1);
    }

    public PipelineStatusPage clickDisableProject() {
        disableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    public PipelineStatusPage clickEnableProject() {
        enableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    public PipelineStatusPage clickBuildNow(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/build?delay=0sec']", name))).click();
        getWait(20).until(ExpectedConditions.visibilityOf(stageView));

        return this;
    }

    public String getMessageDisabledProject() {
        return messageDisabledProject.getText().split("\n")[0];
    }

    public BuildStatusPage clickLastBuildLink() {
        getDriver().navigate().refresh();
        lastBuildLink.click();

        return new BuildStatusPage(getDriver());
    }
}