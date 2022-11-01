import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.time.Duration;

public class RustamKhRedCodeTest extends BaseTest {
    @Test

    public void testLinkName(){

        getDriver().get("https://formy-project.herokuapp.com/");

        String Link =  getDriver().findElement(By.xpath("//li/a[text()='Autocomplete']")).getText();
        Assert.assertEquals(Link, "Autocomplete");
        

    }
    @Test
    public void testLinkOpen() throws InterruptedException{


        getDriver().get("https://formy-project.herokuapp.com/");
        getDriver().findElement(By.xpath("//li/a[text()='Autocomplete']")).click();
        Thread.sleep(2000);
        String actualResult =  getDriver().findElement(By.xpath("//h1")).getText();

        Assert.assertEquals(actualResult, "Autocomplete");


    }
}
