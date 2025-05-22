package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseCase;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report_" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter("reports/" + repName);

        sparkReporter.config().setDocumentTitle("Sauce Demo Automation Report");
        sparkReporter.config().setReportName("Sauce Demo Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "SauceDemo");
        extentReports.setSystemInfo("Module", "Admin");
        extentReports.setSystemInfo("Submodule", "Customers");
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");

        String browser = testContext.getCurrentXmlTest().getParameter("bowser");

        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includeGroups.isEmpty()) {
            extentReports.setSystemInfo("Groups", includeGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult result) {
        test = extentReports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " got successfully executed!");
    }

    public void onTestFailure(ITestResult result) {
        test = extentReports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getTestClass().getName());

        test.log(Status.FAIL, result.getName() + " got failed!");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseCase().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onTestSkipped(ITestResult result) {
        test = extentReports.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped!");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }


    public void onFinish(ITestContext context) {
        extentReports.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + repName;
        File extentReprt = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReprt.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}