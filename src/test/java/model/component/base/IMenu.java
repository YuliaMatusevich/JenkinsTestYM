package model.component.base;

import io.qameta.allure.Step;
import model.IModel;
import model.page.RenameItemPage;
import model.page.base.BaseConfigPage;
import model.page.base.BasePage;
import model.page.base.BaseStatusPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public interface IMenu<StatusPage extends BaseStatusPage<?, ?>, ConfigPage extends BaseConfigPage<?, ?>> extends IModel {

    StatusPage getStatusPage();

    ConfigPage getConfigPage();

    default RenameItemPage<StatusPage> clickRename() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Rename"))).click();

        return new RenameItemPage<>(getDriver(), getStatusPage());
    }

    default <T extends BasePage> T clickDelete(T toPage) {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Delete"))).click();

        return toPage;
    }

    @Step("Click 'Configure' on side menu")
    default ConfigPage clickConfigure() {
        getWait(5).until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Configure"))).click();

        return getConfigPage();
    }
}
