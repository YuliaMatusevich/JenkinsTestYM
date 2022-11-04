import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class EugeneSTest extends BaseTest {


    @Test
    public void testWeather () throws InterruptedException {

        getDriver().get("https://weather.com/");
        WebElement link = getDriver().findElement(By.xpath("//span[contains(text(),'Boston, MA')]"));

        Thread.sleep(2000);

        Assert.assertEquals(link.getText(), "Boston, MA");


    }
}
