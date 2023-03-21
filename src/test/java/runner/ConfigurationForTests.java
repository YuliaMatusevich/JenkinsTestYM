package runner;

import org.testng.*;

import java.util.*;


public class ConfigurationForTests implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

        for (IMethodInstance method : methods) {
            method.getMethod().setRetryAnalyzerClass(RetryFlakyTest.class);
        }

        return methods;
    }
}
