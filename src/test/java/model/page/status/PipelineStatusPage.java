package model.page.status;

import io.qameta.allure.Step;
import model.page.BuildStatusPage;
import model.page.HomePage;
import model.page.base.BaseStatusPage;
import model.component.menu.status.PipelineStatusSideMenuComponent;
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

    @FindBy(id = "enable-project")
    private WebElement messageDisabledProject;

    @FindBy(id = "description-link")
    private WebElement editDescriptionLink;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildLink;

    @FindBy(css = ".build-status-icon__outer>[tooltip = 'Success &gt; Console Output']")
    private WebElement buildLoadingIconSuccess;

    @FindBy(css = "#no-builds")
    private WebElement buildsInformation;

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

        return getHeaderText().substring(getHeaderText().indexOf(" ") + 1);
    }

    @Step("Click 'disable project' button")
    public PipelineStatusPage clickDisableProject() {
        disableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    public PipelineStatusPage clickEnableProject() {
        enableProjectButton.click();

        return new PipelineStatusPage(getDriver());
    }

    @Step("Click 'Build Now' button")
    public PipelineStatusPage clickBuildNow(String name) {
        getDriver().findElement(By.xpath(String.format("//a[@href='/job/%s/build?delay=0sec']", name))).click();
        getWait(60).until(ExpectedConditions.visibilityOf((buildLoadingIconSuccess)));
        getWait(10).until(ExpectedConditions.attributeToBe(buildsInformation, "style", "display: none;"));

        return this;
    }

    @Step("Get message that project is disabled")
    public String getMessageDisabledProject() {
        return messageDisabledProject.getText().split("\n")[0];
    }

    public BuildStatusPage clickLastBuildLink() {
        getDriver().navigate().refresh();
        lastBuildLink.click();

        return new BuildStatusPage(getDriver());
    }
}