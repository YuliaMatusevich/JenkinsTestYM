import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class createFreestyleProjectTest extends BaseTest {

    private void click(By by) {getDriver().findElement(by).click();}

    @Test
    public void createFreestyleProjectWithEngineerName() {

        String expectedResult = "Engineer";

        click(By.linkText("New Item"));
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);
        click(By.className("label"));
        click(By.id("ok-button"));
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        WebElement registeredProject = getDriver().findElement(By.xpath("//h1[@class='job-index-" +
                "headline page-headline']"));

        String actualResult = registeredProject.getText().substring(registeredProject.getText().length()-8);

        Assert.assertEquals(actualResult, expectedResult);
    }
}
