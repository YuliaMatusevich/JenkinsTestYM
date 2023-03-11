package runner;

import org.testng.annotations.DataProvider;

import static runner.TestUtils.getRandomStr;

public class TestDataUtils {

    public static final String EMAIL = getRandomStr(5) + "@gmail.com";
    public static final String PASSWORD = getRandomStr(7);

    public static final String USER_NAME = getRandomStr();
    public static final String USER_FULL_NAME = getRandomStr();
    public static final String NEW_USER_FULL_NAME = getRandomStr();

    public static final String FREESTYLE_PROJECT_NAME = getRandomStr();
    public static final String FREESTYLE_PROJECT_NAME2 = getRandomStr();
    public static final String FREESTYLE_PROJECT_RENAME = getRandomStr();

    public static final String MULTIBRANCH_PIPELINE_NAME = getRandomStr();
    public static final String MULTIBRANCH_PIPELINE_RENAME = getRandomStr();

    public static final String MULTI_CONFIGURATION_PROJECT_NAME = getRandomStr();
    public static final String MULTI_CONFIGURATION_PROJECT_RENAME = getRandomStr();

    public static final String ORGANIZATION_FOLDER_NAME = getRandomStr();
    public static final String ORGANIZATION_FOLDER_RENAME = getRandomStr();

    public static final String PIPELINE_NAME = getRandomStr();
    public static final String PIPELINE_NAME_2 = getRandomStr();

    public static final String PIPELINE_RENAME = getRandomStr() + "renamed";

    public static final String GLOBAL_VIEW_NAME = getRandomStr();
    public static final String LIST_VIEW_NAME = getRandomStr();

    public static final String MY_VIEW_NAME = getRandomStr();
    public static final String VIEW_RENAME = getRandomStr();

    public static final String PROJECT_NAME = getRandomStr();
    public static final String NEW_PROJECT_NAME = getRandomStr();

    public static final String FOLDER_NAME = getRandomStr();
    public static final String FOLDER_NAME_2 = getRandomStr();

    public static final String DISPLAY_NAME = getRandomStr();
    public static final String ITEM_NAME = getRandomStr();

    public static final String DESCRIPTION = getRandomStr();
    public static final String NEW_DESCRIPTION = "New description";

    public static final String GITHUB_REPOSITORY_URL = "https://github.com/RedRoverSchool/JenkinsQA_05-test.git";

    public static final String BRANCH_SPECIFIER = "*/main";

    public static final String MAVEN_NAME = getRandomStr();

    @DataProvider(name = "specialCharacters")
    public static Object[][] specialCharactersList() {
        return new Object[][]{{'&', "&amp;"}, {'>', "&gt;"}, {'<', "&lt;"}, {'!', "!"}, {'@', "@"}, {'#', "#"},
                {'$', "$"}, {'%', "%"}, {'^', "^"}, {'*', "*"}, {'[', "["}, {']', "]"}, {'\\', "\\"}, {'|', "|"},
                {';', ";"}, {':', ":"}, {'/', "/"}, {'?', "?"}};
    }
}
