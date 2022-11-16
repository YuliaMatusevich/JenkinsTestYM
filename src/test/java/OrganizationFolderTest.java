import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.time.Duration;

public class OrganizationFolderTest extends BaseTest {

    private static final By INPUT_NAME = By.xpath("//input [@name = 'name']");
    private static final By ORGANIZATION_FOLDER = By.xpath("//li[@class = 'jenkins_branch_OrganizationFolder']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.id("yui-gen17-button");
    private static final By DASHBOARD = By.xpath("//a[text()='Dashboard']");

    public WebElement getInputName() {
        return getDriver().findElement(INPUT_NAME);
    }
    public WebElement getOrganizationFolder() {
        return getDriver().findElement(ORGANIZATION_FOLDER);
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
    private WebDriverWait wait;
    private WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        }

        return wait;
    }

    @Test
    public void testCreateOrganizationFolder(){
        getDriver().findElement(By.linkText("New Item")).click();
        getInputName().sendKeys("First Organization Folder");
        getOrganizationFolder().click();
        getOkButton().click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("yui-gen17-button")));
        getSaveButton().click();
        getDashboard().click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href ='job/First%20Organization%20Folder/']"))
                .getText(), "First Organization Folder");
    }
}
