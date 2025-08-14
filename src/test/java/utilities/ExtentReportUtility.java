package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtility {

    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        // ✅ Relative path (Jenkins compatible)
        String reportPath = System.getProperty("user.dir") + "/reports/extent-report.html";
        

        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Automation Test Report");
        reporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Ashish Bangar");
        extent.setSystemInfo("Environment", "QA");
    }

    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
