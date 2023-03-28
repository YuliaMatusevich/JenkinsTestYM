package model.page.config;

import io.qameta.allure.Step;
import model.page.base.BaseConfigPage;
import model.page.status.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.List;

import static runner.TestUtils.scrollToElement_PlaceInCenter;

public class MultiConfigurationProjectConfigPage extends BaseConfigPage<MultiConfigurationProjectStatusPage, MultiConfigurationProjectConfigPage> {

    @FindBy(name = "description")
    private WebElement inputDescription;

    @FindBy(className = "textarea-show-preview")
    private WebElement textareaShowPreview;

    @FindBy(xpath = "//div[@class='jenkins-section__title'][@id='build-steps']")
    private WebElement buildStepsSection;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement addBuildStepButton;

    @FindBy(xpath = "//a[text()='Execute Windows batch command']")
    private WebElement executeWindowsFromBuildSteps;

    @FindBy(xpath = "//a[contains(text(),'Execute shell')]")
    private WebElement executeShellFromBuildSteps;

    @FindBy(xpath = "//a[contains(text(),'Set build status to \"pending\" on GitHub commit')]")
    private WebElement buildStatusOnGitHubCommitFromBuildSteps;

    @FindBy(css = ".CodeMirror-scroll>div")
    private WebElement activateShellTextArea;

    @FindBy(css = ".jenkins-input.fixed-width")
    private WebElement executeWindowsTextArea;

    @FindBy(css = ".CodeMirror textarea")
    private WebElement executeShellTextArea;

    @FindBy(xpath = "//label[@for='enable-disable-project']")
    private WebElement enableOrDisableButton;

    @FindBy(xpath = "//div[@class='jenkins-section__title'][@id='configuration-matrix']")
    private WebElement configurationMatrixSection;

    @FindBy(xpath = "//button[@suffix='axis']")
    private WebElement buttonAddAxis;

    @FindBy(xpath = "//a[@class='yuimenuitemlabel']")
    private WebElement userDefinedAxis;

    @FindBy(xpath = "//div[@id='build-steps']/..//div[@class='advancedLink']//button[last()]")
    private WebElement advancedBuildStepsLastButton;

    @FindBy(xpath = "//div[@id='build-steps']/..//div[@class='advancedLink']//button")
    private List<WebElement> advancedButtonsInBuildStepsSection;

    @FindBy(xpath = "//input[@name='_.content']")
    private List<WebElement> contentFieldsInBuildStepsBuildStatusOnGitHubCommit;

    @Override
    protected MultiConfigurationProjectStatusPage createStatusPage() {
        return new MultiConfigurationProjectStatusPage(getDriver());
    }

    public MultiConfigurationProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter some description into description input field")
    public MultiConfigurationProjectConfigPage inputDescription(String description) {
        inputDescription.sendKeys(description);

        return new MultiConfigurationProjectConfigPage(getDriver());
    }

    @Step("Click 'Preview' button to show preview description")
    public MultiConfigurationProjectConfigPage showPreview() {
        getWait(5).until(ExpectedConditions.visibilityOf(textareaShowPreview)).click();

        return new MultiConfigurationProjectConfigPage(getDriver());
    }

    @Step("Scroll and click build steps")
    public MultiConfigurationProjectConfigPage scrollAndClickBuildSteps() {
        getWait(5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Add build step']")));
        TestUtils.scrollToElement(getDriver(), addBuildStepButton);
        getWait(20).until(TestUtils.ExpectedConditions.elementIsNotMoving(addBuildStepButton));
        addBuildStepButton.click();

        return this;
    }

    @Step("Select and click 'execute windows' in the build steps")
    public MultiConfigurationProjectConfigPage selectionAndClickExecuteWindowsFromBuildSteps() {
        executeWindowsFromBuildSteps.click();

        return this;
    }

    @Step("Select and click 'Execute Shell' from the build steps")
    public MultiConfigurationProjectConfigPage selectionAndClickExecuteShellFromBuildSteps() {
        executeShellFromBuildSteps.click();

        return this;
    }

    @Step("Enter the {'name'} command in the 'execute windows' build steps")
    public MultiConfigurationProjectConfigPage enterCommandInExecuteWindowsBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsLastButton));
        executeWindowsTextArea.sendKeys(command);

        return this;
    }

    @Step("Enter the {'name'} command in the 'execute shell' build steps")
    public MultiConfigurationProjectConfigPage enterCommandInExecuteShellBuildSteps(String command) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(advancedBuildStepsLastButton));
        getWait(5).until(ExpectedConditions.visibilityOf(activateShellTextArea));
        scrollToElement_PlaceInCenter(getDriver(), activateShellTextArea);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(activateShellTextArea));
        getWait(5).until(ExpectedConditions.elementToBeClickable(activateShellTextArea)).click();
        executeShellTextArea.sendKeys(command);

        return this;
    }

    @Step("Click 'Enable' or 'Disable' button on multi-configuration project configuration page")
    public MultiConfigurationProjectConfigPage clickEnableOrDisableButton() {
        enableOrDisableButton.click();

        return this;
    }

    @Step("Scroll and click the 'Add Axis' button")
    public MultiConfigurationProjectConfigPage scrollAndClickButtonAddAxis() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), configurationMatrixSection);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddAxis));
        buttonAddAxis.click();

        return this;
    }

    @Step("select user defined axis")
    public MultiConfigurationProjectConfigPage selectUserDefinedAxis() {
        userDefinedAxis.click();
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), buttonAddAxis);
        getWait(3).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddAxis));

        return this;
    }

    @Step("Set project name {'name'}, user defined{'name'}, axis {'number of section'}")
    public MultiConfigurationProjectConfigPage enterNameUserDefinedAxis(String projectName, String name, int numberOfSection) {
        getDriver().findElement(By.xpath
                        (String.format("//div[" + numberOfSection + "]/div/div[3]/div[2]/input[contains(@checkurl,'/job/%s/')]", projectName)))
                .sendKeys(name);

        return this;
    }

    @Step("Set value {'name'}, axis defined by user {'number of sections'}")
    public MultiConfigurationProjectConfigPage enterValueUserDefinedAxis(String value, int numberOfSection) {
        getDriver().findElement(
                        By.xpath("//div[" + numberOfSection + "]/div/div[4]/div[2]/div/div[1]/input[@name='_.valueString']"))
                .sendKeys(value);

        return this;
    }

    @Step("Scroll and click the last 'Advanced' button in the 'Build Steps' section")
    public MultiConfigurationProjectConfigPage scrollAndClickLastAdvancedButtonInBuildStepsSection() {
        getWait(5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@id='build-steps']/..//div[@class='advancedLink']//button")));
        WebElement lastAdvancedButtonInBuildStepsSection = advancedButtonsInBuildStepsSection.get(
                advancedButtonsInBuildStepsSection.size() - 1);
        TestUtils.scrollToElement(getDriver(), lastAdvancedButtonInBuildStepsSection);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(lastAdvancedButtonInBuildStepsSection));
        getWait(5).until(ExpectedConditions.elementToBeClickable(lastAdvancedButtonInBuildStepsSection)).click();

        return this;
    }

    @Step("Scroll and click the specific 'Advanced' button in the 'Build Steps' section")
    public MultiConfigurationProjectConfigPage scrollAndClickSpecificAdvancedButtonInBuildStepsSection(
            int numberOfStep) {
        getWait(5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@id='build-steps']/..//div[@class='advancedLink']//button")));
        WebElement advancedButtonInBuildStepsSection = advancedButtonsInBuildStepsSection.get(numberOfStep - 1);
        TestUtils.scrollToElement(getDriver(), advancedButtonInBuildStepsSection);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(advancedButtonInBuildStepsSection));
        getWait(5).until(ExpectedConditions.elementToBeClickable(advancedButtonInBuildStepsSection)).click();

        return this;
    }

    @Step("Select and click 'Set Build Status' on GitHub commit from build steps")
    public MultiConfigurationProjectConfigPage selectionAndClickSetBuildStatusOnGitHubCommitFromBuildSteps() {
        buildStatusOnGitHubCommitFromBuildSteps.click();

        return this;
    }

    @Step("Set last content fields in build steps build status on GitHub commit")
    public MultiConfigurationProjectConfigPage setLastContentFieldsInBuildStepsBuildStatusOnGitHubCommit(
            String content) {
        WebElement lastContentField = contentFieldsInBuildStepsBuildStatusOnGitHubCommit.get(
                contentFieldsInBuildStepsBuildStatusOnGitHubCommit.size() - 1);
        TestUtils.scrollToElement(getDriver(), lastContentField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(lastContentField));
        lastContentField.clear();
        lastContentField.sendKeys(content);

        return this;
    }

    @Step("Get last content fields in build steps build status on GitHub commit")
    public String getContentFieldsInBuildStepsBuildStatusOnGitHubCommit(int numberOfStepBuildStatusOnGitHubCommit) {
        WebElement contentField = contentFieldsInBuildStepsBuildStatusOnGitHubCommit.get(
                numberOfStepBuildStatusOnGitHubCommit - 1);
        TestUtils.scrollToElement(getDriver(), contentField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(contentField));

        return contentField.getAttribute("value");
    }
}