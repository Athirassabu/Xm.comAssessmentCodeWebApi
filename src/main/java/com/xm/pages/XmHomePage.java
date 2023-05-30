package com.xm.pages;

import com.xm.driver.DriverManager;
import com.xm.reports.ExtentLogger;
import com.xm.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.text.ParseException;
import java.util.LinkedList;

public class XmHomePage {
    private static final By BUTTON_ACCEPTALL = By.xpath("//button[text()='ACCEPT ALL']");
    private static final By TAB_RESEARCHEDUCATION = By.className("main_nav_research");
    private static final By LIST_SECTION = By.xpath("(//ul[@id='main-nav'])[1]//following::li[starts-with(@class,'main_nav')]");
    private static final By LIST_RESEARCH = By.className("menu-research");
    private static final By TAB_ECONOMICCALENDER = By.xpath("//a[text()[normalize-space() = 'Economic Calendar']]");
    private static final By LIST_LEARNINGCENTER = By.xpath("//li[@class='menu-tutorials' or @class='menu-forex-seminars' or @class='menu-forex-classes']");

    private static final By LIST_TOOLS = By.xpath("//li[@class='menu-forex-signals' or @class='menu-mql5' or @class='menu-forex-calculators']");
    private static final By BUTTON_TOGGLE = By.xpath("//button[@class='toggleLeftNav']");
    private static final By BUTTON_RESEARCHEDUCATION = By.xpath("//span[text()='Research & Education']");

    private static final By BUTTON_EconomicCalendar = By.xpath("//span[text()='Economic Calendar']");


    public XmHomePage getTitleOfHome() {

        ExtentLogger.pass("Title of home screen is:" + DriverManager.getDriver().getTitle());
        return this;
    }

    public XmHomePage clickResourceEducation() {
        SeleniumUtils.click(TAB_RESEARCHEDUCATION);
        ExtentLogger.pass("Research and education section is clicked");
        return this;
    }

    public XmEconomicCalendarPage clickResourceEducation800X600() throws ParseException {
        SeleniumUtils.click(BUTTON_TOGGLE);
        ExtentLogger.pass("Toogle menu button at the left is clicked");
        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 350)");
        SeleniumUtils.click(BUTTON_RESEARCHEDUCATION);
        ExtentLogger.pass("Research and education section is clicked");
        SeleniumUtils.click(BUTTON_EconomicCalendar);
        ExtentLogger.pass("Economic calendar is clicked");
        return new XmEconomicCalendarPage();
    }


    public XmHomePage validateSectionsInHome() {
        LinkedList list = new LinkedList<>();
        int size = DriverManager.getDriver().findElements(LIST_SECTION).size();
        for (int i = 1; i <= size; i++) {
            String sections = DriverManager.getDriver().findElement(By.xpath("(//ul[@id='main-nav'])[1]//following::li[starts-with(@class,'main_nav')][" + i + "]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Different sections in home screen is : " + list);
        return this;

    }


    public XmHomePage validateContentsInResearchSection() {
        LinkedList list = new LinkedList<>();
        int size = DriverManager.getDriver().findElements(LIST_RESEARCH).size();
        for (int i = 1; i <= size; i++) {
            String sections = DriverManager.getDriver().findElement(By.xpath("(//li[@class='menu-research'])[" + i + "]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Contents inside Research section is : " + list);
        return this;

    }

    public XmHomePage validateContentsInLearningCenterSection() {
        LinkedList list = new LinkedList<>();
        int size = DriverManager.getDriver().findElements(LIST_LEARNINGCENTER).size();
        for (int i = 1; i <= size; i++) {
            String sections = DriverManager.getDriver().findElement(By.xpath("(//li[@class='menu-tutorials' or @class='menu-forex-seminars' or @class='menu-forex-classes'])[" + i + "]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Contents inside Learning center is : " + list);
        return this;

    }

    public XmHomePage validateContentsInToolsSection() {
        LinkedList list = new LinkedList<>();
        int size = DriverManager.getDriver().findElements(LIST_TOOLS).size();
        for (int i = 1; i <= size; i++) {
            String sections = DriverManager.getDriver().findElement(By.xpath("(//li[@class='menu-forex-signals' or @class='menu-mql5' or @class='menu-forex-calculators'])[" + i + "]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Contents inside Tools section is : " + list);
        return this;

    }

    public XmHomePage completeResearchEducationSectionValidation() {
        clickResourceEducation().validateContentsInResearchSection().validateContentsInLearningCenterSection().validateContentsInToolsSection();
        return this;

    }

    public XmHomePage clickAcceptAll() {
        SeleniumUtils.click(BUTTON_ACCEPTALL);
        ExtentLogger.pass("Accept all is clicked successfully");
        return this;

    }

    public XmEconomicCalendarPage clickEconomicCalendar() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 250)");
        SeleniumUtils.click(TAB_ECONOMICCALENDER);
        ExtentLogger.pass("Economic calendar is clicked successfully");
        return new XmEconomicCalendarPage();

    }

}




