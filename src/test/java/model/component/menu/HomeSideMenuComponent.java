package model.component.menu;

import io.qameta.allure.Step;
import model.component.base.BaseSideMenuComponent;
import model.page.*;
import model.page.view.MyViewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomeSideMenuComponent extends BaseSideMenuComponent {

    @FindBy(linkText = "New Item")
    private WebElement newItem;

    @FindBy(xpath = "//span/a[@href='/asynchPeople/']")
    private WebElement people;

    @FindBy(linkText = "Build History")
    private WebElement buildHistory;

    @FindBy(linkText = "Manage Jenkins")
    private WebElement manageJenkins;

    @FindBy(css = "a[href='/me/my-views']")
    private WebElement myViews;

    @FindBy(xpath = "//a[@href='/computer/']")
    private WebElement buildExecutorStatus;

    @FindBy(linkText = "Delete Agent")
    private WebElement deleteAgent;

    @FindBy(xpath = "//div[@id='executors']//tr//th/a")
    private List<WebElement> nodeList;

    public HomeSideMenuComponent(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'New Item' on the Side Menu")
    public NewItemPage<?> clickNewItem() {
        newItem.click();

        return new NewItemPage<>(getDriver(), null);
    }

    @Step("Click on 'People' link on the side menu")
    public PeoplePage clickPeople() {
        people.click();

        return new PeoplePage(getDriver());
    }

    public BuildHistoryPage clickBuildHistory() {
        buildHistory.click();

        return new BuildHistoryPage(getDriver());
    }

    @Step("Click on 'Manage Jenkins' link on the side menu")
    public ManageJenkinsPage clickManageJenkins() {
        manageJenkins.click();

        return new ManageJenkinsPage(getDriver());
    }

    public MyViewsPage clickMyViewsSideMenuLink() {
        myViews.click();

        return new MyViewsPage(getDriver());
    }

    @Step("Click 'Build Executor Status' menu link")
    public ManageNodesAndCloudsPage clickBuildExecutorStatus() {
        buildExecutorStatus.click();
        return new ManageNodesAndCloudsPage(getDriver());
    }

    @Step("Click on '{nodeName}' node dropdown menu")
    public HomeSideMenuComponent clickNodeDropdownMenu(String nodeName) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.xpath("//tr/th/a[@href='/computer/" + nodeName + "/']/button"))).click();

        return this;
    }

    @Step("Select 'DeleteAgent' option in dropdown menu")
    public DeletePage<ManageNodesAndCloudsPage> selectDeleteAgentInDropdown() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(deleteAgent)).click();

        return new DeletePage<>(getDriver(), new ManageNodesAndCloudsPage(getDriver()));
    }

    @Step("Get Node List from 'Build Executor Status' section")
    public List<String> getNodeList() {
        return nodeList
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}