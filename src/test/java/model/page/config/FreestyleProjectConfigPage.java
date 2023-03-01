package model.page.config;

import io.qameta.allure.Step;
import model.page.base.BaseConfigPage;
import model.page.status.FreestyleProjectStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static runner.TestUtils.*;

public class FreestyleProjectConfigPage extends BaseConfigPage<FreestyleProjectStatusPage, FreestyleProjectConfigPage> {

    @FindBy(tagName = "h1")
    private WebElement headline;

    @FindBy(css = "#main-panel > p")
    private WebElement errorMsg;

    @FindBy(xpath = "//span/label[text()='Discard old builds']")
    private WebElement discardOldBuildsCheckbox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(name = "_.numToKeepStr")
    private WebElement maxNumberOfBuildsToKeep;

    @FindBy(xpath = "//label[text() = 'This project is parameterized']")
    private WebElement checkBoxProjectIsParametrized;

    @FindBy(xpath = "//button[text() = 'Add Parameter']")
    private WebElement buttonAddParameter;

    @FindBy(xpath = "//a[text() = 'String Parameter']")
    private WebElement stringParameter;

    @FindBy(xpath = "//input[@name = 'parameter.name']")
    private WebElement fieldInputStringParameterName;

    @FindBy(xpath = "//input[@name = 'parameter.defaultValue']")
    private WebElement fieldInputStringParameterDefaultValue;

    @FindBy(xpath = "//a[text() = 'Choice Parameter']")
    private WebElement choiceParameter;

    @FindBy(xpath = "//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[2]//input[@name = 'parameter.name']")
    private WebElement fieldInputChoiceParameterName;

    @FindBy(xpath = "//textarea[@name= 'parameter.choices']")
    private WebElement fieldInputChoiceParameterValue;

    @FindBy(xpath = "//a[text() = 'Boolean Parameter']")
    private WebElement booleanParameter;

    @FindBy(xpath = "//div[@class = 'jenkins-form-item hetero-list-container with-drag-drop  ']/div[3]//input[@name = 'parameter.name']")
    private WebElement fieldInputBooleanParameterName;

    @FindBy(xpath = "//label[text() = 'Set by Default']")
    private WebElement setByDefault;

    @FindBy(xpath = "//label[text() = 'Git']")
    private WebElement radioGitButton;

    @FindBy(xpath = "//div[text() = 'Repository URL']/following-sibling::div/input")
    private WebElement fieldInputRepositoryURL;

    @FindBy(xpath = "//button[text()='Add build step']")
    private WebElement buildStepsButton;

    @FindBy(xpath = "//button[text()='Add build step']/../../..//a[@href='#']")
    private List<WebElement> listOfElementsInBuildStepsDropDown;

    @FindBy(xpath = "//button[@data-section-id='build-triggers']")
    private WebElement buildTriggersSideMenuOption;

    @FindBy(xpath = "//label[text()='Build periodically']")
    private WebElement buildPeriodicallyOption;

    @FindBy(name = "hudson-triggers-TimerTrigger")
    private WebElement buildPeriodicallyCheckbox;

    @FindBy(xpath = "//div[contains(text(), 'Branch Specifier')]/following-sibling::div/input")
    private WebElement branchSpecifierInputField;

    @FindBy(xpath = "//button[@data-section-id='source-code-management']")
    private WebElement linkSourceCodeManagement;

    @FindBy(xpath = "//a[text()= 'Invoke top-level Maven targets']")
    private WebElement invokeTopLevelMavenTargets;

    @FindBy(name = "maven.name")
    private WebElement mavenVersionField;

    @FindBy(xpath = "//select[@class='jenkins-input']//option[last()]")
    private WebElement lastMavenOptionInMavenVersionField;

    @FindBy(id = "textarea._.targets")
    private WebElement goalsField;

    @FindBy(xpath = "//button[text()='Add post-build action']")
    private WebElement postBuildActionButton;

    @FindBy(xpath = "//a[text()= 'Build other projects']")
    private WebElement buildOtherProjects;

    @FindBy(xpath = "//input[@name = 'buildTrigger.childProjects']")
    private WebElement projectToBuildField;

    @FindBy(xpath = "//a[text() = 'Publish JUnit test result report']")
    private WebElement publishJUnitTestResultReport;

    @FindBy(name = "_.testResults")
    private WebElement reportPathField;

    @FindBy(name = "_.healthScaleFactor")
    private WebElement healthReportAmplificationFactorField;

    @Override
    protected FreestyleProjectStatusPage createStatusPage() {
        return new FreestyleProjectStatusPage(getDriver());
    }

    public FreestyleProjectConfigPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Discard Old Builds' checkbox")
    public FreestyleProjectConfigPage clickDiscardOldBuildsCheckbox() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(discardOldBuildsCheckbox)).click();

        return this;
    }

    @Step("Input '{numberOfDays}' in the 'Days to keep builds' field of the 'Discard old builds' option")
    public FreestyleProjectConfigPage typeDaysToKeepBuilds(String numberOfDays) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(daysToKeepBuilds)).sendKeys(numberOfDays);

        return this;
    }

    public String getNumberOfDaysToKeepBuilds() {

        return daysToKeepBuilds.getAttribute("value");
    }

    @Step("Set '{numberOfBuilds}' in the 'Max # of builds to keep' field")
    public FreestyleProjectConfigPage typeMaxNumberOfBuildsToKeep(String numberOfBuilds) {
        maxNumberOfBuildsToKeep.sendKeys(numberOfBuilds);

        return this;
    }

    @Step("Set '{numberOfBuilds}' in the 'Max # of builds to keep' field")
    public FreestyleProjectConfigPage typeMaxNumberOfBuildsToKeep(int numberOfBuilds) {
        maxNumberOfBuildsToKeep.click();
        maxNumberOfBuildsToKeep.sendKeys(Integer.toString(numberOfBuilds));

        return this;
    }

    public String getMaxNumberOfBuildsToKeep() {

        return maxNumberOfBuildsToKeep.getAttribute("value");
    }

    @Step("Switch on 'This Project Is Parametrized' checkbox")
    public FreestyleProjectConfigPage switchONCheckBoxThisProjectIsParametrized() {
        checkBoxProjectIsParametrized.click();

        return this;
    }

    @Step("Open 'Add Parameter' dropdown")
    public FreestyleProjectConfigPage clickButtonAddParameter() {
        buttonAddParameter.click();

        return this;
    }

    @Step("Select 'String Parameter' in the 'Add Parameter' dropdown")
    public FreestyleProjectConfigPage selectStringParameter() {
        stringParameter.click();

        return this;
    }

    @Step("Input '{stringParameterName}' in the 'Name' field of the 'String Parameter' section")
    public FreestyleProjectConfigPage inputStringParameterName(String stringParameterName) {
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputStringParameterName)).sendKeys(stringParameterName);

        return this;
    }

    @Step("Input '{stringParameterDefaultValue}' in the 'Default Value' field of the 'String Parameter' section")
    public FreestyleProjectConfigPage inputStringParameterDefaultValue(String stringParameterDefaultValue) {
        fieldInputStringParameterDefaultValue.sendKeys(stringParameterDefaultValue);

        return this;
    }

    @Step("Select 'Choice Parameter' in the 'Add Parameter' dropdown")
    public FreestyleProjectConfigPage selectChoiceParameter() {
        choiceParameter.click();

        return this;
    }

    @Step("Input '{choiceParameterName}' in the 'Name' field of the 'Choice Parameter' section")
    public FreestyleProjectConfigPage inputChoiceParameterName(String choiceParameterName) {
        getWait(5).until(ExpectedConditions.visibilityOf(fieldInputChoiceParameterName)).sendKeys(choiceParameterName);

        return this;
    }

    @Step("Input '{choiceParameterValue}' in the 'Choices' field of the 'Choice Parameter' section")
    public FreestyleProjectConfigPage inputChoiceParameterValue(String choiceParameterValue) {
        fieldInputChoiceParameterValue.sendKeys(choiceParameterValue);

        return this;
    }

    @Step("Select 'Boolean Parameter' in the 'Add Parameter' dropdown")
    public FreestyleProjectConfigPage selectBooleanParameter() {
        booleanParameter.click();

        return this;
    }

    @Step("Input '{booleanParameterName}' in the 'Name' field of the 'Boolean Parameter' section")
    public FreestyleProjectConfigPage inputBooleanParameterName(String booleanParameterName) {
        getWait(10).until(ExpectedConditions.visibilityOf(fieldInputBooleanParameterName)).sendKeys(booleanParameterName);

        return this;
    }

    @Step("Switch on 'Set by Default' in the 'Boolean Parameter' section")
    public FreestyleProjectConfigPage switchONBooleanParameterAsDefault() {
        setByDefault.click();

        return this;
    }

    @Step("Scroll down and select 'String Parameter' in the 'Add Parameter' dropdown")
    public FreestyleProjectConfigPage scrollAndClickAddParameterButton() {
        scrollToElement_PlaceInCenter(getDriver(), buttonAddParameter);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(buttonAddParameter)).click();

        return this;
    }

    @Step("Select 'Git' radio button in the 'Source Code Management' section of the Configuration page")
    public FreestyleProjectConfigPage selectSourceCodeManagementGIT() {
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(radioGitButton));
        getAction()
                .scrollToElement(radioGitButton)
                .moveToElement(radioGitButton)
                .click()
                .perform();

        return this;
    }

    @Step("Input '{url}' in the 'Repository URL' field. Refer to TestDataUtils.GITHUB_REPOSITORY_URL")
    public FreestyleProjectConfigPage inputGITRepositoryURL(String url) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldInputRepositoryURL)).sendKeys(url);

        return this;
    }

    @Step("Switch off 'This Project Is Parametrized' checkbox")
    public FreestyleProjectConfigPage switchOFFCheckBoxThisProjectIsParametrized() {
        checkBoxProjectIsParametrized.click();

        return this;
    }

    @Step("Open ‘Add Build Steps' dropdown in the ‘Build Steps' section")
    public FreestyleProjectConfigPage openAddBuildStepDropDown() {
        scrollToElement_PlaceInCenter(getDriver(), buildStepsButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(buildStepsButton));
        getWait(5).until(ExpectedConditions.elementToBeClickable(buildStepsButton));
        buildStepsButton.click();

        return this;
    }

    @Step("Get list of displayed build steps in the 'Add build steps' dropdown")
    public Set<String> getOptionsInBuildStepsDropDown() {

        return listOfElementsInBuildStepsDropDown
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Step("Click 'Build Triggers' on the side menu")
    public FreestyleProjectConfigPage clickBuildTriggersSideMenuOption() {
        buildTriggersSideMenuOption.click();

        return this;
    }

    @Step("Select 'Build periodically' checkbox in the 'Build Triggers' section")
    public FreestyleProjectConfigPage scrollAndClickBuildPeriodicallyCheckbox() {
        scrollToElement_PlaceInCenter(getDriver(), buildPeriodicallyOption);
        getWait(20).until(TestUtils.ExpectedConditions.elementIsNotMoving(buildPeriodicallyOption)).click();
        getWait(10).until(ExpectedConditions
                .elementSelectionStateToBe(By.name("hudson-triggers-TimerTrigger"), true));

        return this;
    }

    @Step("Verify if 'Build Periodically' checkbox in the 'Build Triggers' section is checked")
    public boolean verifyThatBuildPeriodicallyCheckboxIsSelected() {

        return buildPeriodicallyCheckbox.isSelected();
    }

    @Step("Input '{branchSpecifier}' in the 'Branch Specifier' field")
    public FreestyleProjectConfigPage inputBranchSpecifier(String branchSpecifier) {
        scrollToElement_PlaceInCenter(getDriver(), branchSpecifierInputField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(branchSpecifierInputField)).clear();
        branchSpecifierInputField.sendKeys(branchSpecifier);

        return this;
    }

    @Step("Click 'Source Code Management' on the side menu")
    public FreestyleProjectConfigPage clickLinkSourceCodeManagement() {
        linkSourceCodeManagement.click();

        return this;
    }

    public String getHeadlineText() {
        return headline.getText();
    }

    @Step("Select ‘Invoke top-level Maven targets’ in dropdown menu of the 'Build Steps' section")
    public FreestyleProjectConfigPage selectInvokeTopLevelMavenTargets() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(invokeTopLevelMavenTargets));
        invokeTopLevelMavenTargets.click();

        return this;
    }

    @Step("Select last available Maven version in the dropdown menu in ‘Maven Version’ field")
    public FreestyleProjectConfigPage selectMavenVersion() {
        scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(mavenVersionField));
        mavenVersionField.click();
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(lastMavenOptionInMavenVersionField));
        lastMavenOptionInMavenVersionField.click();

        return this;
    }

    @Step("Set ‘{goal}’ in ‘Goals’ field")
    public FreestyleProjectConfigPage setGoal(String goal) {
        scrollToEnd(getDriver());
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(goalsField));
        goalsField.clear();
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(goalsField));
        goalsField.sendKeys(goal);

        return this;
    }

    @Step("Open 'Add post-build action' dropdown in the 'Post-build Actions' section")
    public FreestyleProjectConfigPage openAddPostBuildActionDropDown() {
        scrollToElement_PlaceInCenter(getDriver(), postBuildActionButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(postBuildActionButton));
        getWait(5).until(ExpectedConditions.elementToBeClickable(postBuildActionButton));
        postBuildActionButton.click();

        return this;
    }

    @Step("Select 'Build other project' option in the 'Post-build Actions' dropdown menu")
    public FreestyleProjectConfigPage selectBuildOtherProjects() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buildOtherProjects));
        buildOtherProjects.click();

        return this;
    }

    @Step("Input the project '{name} in the 'Project to build' field of the 'Build other project' section")
    public FreestyleProjectConfigPage setProjectToBuildName(String name) {
        scrollToElement_PlaceInCenter(getDriver(), projectToBuildField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(projectToBuildField));
        getWait(5).until(ExpectedConditions.elementToBeClickable(projectToBuildField));
        projectToBuildField.click();
        projectToBuildField.sendKeys(name);

        return this;
    }

    @Step("Select 'Publish JUnit Test Result Report' option of the 'Post-build actions' dropdown menu")
    public FreestyleProjectConfigPage selectPublishJUnitTestResultReport() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(publishJUnitTestResultReport));
        publishJUnitTestResultReport.click();

        return this;
    }

    @Step("Set ‘{reportPath}’ in ‘Test report XMLs’ field")
    public FreestyleProjectConfigPage setReportPath(String reportPath) {
        scrollToEnd(getDriver());
        getWait(10).until(TestUtils.ExpectedConditions.elementIsNotMoving(reportPathField));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", reportPathField);
        reportPathField.sendKeys(reportPath);

        return this;
    }

    @Step("Clear 'Health report amplification factor' field")
    public FreestyleProjectConfigPage clearHealthReportAmplificationFactorField() {
        scrollToElement(getDriver(), healthReportAmplificationFactorField);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(healthReportAmplificationFactorField));
        healthReportAmplificationFactorField.clear();

        return this;
    }
}