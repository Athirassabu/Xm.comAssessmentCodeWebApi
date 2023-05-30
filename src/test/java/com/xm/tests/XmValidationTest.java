package com.xm.tests;


import com.xm.config.ConfigFactory;
import com.xm.driver.DriverManager;
import com.xm.pages.XmHomePage;
import com.xm.tests.baseTest.BaseTest;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import java.text.ParseException;


public class XmValidationTest extends BaseTest {


    @Test(description = "Validate the Use case in Maximum resolution", priority = 1, groups = {"web"})
    public void validationMaximumResolution() throws ParseException {
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigFactory.getConfig().url());
        XmHomePage xmh = new XmHomePage();
        xmh.clickAcceptAll().getTitleOfHome().validateSectionsInHome().completeResearchEducationSectionValidation().clickEconomicCalendar().getTodayDate().getTomorrowDate().getNextWeek().getNextMonth().clickHereDisclaimer().clickHereRiskWarning().switchToNextWindow();


    }

    @Test(description = "Validate the Use case in Resolution 1024 X 768", priority = 2, groups = {"web", "sanity"})
    public void validateResolution_1023x768() throws ParseException {
        DriverManager.getDriver().manage().window().setSize(new Dimension(1024, 768));
        DriverManager.getDriver().get(ConfigFactory.getConfig().url());
        XmHomePage xmh = new XmHomePage();
        xmh.clickAcceptAll().getTitleOfHome().validateSectionsInHome().completeResearchEducationSectionValidation().clickEconomicCalendar().getTodayDate().getTomorrowDate().getNextWeek().clickHereDisclaimer().clickHereRiskWarning().switchToNextWindow();

    }

    @Test(description = "Validate the use case in Resolution 800 X 600", priority = 3, groups = {"web", "sanity"})
    public void validateResolution_800x600() throws ParseException {
        DriverManager.getDriver().manage().window().setSize(new Dimension(800, 600));
        DriverManager.getDriver().get(ConfigFactory.getConfig().url());
        XmHomePage xmh = new XmHomePage();
        xmh.clickAcceptAll().getTitleOfHome().clickResourceEducation800X600().clickCalendarButton().getTodayDate().getTomorrowDate().getNextWeek().clickHereDisclaimer().clickHereRiskWarning().switchToNextWindow();
        ;

    }
}


