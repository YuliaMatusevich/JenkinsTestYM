package model.page.view;

import io.qameta.allure.Step;
import model.page.BuildHistoryPage;
import model.page.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class MyViewsPage extends MainBasePage {

    @FindBy(css = "a[title='New View']")
    private WebElement newView;

    @FindBy(linkText = "Build History")
    private WebElement buildHistory;

    @FindBy(css = ".tabBar .tab a[href*='/my-views/view/']")
    private List<WebElement> listAllViews;

    @FindBy(xpath = "//tbody/tr/td/a")
    private List<WebElement> listProjects;

    @FindBy(id = "description-link")
    private WebElement descriptionLink;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement displayedDescriptionText;

    @FindBy(id = "description-link")
    private WebElement editDescriptionButton;

    @FindBy(xpath = "//a[@href='/iconSize?24x24']")
    private WebElement buttonSizeM;

    @FindBy(xpath = "//a[@href='/iconSize?16x16']")
    private WebElement buttonSizeS;

    @FindBy(xpath = "//a[@href='/iconSize?32x32']")
    private WebElement buttonSizeL;

    @FindBy(xpath = "//table[@class='jenkins-table jenkins-table--medium sortable']")
    private WebElement tableSizeM;

    @FindBy(xpath = "//table[@class='jenkins-table jenkins-table--small sortable']")
    private WebElement tableSizeS;

    @FindBy(xpath = "//table[@class='jenkins-table  sortable']")
    private WebElement tableSizeL;

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;


    @FindBy(css = ".item a[class=''][href$='/my-views/']")
    private WebElement myViewsTopMenuLink;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on the new view icon on the 'My Views' Page")
    public NewViewFromMyViewsPage<?> clickNewView() {
        newView.click();

        return new NewViewFromMyViewsPage<>(getDriver(), null);
    }

    @Step("Get list of views names on My Views Page")
    public String getListViewsNames() {
        StringBuilder listViewsNames = new StringBuilder();
        for (WebElement view : listAllViews) {
            listViewsNames.append(view.getText()).append(" ");
        }

        return listViewsNames.toString().trim();
    }

    @Step("Click on the viewName '{viewName}'")
    public ViewPage clickView(String viewName) {
        getDriver().findElement(By.cssSelector(".tabBar .tab a[href*='/my-views/view/" + viewName + "/']")).click();

        return new ViewPage(getDriver());
    }

    @Step("Get all project names as list")
    public String getListProjectsNamesAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : listProjects) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }

    @Step("Click 'Add description' link")
    public MyViewsPage clickAddDescription() {
        descriptionLink.click();

        return this;
    }

    @Step("Enter description text int description field")
    public MyViewsPage setDescription(String descriptionText) {
        descriptionField.sendKeys(descriptionText);

        return this;
    }

    @Step("Click 'Save' button")
    public MyViewsPage clickSaveButton() {
        saveButton.click();

        return this;
    }

    @Step("Clear the description field")
    public MyViewsPage clearDescriptionField() {
        getWait(3).until(ExpectedConditions.visibilityOf(descriptionField)).clear();

        return this;
    }

    @Step("Get description text")
    public String getDescriptionText() {

        return displayedDescriptionText.getText();
    }

    @Step("Click 'Edit description' link")
    public MyViewsPage clickEditDescription() {
        editDescriptionButton.click();

        return this;
    }

    @Step("Click the 'Medium' icon size button")
    public MyViewsPage clickSizeM() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonSizeM)).click();

        return this;
    }

    @Step("Verify that 'Medium' size table is displayed")
    public boolean tableSizeM() {
        return tableSizeM.isDisplayed();
    }

    @Step("Click the 'Small' icon size button")
    public MyViewsPage clickSizeS() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonSizeS)).click();

        return this;
    }

    @Step("Verify that 'Small' size table is displayed")
    public boolean tableSizeS() {
        return tableSizeS.isDisplayed();
    }

    @Step("Click the 'Large' icon size button")
    public MyViewsPage clickSizeL() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(buttonSizeL)).click();

        return this;
    }

    @Step("Verify that 'Large' size table is displayed")
    public boolean tableSizeL() {
        return tableSizeL.isDisplayed();
    }

    public List<String> getJobNamesList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get text of 'My View' breadcrumb")
    public String getMyViewsTopMenuLinkText() {

        return myViewsTopMenuLink.getText();
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistory.click();

        return new BuildHistoryPage(getDriver());
    }
}