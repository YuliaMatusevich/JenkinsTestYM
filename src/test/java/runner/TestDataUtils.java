package runner;

import org.testng.annotations.DataProvider;

public class TestDataUtils {

    @DataProvider(name = "specialCharacters")
    public Object[][] specialCharactersList() {
        return new Object[][]{{'&', "&amp;"}, {'>', "&gt;"}, {'<', "&lt;"}, {'!', "!"}, {'@', "@"}, {'#', "#"},
                {'$', "$"}, {'%', "%"}, {'^', "^"}, {'*', "*"}, {'[', "["}, {']', "]"}, {'\\', "\\"}, {'|', "|"},
                {';', ";"}, {':', ":"}, {'/', "/"}, {'?', "?"},};
    }
}
