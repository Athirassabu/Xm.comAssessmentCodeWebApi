package com.xm.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReport {
    private ExtentReport(){}
    public static ExtentReports extent;
    public static ExtentTest extentTest;



    public static void initReport()
    {
        extent= new ExtentReports();
        ExtentSparkReporter spark= new ExtentSparkReporter(System.getProperty("user.dir")+"/index.html");
        extent.attachReporter(spark);
    }

    public static void flushReport()
    {
     extent.flush();
    }

    public static void createTest(String testCaseName)
    {
        extentTest=extent.createTest(testCaseName);
        ExtentManager.setExtentTest(extentTest);
    }

}
