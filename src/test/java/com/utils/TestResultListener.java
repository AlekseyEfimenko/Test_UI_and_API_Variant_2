package com.utils;

import static com.cucumber.ScenarioContext.setContext;
import aquality.selenium.core.logging.Logger;
import com.cucumber.Context;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.IInvokedMethodListener;
import java.io.PrintStream;

public class TestResultListener extends TestListenerAdapter implements IInvokedMethodListener {
    private static final String LOG_PATH = String.format("%1$s/log/log.log", Config.getInstance().getRootPath());
    private static final String ERROR_FILE_NAME = String.format("%1$s/log/error.log", Config.getInstance().getRootPath());
    private final DataUtils dataUtils = DataUtils.getInstance();
    private final PrintStream errorLog = dataUtils.createFile(ERROR_FILE_NAME);
    private final Logger logger = Logger.getInstance();

    @Override
    public void onTestSuccess(ITestResult result) {
        setContext(Context.IS_EXCEPTION, "false");
        setContext(Context.LOGS, dataUtils.printLog(LOG_PATH));
        logger.info("Test successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error(String.format("Test failed with message: %1$s", result.getThrowable().getMessage()));
        setContext(Context.IS_EXCEPTION, "true");
        result.getThrowable().printStackTrace(new PrintStream(errorLog));
        setContext(Context.LOGS, dataUtils.printLog(ERROR_FILE_NAME));
    }
}
