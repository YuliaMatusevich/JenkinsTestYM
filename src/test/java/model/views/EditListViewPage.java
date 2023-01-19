package model.views;

import model.base.MainBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.BaseUtils;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditListViewPage extends MainBasePage {

    @FindBy(css = "#yui-gen3-button")
    private WebElement addColumnButton;

    @FindBy(css = ".repeated-chunk__header")
    private List<WebElement> columns;

    @FindBy(css = "input[json='true']+label")
    private List<WebElement> listJobsToAddListView;

    @FindBy(name = "name")
    private WebElement viewName;

    @FindBy(css = ".bottom-sticker-inner--stuck")
    private WebElement bottomStickerDynamic;

    @FindBy(xpath = "//button[text() = 'OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[text() = 'Apply']")
    private WebElement applyButton;

    @FindBy(css = "#notification-bar")
    private WebElement confirmAfterClickingApply;

    @FindBy(css = "input[checked='true']")
    private WebElement markedCheckboxNameJob;

    @FindBy(css = "input[name='useincluderegex']+label")
    private WebElement regexFilterCheckbox;

    @FindBy(css = "input[name='includeRegex']")
    private WebElement regexFilterTextArea;

    @FindBy(xpath = "//div[@descriptorid='hudson.views.StatusColumn']//div[@class='dd-handle']")
    private WebElement statusColumnDragHandle;

    @FindBy(xpath = "//div[text()='Jobs']")
    private WebElement sectionJobs;

    @FindBy(css = "input[name=filterQueue]")
    private WebElement filterBuildQueueOptionCheckBox;

    @FindBy(css = ".hetero-list-add[suffix='columns']")
    private WebElement addColumnDropDownMenu;

    @FindBy(xpath = "//a[@class='yuimenuitemlabel' and text()='Git Branches']")
    private WebElement gitBranchesColumnMenuOption;

    @FindBy(css = "a.yuimenuitemlabel")
    private List<WebElement> listAddColumnDropDownMenuItemsText;

    @FindBy(xpath = "//div[contains(@class, 'hetero-list-container')]/div[@class='repeated-chunk'][last()]//button")
    private WebElement lastExistingColumnDeleteButton;

    @FindBy(css = "div[descriptorid='hudson.views.StatusColumn'] button.repeatable-delete")
    private WebElement deleteStatusColumnButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement description;

    @FindBy(css = "div:nth-of-type(5) > .jenkins-section__title")
    private WebElement uniqueSectionOnListViewEditPage;

    public EditListViewPage(WebDriver driver) {
        super(driver);
    }

    public EditListViewPage addJobsToListView(int numberOfJobs) {
        if (listJobsToAddListView.size() < numberOfJobs) {
            BaseUtils.log("Create more items");
            return null;
        }
        for (int i = 0; i < numberOfJobs; i++) {
            listJobsToAddListView.get(i).click();
        }

        return this;
    }

    public EditListViewPage addAllJobsToListView() {
        try {
            listJobsToAddListView
                    .stream()
                    .forEach(WebElement::click);
        } catch (NoSuchElementException exception) {
            BaseUtils.log(String.format("Jobs not found at " + getDriver().getTitle()));
        }

        return this;
    }

    public int getCountColumns() {
        return columns.size();
    }

    public EditListViewPage addColumn(String type) {
        TestUtils.scrollToEnd(getDriver());
        getWait(10).until(ExpectedConditions.invisibilityOf(bottomStickerDynamic));
        addColumnButton.click();
        getDriver().findElement(By.linkText(type)).click();

        return this;
    }

    public EditListViewPage renameView(String name) {
        viewName.clear();
        viewName.sendKeys(name);

        return this;
    }

    public ViewPage clickOkButton() {
        okButton.click();

        return new ViewPage(getDriver());
    }

    public EditListViewPage addJobToView(String name) {
        TestUtils.scrollToElement(getDriver(), sectionJobs);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(
                By.xpath(String.format("//label[@title='%s']", name)))).click();

        return this;
    }

    public List<String> getNamesAllColumns() {
        List<String> list = new ArrayList<>();
        for (WebElement column : columns) {
            list.add(column.getText());
        }

        return list;
    }

    public EditListViewPage removeSomeColumns(List<String> nameRemoveColumns) {
        List<String> namesAllColumns = getNamesAllColumns();
        for (String name : nameRemoveColumns) {
            if (namesAllColumns.contains(name)) {
                WebElement element = getDriver().findElement(By.xpath(String.format("//div[contains(text(),'%s')]/button[@title='Delete']", name)));
                TestUtils.scrollToElement_PlaceInCenter(getDriver(), element);
                getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(element)).click();
            }
        }

        return this;
    }

    public EditListViewPage clickApplyButton() {
        applyButton.click();

        return this;
    }

    public String getTextConfirmAfterClickingApply() {

        return getWait(15).until(ExpectedConditions.visibilityOf(
                confirmAfterClickingApply)).getText();
    }

    public String getSelectedJobName() {

        return markedCheckboxNameJob.getAttribute("name");
    }

    public EditListViewPage scrollToRegexFilterCheckboxPlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), regexFilterCheckbox);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(regexFilterCheckbox));

        return this;
    }

    public boolean isRegexCheckboxChecked() {

        return regexFilterCheckbox.isSelected();
    }

    public EditListViewPage clickRegexCheckbox() {
        regexFilterCheckbox.click();

        return this;
    }

    public EditListViewPage clearAndSendKeysRegexTextArea(String regex) {
        regexFilterTextArea.clear();
        regexFilterTextArea.sendKeys(regex);

        return this;
    }

    public EditListViewPage scrollToStatusColumnDragHandlePlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), statusColumnDragHandle);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(statusColumnDragHandle));

        return this;
    }

    public EditListViewPage dragByYOffset(int offset) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(statusColumnDragHandle)
                .clickAndHold(statusColumnDragHandle)
                .moveByOffset(0, offset / 2)
                .moveByOffset(0, offset / 2)
                .release().perform();

        return this;
    }

    public EditListViewPage selectFilterBuildQueueOptionCheckBox() {
        filterBuildQueueOptionCheckBox.findElement(By.xpath("following-sibling::label")).click();

        return this;
    }

    public EditListViewPage clickAddColumnDropDownMenu() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), addColumnDropDownMenu);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(addColumnDropDownMenu));
        addColumnDropDownMenu.click();

        return this;
    }

    public EditListViewPage clickGitBranchesColumnMenuOption() {
        gitBranchesColumnMenuOption.click();

        return this;
    }

    public Map<String, String> getMapMatchingColumnDropDownMenuItemsAndJobTableHeader() {
        final String[] listOrderedAllPossibleJobTableHeadersText = {
                "S", "W", "Name", "Last Success", "Last Failure", "Last Stable",
                "Last Duration", "", "Git Branches", "Name", "Description"};

        Map<String, String> tableMenuMap = new HashMap<>();
        for (int i = 0; i < listAddColumnDropDownMenuItemsText.size(); i++) {
            tableMenuMap.put(listAddColumnDropDownMenuItemsText.get(i).getText(), listOrderedAllPossibleJobTableHeadersText[i]);
        }

        return tableMenuMap;
    }

    public int getNumberOfAllAddColumnDropDownMenuItems(){

        return listAddColumnDropDownMenuItemsText.size();
    }

    public EditListViewPage clickLastExistingColumnDeleteButton(){
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), lastExistingColumnDeleteButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(lastExistingColumnDeleteButton));
        lastExistingColumnDeleteButton.click();

        return this;
    }

    public EditListViewPage clickDeleteStatusColumnButton() {
        deleteStatusColumnButton.click();
        getWait(5).until(ExpectedConditions.invisibilityOf(deleteStatusColumnButton));

        return this;
    }

    public EditListViewPage scrollToDeleteStatusColumnButtonPlaceInCenterWaitTillNotMoving() {
        TestUtils.scrollToElement_PlaceInCenter(getDriver(), deleteStatusColumnButton);
        getWait(5).until(TestUtils.ExpectedConditions.elementIsNotMoving(deleteStatusColumnButton));

        return this;
    }

    public EditListViewPage addDescription(String desc) {
        description.sendKeys(desc);

        return this;
    }

    public EditListViewPage clickJobsCheckBoxForAddRemoveToListView(String jobName) {
        getDriver().findElement(By.cssSelector("label[title='" + jobName + "']")).click();

        return this;
    }

    public String getUniqueSectionOnListViewEditPage() {

        return uniqueSectionOnListViewEditPage.getText();
    }
}
