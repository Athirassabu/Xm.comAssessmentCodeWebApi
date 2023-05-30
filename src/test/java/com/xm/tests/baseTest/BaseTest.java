package com.xm.tests.baseTest;

import com.xm.driver.Driver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
   @BeforeMethod(alwaysRun = true)
    public void setUp() {

        Driver.initDriver();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        Driver.quitDriver();
    }
}
