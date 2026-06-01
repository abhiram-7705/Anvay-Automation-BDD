package com.cts.mfrp.Anvay.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.utils.ConfigReader;
import com.cts.mfrp.Anvay.utils.ScreenshotUtil;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        ConfigReader.load();
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(ConfigReader.get("report.path"));
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            testName = testName + " [" + params[0] + "]";
        }
        ExtentTest test = extent.createTest(testName);
        testNode.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testNode.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testNode.get().fail(result.getThrowable());
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                testNode.get().addScreenCaptureFromBase64String(
                        ScreenshotUtil.captureAsBase64(driver), "Failure Screenshot");
                ScreenshotUtil.capture(driver, result.getName());
            } catch (Exception e) {
                testNode.get().warning("Screenshot failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.wasRetried()) {
            return;
        }
        testNode.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}