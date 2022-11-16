import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FolderTest extends BaseTest {

    private static final By OK_BUTTON = By.id("ok-button");

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");

    private static final By SAVE_BUTTON = By.id("yui-gen6-button");

    private static final By FOLDER = By.xpath("//span[text()='Folder']");

    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");


    public WebElement getInputName() {
        return getDriver().findElement(INPUT_NAME);
    }

    public WebElement getFolder() {
        return getDriver().findElement(FOLDER);
    }

    public WebElement getOkButton() {
        return getDriver().findElement(OK_BUTTON);
    }

    public WebElement getSaveButton() {
        return getDriver().findElement(SAVE_BUTTON);
    }

    public WebElement getDashboard() {
        return getDriver().findElement(DASHBOARD);
    }

    @Test
    public void create() {
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("First job");
        getFolder().click();
        getOkButton().click();
        getSaveButton().click();
        getDashboard().click();
        String job = getDriver().findElement(By.xpath("//span[text()='First job']")).getText();

        Assert.assertEquals(job, "First job");
    }

    @Test
    public void configureFolderDisplayName() {
        String firstJobName = "First job";
        String secondJobName = "Second job";
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys(firstJobName);
        getFolder().click();
        getOkButton().click();
        getSaveButton().click();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + firstJobName + "']")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/First%20job/configure']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(secondJobName);
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys("change name");
        getSaveButton().click();
        getDashboard().click();
        String changedName = getDriver().findElement(By.xpath("//span[text()='" + secondJobName + "']")).getText();

        Assert.assertEquals(changedName, secondJobName);
    }

    @Test
    public void deleteFolder() {
        String generatedString = UUID.randomUUID().toString().substring(0, 8);
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys(generatedString);
        getFolder().click();
        getOkButton().click();
        getSaveButton().click();
        getDashboard().click();
        getDriver().findElement(By.xpath("//span[text()='" + generatedString + "']")).click();
        getDriver().findElement(By.xpath("//span//*[@class='icon-edit-delete icon-md']")).click();
        getDriver().findElement(By.id("yui-gen1-button")).click();
        getDashboard().click();
         try {
            getDriver().findElement((By.xpath("//span[text()='" + generatedString + "']")));
            Assert.fail("Folder with name " + generatedString + " expected to not to be found on the screen");
        } catch (NoSuchElementException ignored) {}
    }
}