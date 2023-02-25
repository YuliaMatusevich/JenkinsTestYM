package model.page;

import io.qameta.allure.Step;
import model.page.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class WorkspacePage extends MainBasePage {

    @FindBy(xpath = "//table[@class = 'fileList']//a")
    private List<WebElement> listOfFolders;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of folders")
    public List<String> getListOfFolders() {

        return listOfFolders
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
