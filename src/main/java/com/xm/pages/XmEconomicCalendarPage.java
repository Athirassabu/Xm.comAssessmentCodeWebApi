package com.xm.pages;

import com.xm.config.ConfigFactory;
import com.xm.driver.DriverManager;
import com.xm.reports.ExtentLogger;
import com.xm.utils.SeleniumUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XmEconomicCalendarPage {
    private static final By TEXT_MONTHYEAR = By.xpath("//span[starts-with(@id,'mat-calendar-button')]");
    private static final By TEXT_TODAYDAY = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today mat-calendar-body-selected']");
    private static final By TEXT_TOMMOROWDATE = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected']");
    private static final By TEXT_CLICKNEXT = By.xpath("//button[@class='mat-focus-indicator mat-calendar-next-button mat-icon-button mat-button-base']");
    private static final By TEXT_NEXTWEEKFIRSTDAY = By.xpath("//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected']");
    private static final By LINK_HEREDISCLAIMER = By.xpath("//a[text()='here']");
    private static final By BUTTON_CALENDAR = By.xpath("//span[starts-with(@class,'tc-calendar-button')]");

    private static final By VISIBILITY_CALENDAR = By.xpath("//div[@class='ng-star-inserted' and text()='Recent & Next']");
    public static String todaysDate;

    public XmEconomicCalendarPage getTodayDate() {
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
        String todayDateGot = SeleniumUtils.getText(TEXT_MONTHYEAR);
        String todayDate = SeleniumUtils.getText(TEXT_TODAYDAY);
        if (todaysDate.equals(todayDateGot) && todayIs.equals(todayDate)) {
            ExtentLogger.pass("Year and month displayed in calendar is :" + todayDateGot + " Todays date displayed is : " + todayDate);

        }
        return this;

    }


    public XmEconomicCalendarPage getTomorrowDate() {

        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 40, 0).build().perform();
        DateFormat tommorowDate = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = cal.getTime();
        String tomorrowIs= tommorowDate.format(tomorrow);
        String tomorrowIss=  StringUtils.stripStart(tomorrowIs,"0");
        String tomorrowDayIst = SeleniumUtils.getText(TEXT_TOMMOROWDATE);
        if (tomorrowIss.equals(tomorrowDayIst)) {
            ExtentLogger.pass("Tomorrow Date displayed is : " + tomorrowDayIst);

        }
        return this;

    }


    public XmEconomicCalendarPage getNextWeek() throws ParseException {
        WebElement ele = DriverManager.getDriver().findElement(By.xpath("//div[@class='mat-slider-thumb']"));
        Actions actions = new Actions(DriverManager.getDriver());
        actions.dragAndDropBy(ele, 80, 0).build().perform();
        LocalDate today = LocalDate.now(ZoneId.of("Pacific/Auckland"));
        LocalDate nextOrSameMonday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        Stream<LocalDate> stream = nextOrSameMonday.datesUntil(nextOrSameMonday.plusWeeks(1));
        List<LocalDate> dates = stream.collect(Collectors.toList());
        String dayOfMonthMonday = String.valueOf(dates.get(0).getDayOfMonth());
        String dayOfMonthSunday = String.valueOf(dates.get(6).getDayOfMonth());
        String sundayMonth = String.valueOf(dates.get(6)).substring(5, 7);
        String mondayMonth = String.valueOf(dates.get(0)).substring(5, 7);
        String mondayYear = String.valueOf(dates.get(6).getYear());
        String sundayYear = String.valueOf(dates.get(6).getYear());
        String sundayYearMonth = sundayYear + "-" + sundayMonth;
        String mondayYearMonth = mondayYear + "-" + mondayMonth;
        String todayDateGot = todaysDate;
        if (todayDateGot.equals(mondayYearMonth) && !todayDateGot.equals(sundayYearMonth)) {
            String nextWeekFirstDay = SeleniumUtils.getText(TEXT_NEXTWEEKFIRSTDAY);
            if (dayOfMonthMonday.equals(nextWeekFirstDay)) {
                ExtentLogger.pass("Fist day of next week is validated successfully as :" + nextWeekFirstDay);
            }
            SeleniumUtils.click(TEXT_CLICKNEXT);
            String nextWeekLastDay = SeleniumUtils.getText(TEXT_NEXTWEEKFIRSTDAY);
            if (dayOfMonthSunday.equals(nextWeekLastDay)) {
                ExtentLogger.pass("Last day of next week is validated successfully as :" + nextWeekFirstDay);
            }
        }
        LinkedList list = new LinkedList<>();
        if (!todayDateGot.equals(sundayYearMonth) && !todayDateGot.equals(mondayYearMonth)) {
            int size = DriverManager.getDriver().findElements(TEXT_NEXTWEEKFIRSTDAY).size();
            for (int i = 1; i <= size; i++) {

                String datesDisplayed = DriverManager.getDriver().findElement(By.xpath("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected'])[" + i + "]")).getText();
                list.add(datesDisplayed);

            }
            String monday = (String) list.get(0);
            String sunday = (String) list.get(1);
            if (dayOfMonthMonday.equals(monday) && dayOfMonthSunday.equals(sunday)) {
                ExtentLogger.pass("Fist day of next week is :" + monday + " Last day of next week Sunday is : " + sunday);
            }

        }
        if (todayDateGot.equals(sundayYearMonth) && todayDateGot.equals(mondayYearMonth)) {
            int size = DriverManager.getDriver().findElements(TEXT_NEXTWEEKFIRSTDAY).size();
            System.out.println("Size is displayed as :" + size);
            for (int i = 1; i <= size; i++) {

                String datesDisplayed = DriverManager.getDriver().findElement(By.xpath("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected'])[" + i + "]")).getText();
                list.add(datesDisplayed);

            }
            String monday = (String) list.get(0);
            String sunday = (String) list.get(1);
            if (dayOfMonthMonday.equals(monday) && dayOfMonthSunday.equals(sunday)) {
                ExtentLogger.pass("Fist day of next week is :" + monday + " Last day of next week Sunday is : " + sunday);
            }

        }

        return this;
    }


    public XmEconomicCalendarPage getNextMonth() throws ParseException {
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
        int size = DriverManager.getDriver().findElements(TEXT_TOMMOROWDATE).size();
        System.out.println("Size is :" + size);
        LinkedList list = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            String dates = DriverManager.getDriver().findElement(By.xpath("(//div[@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-selected'])[" + i + "]")).getText();
            list.add(dates);
        }
        if (list.get(0).equals(nextMonthFirstDayShown) && list.get(1).equals(nextMonthLastDayShown)) {
            ExtentLogger.pass("Next month is displayed correctly in the calendar");
            ExtentLogger.pass("Next month first day is :" + list.get(0));
            ExtentLogger.pass("Next month last day is :" + list.get(1));
        }

        return this;

    }

    public XmEconomicCalendarPage clickCalendarButton() {

        WebElement frame = DriverManager.getDriver().findElement(By.id("iFrameResizer0"));
        DriverManager.getDriver().switchTo().frame(frame);
        SeleniumUtils.click(BUTTON_CALENDAR);
        DriverManager.getDriver().switchTo().defaultContent();
        return this;
    }

    public RiskWarningPages clickHereDisclaimer() {
        DriverManager.getDriver().switchTo().defaultContent();
        WebElement element = DriverManager.getDriver().findElement(LINK_HEREDISCLAIMER);
        JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
        executor.executeScript("arguments[0].click();", element);
        ExtentLogger.pass("Click here in Disclaimer is clicked successfully");
        return new RiskWarningPages();

    }
}
