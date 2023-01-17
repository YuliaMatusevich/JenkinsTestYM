package model;

import model.base.MainBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersPage extends MainBasePage {

    @FindBy(css = ".jenkins-table__button")
    private WebElement ConfigureUser;

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUser;

    @FindBy(className = "jenkins-table__link")
    private List<WebElement> userIdList;

    @FindBy(xpath = "//tr/td[3]")
    private List<WebElement> userFullNameList;

    @FindBy(className = "item")
    private WebElement rootMenuDashboardLink;

    @FindBy(css = "a[href='user/admin/'] > .jenkins-menu-dropdown-chevron")
    private WebElement userDropdownMenu;

    @FindBy(linkText = "this list")
    private WebElement thisListLink;

    public ManageUsersPage(WebDriver driver) {
        super(driver);
    }

    public ConfigureUserPage clickConfigureUser() {
        ConfigureUser.click();

        return new ConfigureUserPage(getDriver());
    }

    public CreateUserPage clickCreateUser() {
        createUser.click();

        return new CreateUserPage(getDriver());
    }

    public List<String> getListOfUserIDs() {
        List<String> listOfUserIDs = new ArrayList<>();
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(userIdList));
        for (int i = 0; i < userIdList.size(); i++) {
            listOfUserIDs.add(i, userIdList.get(i).getText());
        }
        return listOfUserIDs;
    }

    public List<String> getListOfFullNamesOfUsers() {
        List<String> listOfFullNamesOfUsers = new ArrayList<>();
        getWait(5).until(ExpectedConditions.visibilityOfAllElements(userFullNameList));
        for (int i = 0; i < userFullNameList.size(); i++) {
            listOfFullNamesOfUsers.add(i, userFullNameList.get(i).getText());
        }
        return listOfFullNamesOfUsers;
    }

    public HomePage rootMenuDashboardLinkClick() {
        rootMenuDashboardLink.click();

        return new HomePage(getDriver());
    }

    public DeletePage<ManageUsersPage> clickDeleteUser(String name) {
        getWait(3).until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='user/" + name.toLowerCase() + "/delete']"))).click();

        return new DeletePage<>(getDriver(), this);
    }

    public PeoplePage clickThisListLink(){
        thisListLink.click();

        return new PeoplePage(getDriver());
    }
}
