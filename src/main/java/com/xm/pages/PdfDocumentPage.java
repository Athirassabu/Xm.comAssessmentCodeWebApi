package com.xm.pages;

import com.xm.driver.DriverManager;
import com.xm.reports.ExtentLogger;

public class PdfDocumentPage {

    public PdfDocumentPage switchToNextWindow() {

        for (String winHandle : DriverManager.getDriver().getWindowHandles()) {
            DriverManager.getDriver().switchTo().window(winHandle);
        }
        ExtentLogger.pass("Risk disclosure document is opened in new tab");
        return this;
    }
}
