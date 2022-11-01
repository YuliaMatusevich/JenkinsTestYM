import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class RustamKhRedCodeTest extends BaseTest {
    @Test

    public void testLinkName(){

        getDriver().get("https://formy-project.herokuapp.com/");

        String Link =  getDriver().findElement(By.xpath("//li/a[text()='Autocomplete']")).getText();
        Assert.assertEquals(Link, "Autocomplete");
        

    }
}
