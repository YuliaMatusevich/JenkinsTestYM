package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage extends MainBasePage {

    @FindBy(xpath = "//div[@id='main-panel']/ol/li/a")
    private List<WebElement> searchResults;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of search results")
    public List<String> getSearchResultList() {
        List<String> resultsList = new ArrayList<>();
        for (WebElement result : searchResults) {
            resultsList.add(result.getText().toLowerCase());
        }

        return resultsList;
    }
}
