package model.page.config;

import io.qameta.allure.Step;
import model.page.base.BaseConfigPage;
import model.page.status.PipelineStatusPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import runner.TestUtils;

public class PipelineConfigPage extends BaseConfigPage<PipelineStatusPage, PipelineConfigPage> {

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement gitHubCheckbox;

    @FindBy(name = "_.projectUrlStr")
    private WebElement gitHubUrl;

    @FindBy(xpath = "//option[text()='try sample Pipeline...']")
    private WebElement trySamplePipelineDropDownMenu;

    @FindBy(css = "option[value='hello']")
    private WebElement helloWorldScript;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewLink;

    @FindBy(className = "textarea-hide-preview")
    private WebElement hidePreviewLink;

    @FindBy(className = "textarea-preview")
    private WebElement textareaPreview;

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement parameterizationCheckbox;

    @FindBy(id = "yui-gen1-button")
    private WebElement addParameter;

    @FindBy(id = "yui-gen9")
    private WebElement choiceParameter;

    @FindBy(name = "parameter.name")
    private WebElement parameterName;

    @FindBy(name = "parameter.choices")
    private WebElement parameterChoices;

    @FindBy(xpath = "(//select[contains(@class,'jenkins-select__input dropdownList')])[2]")
    private WebElement pipelineScriptFromScm;

    @FindBy(xpath = "(//select[contains(@class,'jenkins-select__input dropdownList')])[3]")
    private WebElement scriptScm;

    @FindBy(name = "_.url")
    private WebElement gitHub;

    @FindBy(className = "textarea-preview")
    private WebElement previewTextDescription;

    @FindBy(xpath = "//label[text()='Build after other projects are built']")
    private WebElement buildAfterOtherProjectsAreBuiltCheckbox;

    @FindBy(xpath = "//label[text()='Trigger builds remotely (e.g., from scripts)']")
    private WebElement triggerBuildsRemotelyCheckbox;

    @FindBy(xpath = "//input[@name='_.upstreamProjects']")
    private WebElement projectsToWatchField;

    @Override
    protected PipelineStatusPage createStatusPage() {
        return new PipelineStatusPage(getDriver());
    }

    public PipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click the GitHub checkbox on the configuration page")
    public PipelineConfigPage clickGitHubCheckbox() {
        gitHubCheckbox.click();

        return this;
    }

    @Step("Set the GitHub repository '{gitHubRepo}' on the configuration page")
    public PipelineConfigPage setGitHubRepo(String gitHubRepo) {
        getAction().moveToElement(gitHubUrl).click().sendKeys(gitHubRepo).perform();

        return this;
    }

    public PipelineConfigPage scrollToEndPipelineConfigPage() {
        TestUtils.scrollToEnd(getDriver());

        return this;
    }

    public PipelineConfigPage clickTrySamplePipelineDropDownMenu() {
        getWait(10).until(ExpectedConditions.visibilityOf(trySamplePipelineDropDownMenu)).click();

        return this;
    }

    public PipelineConfigPage clickHelloWorld() {
        helloWorldScript.click();

        return this;
    }

    public PipelineConfigPage setDescriptionField(String name) {
        descriptionField.sendKeys(name);

        return this;
    }

    public PipelineConfigPage clickPreviewLink() {
        previewLink.click();

        return this;
    }

    public PipelineConfigPage clickHidePreviewLink() {
        hidePreviewLink.click();

        return this;
    }

    public String getTextareaPreview() {
        return textareaPreview.getText();
    }

    public PipelineConfigPage clickParameterizationCheckbox() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(parameterizationCheckbox)).click();

        return this;
    }

    public PipelineConfigPage clickAddParameter() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(addParameter)).click();

        return this;
    }

    public PipelineConfigPage clickChoiceParameter() {
        choiceParameter.click();

        return this;
    }

    public PipelineConfigPage setChoiceParameter(String name, String choice1, String choice2, String choice3) {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), parameterizationCheckbox);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(parameterizationCheckbox));
        getAction()
                .moveToElement(parameterName)
                .click()
                .sendKeys(name)
                .moveToElement(parameterChoices)
                .click()
                .sendKeys(choice1 + Keys.ENTER, choice2 + Keys.ENTER, choice3 + Keys.ENTER)
                .perform();

        return this;
    }

    public PipelineConfigPage selectPipelineScriptFromScm() {
        TestUtils.scrollToEnd(getDriver());
        getWait(5).until(ExpectedConditions.visibilityOf(pipelineScriptFromScm));
        new Select(pipelineScriptFromScm).selectByVisibleText("Pipeline script from SCM");

        return this;
    }

    public PipelineConfigPage selectScriptScm() {
        getWait(5).until(ExpectedConditions.visibilityOf(scriptScm));
        new Select(scriptScm).selectByVisibleText("Git");

        return this;
    }

    public PipelineConfigPage setGitHubUrl(String gitHubUrl) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(gitHub)).sendKeys(gitHubUrl);

        return this;
    }

    public boolean isDisplayedPreviewTextDescription() {
        return previewTextDescription.isDisplayed();
    }

    @Step("set checkbox 'Build after other projects are built' and choose project")
    public PipelineConfigPage setTriggerBuildAfterOtherProjectsAreBuilt(String jobName) {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), triggerBuildsRemotelyCheckbox);
        getWait(3).until(ExpectedConditions.elementToBeClickable(buildAfterOtherProjectsAreBuiltCheckbox)).click();
        getWait(3).until(ExpectedConditions.elementToBeClickable(projectsToWatchField)).click();
        getWait(3).until(ExpectedConditions.elementToBeClickable(projectsToWatchField)).sendKeys(jobName);

        return this;
    }
}