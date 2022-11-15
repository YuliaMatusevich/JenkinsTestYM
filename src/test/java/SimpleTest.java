import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleTest extends BaseTest {

    @Test
    public void testSimple() {
        String text = getDriver().findElement(By.id("description-link")).getText();

        Assert.assertEquals(text, "Add description");
    }
}
