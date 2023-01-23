package tests;

import model.HomePage;
import model.views.ViewPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;

import static runner.TestUtils.getRandomStr;

public class ListViewTest extends BaseTest {

    private static final String LIST_VIEW_NAME = getRandomStr();

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = getRandomStr();

        int quantityProjectsInListView = new HomePage(getDriver())
                .getSideMenuFrame()
                .clickNewItem()
                .setItemName(projectOne)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .getBreadcrumbs()
                .clickDashboard()
                .clickAddViewLink()
                .setViewName(LIST_VIEW_NAME)
                .setListViewType()
                .clickCreateButtonToEditListView()
                .addJobToView(projectOne)
                .clickOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(new HomePage(getDriver()).getViewList().contains(LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = getRandomStr();

        String actualDescription = new HomePage(getDriver())
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .addDescription(descriptionRandom)
                .clickOkButton()
                .getTextDescription();

        Assert.assertEquals(actualDescription, descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {
        String descriptionText = new HomePage(getDriver())
                .clickView(LIST_VIEW_NAME)
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(descriptionText, "");
    }

    @Test(dependsOnMethods = "testEditViewDeleteDescription")
    public void testRemoveSomeHeadersFromProjectStatusTableInListView() {
        final List<String> namesRemoveColumns = List.of("Weather", "Last Failure", "Last Duration");

        int numberOfJobTableHeadersListView = new HomePage(getDriver())
                .clickView(LIST_VIEW_NAME)
                .clickEditListView()
                .removeSomeColumns(namesRemoveColumns)
                .clickApplyButton()
                .clickOkButton()
                .getJobTableHeadersSize();

        int numberOfJobTableHeadersAll = new ViewPage(getDriver())
                .getBreadcrumbs()
                .clickDashboard()
                .getJobTableHeadersSize();

        Assert.assertNotEquals(numberOfJobTableHeadersAll, numberOfJobTableHeadersListView);
    }

    @Test(dependsOnMethods = "testRemoveSomeHeadersFromProjectStatusTableInListView")
    public void testDeleteListView() {

        List<String> viewList = new HomePage(getDriver())
                .clickView(LIST_VIEW_NAME)
                .clickDeleteViewToHomePage()
                .clickYes()
                .getViewList();

        Assert.assertFalse(viewList.contains(LIST_VIEW_NAME));
    }
}