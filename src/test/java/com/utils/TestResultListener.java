package com.utils;

import static com.cucumber.ScenarioContext.setContext;
import com.cucumber.Context;
import com.data.Values;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.io.PrintStream;

public class TestResultListener extends TestListenerAdapter {
    private static final String LOG_PATH = String.format("%1$s/log/log.log", Config.getInstance().getRootPath());
    private static final String ERROR_FILE_NAME = String.format("%1$s/log/error.log", Config.getInstance().getRootPath());
    private final DataUtils dataUtils = DataUtils.getInstance();
    private final PrintStream errorLog = dataUtils.createFile(ERROR_FILE_NAME);


    @Override
    public void onTestSuccess(ITestResult result) {
        setContext(Context.IS_EXCEPTION, Values.V_FALSE.getValue());
        setContext(Context.LOGS, dataUtils.printLog(LOG_PATH));

    }

    @Override
    public void onTestFailure(ITestResult result) {
        setContext(Context.IS_EXCEPTION, Values.V_TRUE.getValue());
        result.getThrowable().printStackTrace(new PrintStream(errorLog));
        setContext(Context.LOGS, dataUtils.printLog(ERROR_FILE_NAME));
    }
}
