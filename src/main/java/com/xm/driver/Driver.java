package com.xm.driver;

import com.xm.config.ConfigFactory;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public final class Driver { //IS A relationship

    private Driver() {
    }

    public static void initDriver() {
        String browser = ConfigFactory.getConfig().browser();
        if (DriverManager.getDriver() == null) {
            WebDriver driver = DriverFactory.getDriver(browser);
            DriverManager.setDriver(driver);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(ConfigFactory.getConfig().timeout(), TimeUnit.SECONDS);//presence of element in the dom

        }
    }

    public static void quitDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.setDriver(null);
        }
    }
}
