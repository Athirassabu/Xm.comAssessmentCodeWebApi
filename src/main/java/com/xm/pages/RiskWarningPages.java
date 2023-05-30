package com.xm.pages;

import com.xm.driver.DriverManager;
import com.xm.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class RiskWarningPages {
    private static final By LINK_RISKWARNIING = By.xpath("//a[text()='here' and starts-with(@href,'https://cloud.xm-cdn.com/static/pdf/System-PDFs/XMGlobal-Risk')]");


    public PdfDocumentPage clickHereRiskWarning() {
        WebElement element = DriverManager.getDriver().findElement(LINK_RISKWARNIING);
        JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);
        ExtentLogger.pass("Click here in Risk warning screen is clicked successfully");
        return new PdfDocumentPage();

    }


}
