package com.xm.utils;

import com.xm.config.ConfigFactory;
import com.xm.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {


    public static void click(By element)
    {
        waitUntilElementPresent(element);
        DriverManager.getDriver().findElement(element).click();
    }

    public static String getText(By element)
    {
        waitUntilElementPresent(element);
        String text=DriverManager.getDriver().findElement(element).getText();
        return text;
    }
    public static void sendKeys(By element,String value)
    {
        waitUntilElementPresent(element);
        DriverManager.getDriver().findElement(element).sendKeys(value);
    }

    public static void waitUntilElementPresent(By element)
    {
        WebDriverWait wait=new WebDriverWait(DriverManager.getDriver(), ConfigFactory.getConfig().timeout());
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }
}
