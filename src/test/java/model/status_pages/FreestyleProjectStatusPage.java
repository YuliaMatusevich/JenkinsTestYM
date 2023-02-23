package model.status_pages;

import model.HomePage;
import model.base.BaseStatusPage;
import model.status_side_menu_component.FreestyleProjectStatusSideMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class FreestyleProjectStatusPage extends BaseStatusPage<FreestyleProjectStatusPage, FreestyleProjectStatusSideMenuComponent> {

    @FindBy(xpath = "//textarea[@name = 'description']")
    private WebElement fieldDescriptionText;

    @FindBy(id = "yui-gen2")
    private WebElement buttonSave;

    @FindBy(id = "enable-project")
    private WebElement warningForm;

    @FindBy(name = "Submit")
    private WebElement disableProjectBtn;

    @FindBy(linkText = "Edit description")
    private WebElement buttonEditDescription;

    @FindBy(xpath = "//div[@id = 'main-panel']//h2")
    private List<WebElement> listOfH2Headers;

    @FindBy(xpath = "//a[@class = 'model-link jenkins-icon-adjacent']")
    private List<WebElement> listOfDownstreamProjects;


    //a[@class = "model-link jenkins-icon-adjacent"]
    @Override
    protected FreestyleProjectStatusSideMenuComponent createSideMenuComponent() {
        return new FreestyleProjectStatusSideMenuComponent(getDriver(), this);
    }

    public FreestyleProjectStatusPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleProjectStatusPage clickDisableProjectBtn() {
        disableProjectBtn.click();

        return this;
    }

    public FreestyleProjectStatusPage inputAndSaveDescriptionText(String description) {
        getWait(10).until(ExpectedConditions.elementToBeClickable(fieldDescriptionText)).clear();
        fieldDescriptionText.sendKeys(description);
        getWait(10).until(ExpectedConditions.elementToBeClickable(buttonSave)).click();

        return this;
    }

    public HomePage confirmAlertAndDeleteProject() {
        getDriver().switchTo().alert().accept();

        return new HomePage(getDriver());
    }

    public String getWarningMsg() {
        return warningForm.getText().substring(0, warningForm.getText().indexOf("\n"));
    }

    public FreestyleProjectStatusPage clickButtonEditDescription() {
        buttonEditDescription.click();

        return this;
    }

    public FolderStatusPage confirmAlertAndDeleteProjectFromFolder() {
        getDriver().switchTo().alert().accept();

        return new FolderStatusPage(getDriver());
    }

    public List<String> getH2HeaderNamesList() {
        return listOfH2Headers
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getDownstreamProjectNamesList() {
        return listOfDownstreamProjects
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }


}