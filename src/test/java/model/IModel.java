package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface IModel {

    WebDriver getDriver();

    WebDriverWait getWait(int seconds);
}
