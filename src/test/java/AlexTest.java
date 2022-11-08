import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import runner.BaseTest;

public class AlexTest extends BaseTest {

    @Test
    public void testSearchResults() {
        getDriver().get("https://ya.ru/");
        WebElement text = getDriver().findElement(By.id("text"));
        text.sendKeys("Vilnius");
        WebElement button = getDriver().findElement(By.xpath("//*[@type='submit']"));
        button.click();
    }
}
