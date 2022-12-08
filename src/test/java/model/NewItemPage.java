package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import runner.TestUtils;

public class NewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(className = "com_cloudbees_hudson_plugins_folder_Folder")
    private WebElement folderType;

    @FindBy(className = "btn-decorator")
    private WebElement okButton;

    @FindBy(xpath = "//li[@class = 'jenkins_branch_OrganizationFolder']")
    private WebElement orgFolder;

    @FindBy(xpath = "//span[contains(text(), 'Multi-configuration project')]")
    private WebElement multiConfigurationProject;

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage setProjectName(String name) {
        getWait(2).until(ExpectedConditions.visibilityOf(itemName)).sendKeys(name);

        return this;
    }

    public FolderConfigPage selectFolderAndClickOk() {
        folderType.click();
        okButton.click();

        return new FolderConfigPage(getDriver());
    }

    public OrgFolderConfigPage selectOrgFolderAndClickOk() {
        TestUtils.scrollToElement(getDriver(), orgFolder);
        orgFolder.click();
        okButton.click();

        return new OrgFolderConfigPage(getDriver());
    }

    public MulticonfigurationProjectConfigPage selectMultiConfigurationProjectAndClickOk() {
        multiConfigurationProject.click();
        okButton.click();

        return new MulticonfigurationProjectConfigPage(getDriver());
    }
}