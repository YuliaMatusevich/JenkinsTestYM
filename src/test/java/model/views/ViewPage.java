package model.views;

import model.DeletePage;
import model.HomePage;
import model.base.BasePage;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPage extends MainBasePage {

    @FindBy(css = "tr td a.model-link")
    private List<WebElement> jobList;

    @FindBy(xpath = "//a[@href='delete']")
    private WebElement deleteViewItem;

    @FindBy(xpath = "//div[@class='jenkins-buttons-row jenkins-buttons-row--invert']/preceding-sibling::div")
    private WebElement descriptionText;

    @FindBy(css = ".task")
    private List<WebElement> sideMenuList;

    @FindBy(css = "#description-link")
    private WebElement editDescriptionButton;

    @FindBy(xpath = "//textarea[@name='description']")
    private WebElement description;

    @FindBy(css = "#yui-gen1-button")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@class='tabBar']/div/a")
    private List<WebElement> viewList;

    @FindBy(css = "#projectstatus th")
    private List<WebElement> listJobTableHeaders;

    @FindBy(xpath = "//span[text()='Edit View']/..")
    private WebElement editViewLink;

    @FindBy(css = ".tabBar .tab a[href*='/my-views/view/']")
    private List<WebElement> listAllViews;

    @FindBy(css = ".pane-header-title")
    private List<WebElement> listViewActiveFilters;

    @FindBy(css = "table#projectstatus th:last-child")
    private WebElement jobTableLastHeader;

    @FindBy(css = ".tab.active")
    private WebElement activeView;

    @FindBy(xpath = "//tbody/tr/td/a")
    private List<WebElement> listProjects;

    @FindBy(xpath = "//div[@id='main-panel']")
    private List<WebElement> viewMainPanel;

    @FindBy(linkText = "add some existing jobs")
    private WebElement linkTextAddSomeExistingJobs;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    private <T extends BasePage> DeletePage<T> clickDeleteView(T page) {
        deleteViewItem.click();

        return new DeletePage<>(getDriver(), page);
    }

    public DeletePage<MyViewsPage> clickDeleteViewToMyViews() {
        return clickDeleteView(new MyViewsPage(getDriver()));
    }

    public DeletePage<HomePage> clickDeleteViewToHomePage() {
        return clickDeleteView(new HomePage(getDriver()));
    }

    public String getTextDescription() {

        return descriptionText.getText();
    }

    public ArrayList<String> getSideMenuTextList() {
        ArrayList<String> sideMenuText = new ArrayList<>();
        List<WebElement> sideMenu = sideMenuList;
        for (WebElement element : sideMenu) {
            sideMenuText.add(element.getText());
        }
        return sideMenuText;
    }

    public ArrayList<String> getActualSideMenu() {
        ArrayList<String> actualSideMenu = new ArrayList<>();
        actualSideMenu.add("New Item");
        actualSideMenu.add("People");
        actualSideMenu.add("Build History");
        actualSideMenu.add("Edit View");
        actualSideMenu.add("Delete View");

        return actualSideMenu;
    }

    public ViewPage clickEditDescription() {
        editDescriptionButton.click();

        return this;
    }

    public ViewPage clearDescription() {
        getWait(5)
                .until(TestUtils.ExpectedConditions.elementIsNotMoving(description)).clear();

        return this;
    }

    public ViewPage clickSaveButton() {
        getWait(5)
                .until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return this;
    }

    public EditListViewPage clickLinkTextAddExistingJob () {
        linkTextAddSomeExistingJobs.click();

        return new EditListViewPage(getDriver());
    }

    public EditGlobalViewPage clickEditGlobalView() {
        editViewLink.click();

        return new EditGlobalViewPage(getDriver());
    }

    public EditMyViewPage clickEditMyView() {
        editViewLink.click();

        return new  EditMyViewPage(getDriver());
    }

    public EditListViewPage clickEditListView() {
        editViewLink.click();

        return new EditListViewPage(getDriver());
    }

    public String getJobListAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : jobList) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }

    public List<String> getViewList() {
        return viewList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getJobTableHeadersSize() {
        return listJobTableHeaders.size();
    }

    public String getListViewsNames() {
        StringBuilder listViewsNames = new StringBuilder();
        for (WebElement view : listAllViews) {
            listViewsNames.append(view.getText()).append(" ");
        }

        return listViewsNames.toString().trim();
    }

    public List<String> getJobNamesList() {
        return jobList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getActiveFiltersList() {

        return listViewActiveFilters.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getJobTableLastHeaderText() {

        return jobTableLastHeader.getText();
    }

    public List<String> getJobTableHeaderTextList() {

        return listJobTableHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getActiveViewName(){

        return activeView.getText();
    }

    public String getListProjectsNamesAsString() {
        StringBuilder listProjectsNames = new StringBuilder();
        for (WebElement projects : listProjects) {
            listProjectsNames.append(projects.getText()).append(" ");
        }

        return listProjectsNames.toString().trim();
    }
    
    public String getListProjectsNamesFromView() {
        String names = listProjects.get(0).getText();
        for (int i = 1; i <listProjects.size() ; i++) {
           names =  names.concat( " ").concat(listProjects.get(i).getText());
        }
        return names;
    }

    public String getTextContentOnViewMainPanel() {
        StringBuilder list = new StringBuilder();
        for (WebElement text : viewMainPanel) {
            list.append(text.getText());
        }

        return list.toString();
    }
}
