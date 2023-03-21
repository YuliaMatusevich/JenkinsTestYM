package runner;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFlakyTest implements IRetryAnalyzer {
    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            int maxReply = 2;
            if (count < maxReply) {
                count++;
                return true;
            }
        }
        return false;
    }
}
