import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class CreatePipelineInFolderTest extends BaseTest {

    private static final String NEW_ITEM = "New Item";
    private static final String NAME = "name";
    private static final String SUBMIT_BUTTON = "//button[@type = 'submit']";
    private static final String FOLDER_LOCATION = "//ul[@id='breadcrumbs']/li[3]";
    private static final String PIPELINE_LOCATION = "//ul[@id='breadcrumbs']/li[5]";
    private static final String FOLDER_OPTION = "//form/div[2]/div[2]/ul/li[1]";
    private static final String PIPELINE_OPTION = "//form/div[2]/div[1]/ul/li[2]";
    private static final String JENKINS_ICON = "jenkins-name-icon";
    private static final String CREATE_JOB = "Create a job";
    private static final String DELETE_FOLDER = "Delete Folder";
    private static final String YES_BUTTON_DELETE_PR = "yui-gen1-button";
    private static final String SELECTION_SCRIPT = "//div[@class='samples']/select/option[4]";
    private static final String RANDOM_FOLDER_NAME = RandomStringUtils.randomAlphanumeric(8);
    private static final String RANDOM_PIPELINE_NAME = RandomStringUtils.randomAlphanumeric(8);

    private void xpathClick(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }
    private void linkTextClick(String locator) { getDriver().findElement(By.linkText(locator)).click(); }
    private void idClick(String locator) { getDriver().findElement(By.id(locator)).click(); }
    private void idSendKeys(String locator1, String locator2) { getDriver().findElement(By.id(locator1)).sendKeys(locator2); }

    private String xpathGetText(String locator) {
        String text = getDriver().findElement(By.xpath(locator)).getText();
        return text;
    }

    public void deleteCreatedFolder()  {
        idClick(JENKINS_ICON);
        linkTextClick(RANDOM_FOLDER_NAME);
        linkTextClick(DELETE_FOLDER);
        idClick(YES_BUTTON_DELETE_PR);
    }

    @Test
    public void testCreateFolder() {
        linkTextClick(NEW_ITEM);
        idSendKeys(NAME, RANDOM_FOLDER_NAME);
        xpathClick(FOLDER_OPTION);
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SUBMIT_BUTTON);
        linkTextClick(NEW_ITEM);
        idSendKeys(NAME,RANDOM_PIPELINE_NAME);
        xpathClick(PIPELINE_OPTION);
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SUBMIT_BUTTON);

        String actualFolderName = xpathGetText(FOLDER_LOCATION);
        String actualPipelineName = xpathGetText(PIPELINE_LOCATION);

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);

        deleteCreatedFolder();
    }

    @Test
    public void testCreatePipelineFolderOptionJob() {
        linkTextClick(NEW_ITEM);
        idSendKeys(NAME, RANDOM_FOLDER_NAME);
        xpathClick(FOLDER_OPTION);
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SUBMIT_BUTTON);
        linkTextClick(CREATE_JOB);
        idSendKeys(NAME,RANDOM_PIPELINE_NAME);
        xpathClick(PIPELINE_OPTION);
        xpathClick(SUBMIT_BUTTON);
        xpathClick(SELECTION_SCRIPT);
        xpathClick(SUBMIT_BUTTON);

        String actualFolderName = xpathGetText(FOLDER_LOCATION);
        String actualPipelineName = xpathGetText(PIPELINE_LOCATION);

        Assert.assertEquals(RANDOM_FOLDER_NAME,actualFolderName);
        Assert.assertEquals(RANDOM_PIPELINE_NAME,actualPipelineName);

        deleteCreatedFolder();
    }
}


