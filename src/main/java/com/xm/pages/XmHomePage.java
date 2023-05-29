package com.xm.pages;

import com.xm.driver.DriverManager;
import com.xm.reports.ExtentLogger;
import com.xm.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmHomePage {
    private static final By BUTTON_ACCEPTALL = By.xpath("//button[text()='ACCEPT ALL']");
    private static final By TAB_RESEARCHEDUCATION = By.className("main_nav_research");
    private static final By LIST_SECTION = By.xpath("(//ul[@id='main-nav'])[1]//following::li[starts-with(@class,'main_nav')]");
    private static final By LIST_RESEARCH = By.className("menu-research");
    private static final By TAB_ECONOMICCALENDER = By.xpath("//a[text()[normalize-space() = 'Economic Calendar']]");
    private static final By LIST_LEARNINGCENTER = By.xpath("//li[@class='menu-tutorials' or @class='menu-forex-seminars' or @class='menu-forex-classes']");

    private static final By LIST_TOOLS = By.xpath("//li[@class='menu-forex-signals' or @class='menu-mql5' or @class='menu-forex-calculators']");
    private static final By TEXT_MONTHYEAR = By.xpath("//span[starts-with(@id,'mat-calendar-button')]");
    private static final By TEXT_TODAYDAY = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today mat-calendar-body-selected']");
    private static final By TEXT_TOMMOROWDATE = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected']");
    private static final By TEXT_CLICKNEXT = By.xpath("//button[@class='mat-focus-indicator mat-calendar-next-button mat-icon-button mat-button-base']");
    private static final By TEXT_NEXTWEEKFIRSTDAY = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected']");
    private static final By LINK_HEREDISCLAIMER = By.xpath("//a[text()='here']");
    private static final By LINK_RISKWARNIING = By.xpath("//a[text()='here' and starts-with(@href,'https://cloud.xm-cdn.com/static/pdf/System-PDFs/XMGlobal-Risk')]");

    private static final By BUTTON_TOGGLE = By.xpath("//button[@class='toggleLeftNav']");
    private static final By BUTTON_RESEARCHEDUCATION = By.xpath("//span[text()='Research & Education']");

    private static final By BUTTON_EconomicCalendar = By.xpath("//span[text()='Economic Calendar']");

    private static final By BUTTON_CALENDAR = By.xpath("//span[starts-with(@class,'tc-calendar-button')]");
    public static String todaysDate;


    public XmHomePage getTitleOfHome() {

        ExtentLogger.pass("Title of home screen is:" + DriverManager.getDriver().getTitle());
        return this;
    }

    public XmHomePage clickResourceEducation() {
        SeleniumUtils.click(TAB_RESEARCHEDUCATION);
        ExtentLogger.pass("Research and education section is clicked");
        return this;
    }

    public XmHomePage clickResourceEducation800X600() throws ParseException {
        SeleniumUtils.click(BUTTON_TOGGLE);
        ExtentLogger.pass("Toogle menu button at the left is clicked");
        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 350)");
        SeleniumUtils.click(BUTTON_RESEARCHEDUCATION);
        ExtentLogger.pass("Research and education section is clicked");
        SeleniumUtils.click(BUTTON_EconomicCalendar);
        ExtentLogger.pass("Economic calendar is clicked");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement frame = DriverManager.getDriver().findElement(By.id("iFrameResizer0"));
        DriverManager.getDriver().switchTo().frame(frame);
       /* WebElement element = DriverManager.getDriver().findElement(BUTTON_CALENDAR);
        JavascriptExecutor executor = (JavascriptExecutor)DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);*/
        SeleniumUtils.click(BUTTON_CALENDAR);
        DriverManager.getDriver().switchTo().defaultContent();
        getTodayDate();
        getTomorrowDate();
        getNextWeek();
        clickHereDisclaimer();
        clickHereRiskWarning();
        switchToNextWindow();
        return this;
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

    public XmHomePage clickEconomicCalendar() {
        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 250)");
        // jse.executeScript("arguments[0].scrollIntoView()", TAB_ECONOMICCALENDER);
        SeleniumUtils.click(TAB_ECONOMICCALENDER);
        ExtentLogger.pass("Economic calendar is clicked successfully");
        return this;

    }

    public XmHomePage getTodayDate() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 300)");
        WebElement frame = DriverManager.getDriver().findElement(By.id("iFrameResizer0"));
        DriverManager.getDriver().switchTo().frame(frame);
        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 50, 0).build().perform();
        DateFormat monthYear = new SimpleDateFormat("yyyy-MM");
        DateFormat todayDay = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        todaysDate = monthYear.format(date);
        String todayIs = todayDay.format(date);
        System.out.println("Todays date is: " + todaysDate);
        String todayDateGot = SeleniumUtils.getText(TEXT_MONTHYEAR);
        String todayDate = SeleniumUtils.getText(TEXT_TODAYDAY);
        if (todaysDate.equals(todayDateGot) && todayIs.equals(todayDate)) {
            ExtentLogger.pass("Year and month displayed in calendar is :" + todayDateGot + " Todays date displayed is : " + todayDate);

        }
        return this;

    }

    public XmHomePage getTodayDate800X600() {

        JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
        jse.executeScript("scroll(0, 300)");
        WebElement frame = DriverManager.getDriver().findElement(By.id("iFrameResizer0"));
        DriverManager.getDriver().switchTo().frame(frame);
        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 50, 0).build().perform();
        DateFormat monthYear = new SimpleDateFormat("yyyy-MM");
        DateFormat todayDay = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        todaysDate = monthYear.format(date);
        String todayIs = todayDay.format(date);
        System.out.println("Todays date is: " + todaysDate);
        String todayDateGot = SeleniumUtils.getText(TEXT_MONTHYEAR);
        String todayDate = SeleniumUtils.getText(TEXT_TODAYDAY);
        if (todaysDate.equals(todayDateGot) && todayIs.equals(todayDate)) {
            ExtentLogger.pass("Year and month displayed in calendar is :" + todayDateGot + " Todays date displayed is : " + todayDate);

        }
        return this;

    }


    public XmHomePage getTomorrowDate() {

        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 40, 0).build().perform();
        DateFormat tommorowDate = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = cal.getTime();
        String tomorrowIs = tommorowDate.format(tomorrow);
        System.out.println("Tommorow date is : " + tomorrowIs);
        String tomorrowDayIst = SeleniumUtils.getText(TEXT_TOMMOROWDATE);
        System.out.println("Displayed tommo date is: " + tomorrowDayIst);
        if (tomorrowIs.equals(tomorrowDayIst)) {
            ExtentLogger.pass("Tomorrow Date displayed is : " + tomorrowDayIst);

        }
        return this;

    }


    public XmHomePage getNextWeek() throws ParseException {
        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 80, 0).build().perform();
        LocalDate today = LocalDate.now(ZoneId.of("Pacific/Auckland"));
        LocalDate nextOrSameMonday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        Stream<LocalDate> stream = nextOrSameMonday.datesUntil(nextOrSameMonday.plusWeeks(1));
        List<LocalDate> dates = stream.collect(Collectors.toList());
        System.out.println(dates.get(0).getDayOfMonth());
        String dayOfMonthMonday = String.valueOf(dates.get(0).getDayOfMonth());
        String dayOfMonthSunday = String.valueOf(dates.get(6).getDayOfMonth());
        String sundayMonth = String.valueOf(dates.get(6)).substring(5, 7);
        String mondayMonth = String.valueOf(dates.get(0)).substring(5, 7);
        String mondayYear = String.valueOf(dates.get(6).getYear());
        String sundayYear = String.valueOf(dates.get(6).getYear());
        String sundayYearMonth = sundayYear + "-" + sundayMonth;
        String mondayYearMonth = mondayYear + "-" + mondayMonth;
        System.out.println("Sunday Year and Month is: " + sundayYearMonth);
        String todayDateGot = SeleniumUtils.getText(TEXT_MONTHYEAR);
        if (todayDateGot.equals(mondayYearMonth)) {
            String nextWeekFirstDay = SeleniumUtils.getText(TEXT_NEXTWEEKFIRSTDAY);
            if (dayOfMonthMonday.equals(nextWeekFirstDay))
                ExtentLogger.pass("Fist day of next week is validated successfully as :" + nextWeekFirstDay);
        }
        LinkedList list = new LinkedList<>();
        if (!todayDateGot.equals(sundayYearMonth) || !todayDateGot.equals(mondayYearMonth)) {
            SeleniumUtils.click(TEXT_CLICKNEXT);
            int size = DriverManager.getDriver().findElements(TEXT_NEXTWEEKFIRSTDAY).size();
            if (size == 1) {
                String nextWeekLastDay = SeleniumUtils.getText(TEXT_NEXTWEEKFIRSTDAY);
                System.out.println("Sunday last day of week is:" + dayOfMonthSunday);
                if (dayOfMonthSunday.equals(nextWeekLastDay)) {
                    ExtentLogger.pass("Last day of the week is validated and displayed as :" + nextWeekLastDay);
                }
            }
            if (size > 1) {

                for (int i = 1; i <= size; i++) {

                    String datesDisplayed = DriverManager.getDriver().findElement(By.xpath("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected'])[" + i + "]")).getText();
                    list.add(datesDisplayed);

                }
                String monday = (String) list.get(0);
                String sunday = (String) list.get(1);
                if (dayOfMonthMonday.equals(monday) && dayOfMonthSunday.equals(sunday)) {
                    ExtentLogger.pass("Fist day of next week is :" + monday + " Last day of week Sunday is : " + sunday);
                }

            }

            ExtentLogger.pass("Next week date is available in next month hence clicked next in calender");
        }

        return this;

    }


    public XmHomePage getNextMonth() throws ParseException {
        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 120, 0).build().perform();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date nextMonthLastDay = calendar.getTime();
        String nextMonthFirstDayShown = nextMonthFirstDay.toString().substring(9, 10);
        String nextMonthLastDayShown = nextMonthLastDay.toString().substring(8, 10);
        System.out.println("nextMonthFirstDayShown" + nextMonthFirstDayShown);
        System.out.println("nextMonthLastDayShown" + nextMonthLastDayShown);
        int size = DriverManager.getDriver().findElements(TEXT_TOMMOROWDATE).size();
        System.out.println("Size is :" + size);
        LinkedList list = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            String dates = DriverManager.getDriver().findElement(By.xpath("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected'])[" + i + "]")).getText();
            System.out.println("Date is :" + dates);
            list.add(dates);
        }
        System.out.println("List is :" + list);
        if (list.get(0).equals(nextMonthFirstDayShown) && list.get(1).equals(nextMonthLastDayShown)) {
            ExtentLogger.pass("Next month is displayed correctly in the calendar");
            ExtentLogger.pass("Next month first day is :" + list.get(0));
            ExtentLogger.pass("Next month last day is :" + list.get(1));
        }

        return this;

    }

    public XmHomePage clickHereDisclaimer(){
       DriverManager.getDriver().switchTo().defaultContent();
        WebElement element = DriverManager.getDriver().findElement(LINK_HEREDISCLAIMER);
        JavascriptExecutor executor = (JavascriptExecutor)DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);
        ExtentLogger.pass("Click here in Disclaimer is clicked successfully");
        return this;

    }

    public XmHomePage clickHereRiskWarning(){
        WebElement element = DriverManager.getDriver().findElement(LINK_RISKWARNIING);
        JavascriptExecutor executor = (JavascriptExecutor)DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);
        ExtentLogger.pass("Click here in Risk warning screen is clicked successfully");
        return this;

    }

    public XmHomePage switchToNextWindow(){

        for(String winHandle : DriverManager.getDriver().getWindowHandles()){
            DriverManager.getDriver().switchTo().window(winHandle);
        }
        ExtentLogger.pass("Risk disclosure document is opened in new tab");
        return this;
    }
    public XmHomePage validateContentsInLearningCenterSection(){
        LinkedList list=new LinkedList<>();
        int size=DriverManager.getDriver().findElements(LIST_LEARNINGCENTER).size();
        for(int i=1;i<=size;i++)
        {
            String sections= DriverManager.getDriver().findElement(By.xpath("(//li[@class='menu-tutorials' or @class='menu-forex-seminars' or @class='menu-forex-classes'])["+i+"]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Contents inside Learning center is : "+list);
        return this;

    }

    public XmHomePage validateContentsInToolsSection(){
        LinkedList list=new LinkedList<>();
        int size=DriverManager.getDriver().findElements(LIST_TOOLS).size();
        for(int i=1;i<=size;i++)
        {
            String sections= DriverManager.getDriver().findElement(By.xpath("(//li[@class='menu-forex-signals' or @class='menu-mql5' or @class='menu-forex-calculators'])["+i+"]")).getText();
            list.add(sections);
        }
        ExtentLogger.pass("Contents inside Tools section is : "+list);
        return this;

    }
    public XmHomePage completeResearchEducationSectionValidation(){
        clickResourceEducation().validateContentsInResearchSection().validateContentsInLearningCenterSection().validateContentsInToolsSection();
        return this;

    }

    public XmHomePage completeResearchEducationSectionValidationG(){
        clickResourceEducation().validateContentsInResearchSection().validateContentsInLearningCenterSection().validateContentsInToolsSection();
        return this;

    }

    public XmHomePage clickAcceptAll(){
        SeleniumUtils.click(BUTTON_ACCEPTALL);
        ExtentLogger.pass("Accept all is clicked successfully");
        return this;

    }

}




