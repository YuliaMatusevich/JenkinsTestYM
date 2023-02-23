package model;

import io.qameta.allure.Step;
import model.base.MainBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkspacePage extends MainBasePage {

    @FindBy(className = "fileList")
    private List<WebElement> listOfFolders;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of folders")
    public Set<String> getListOfFolders() {

        return listOfFolders
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
