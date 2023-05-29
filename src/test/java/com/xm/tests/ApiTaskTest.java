package com.xm.tests;
import com.xm.driver.DriverManager;
import com.xm.pages.ApiValidationPages;
import com.xm.tests.baseTest.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ApiTaskTest extends BaseTest {
    @Test(description="Validate film with a title A New Hope",priority = 1)
    public void validateFilmTitle(){
        DriverManager.getDriver().close();
        ApiValidationPages avp=new ApiValidationPages();
        LinkedHashMap filmDetails=avp.findFilmTitleNewHope();
        String filmName= (String) filmDetails.get("filmNameDisplayed");
        Assertions.assertEquals(filmName,"A New Hope");
    }

    @Test(description="Validate person with name “Biggs Darklighter” among the characters that were part of that film.",priority = 2)
    public void validateCharacterName(){
        DriverManager.getDriver().close();
        ApiValidationPages avp=new ApiValidationPages();
        String filmLocation=avp.validateCharacterName();
        Assertions.assertEquals(filmLocation,ApiValidationPages.location);
    }
    @Test(description="Validate which starship he/she was flying on.",priority = 3)
    public void validateStarShipName(){
        DriverManager.getDriver().close();
        ApiValidationPages avp=new ApiValidationPages();
        LinkedList starShipName=avp.validateStarShipName() ;
        Assertions.assertEquals(starShipName.get(0),"X-wing");
    }

    @Test(description="Validate starship class is “Starfighter”",priority = 4)
    public void validateStarShipClass(){
        DriverManager.getDriver().close();
        ApiValidationPages avp=new ApiValidationPages();
        LinkedList starShipName=avp.validateStarShipName() ;
        Assertions.assertEquals(starShipName.get(1),"Starfighter");
    }
    @Test(description="Validate Luke Skywalker among the Pilots",priority = 5)
    public void validateLukeSkywalkerPilot(){
        DriverManager.getDriver().close();
        ApiValidationPages avp=new ApiValidationPages();
        avp.validateLukeSkyWalkerPilot();
        Assertions.assertEquals(ApiValidationPages.pilotName,"Luke Skywalker");
    }
}
