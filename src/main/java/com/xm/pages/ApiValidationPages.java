package com.xm.pages;

import com.google.common.base.CharMatcher;
import com.xm.reports.ExtentLogger;
import com.xm.utils.ApiUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ApiValidationPages {

    public static String filmLocationGot;
    public static String location = "";
    public static String pilotName = "";
    public static LinkedHashMap filmNameMap;

    public static LinkedHashMap findFilmTitleNewHope() {
        Response response = ApiUtils.buildRequestForGetCalls().get("films/").then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        ExtentLogger.pass("Get films Api is hit successfully with status code as : " + response.statusCode());
        filmNameMap = new LinkedHashMap<>();
        String count = response.jsonPath().getString("count");
        int size = Integer.parseInt(count);
        ExtentLogger.pass("Number of films available in the api call is : " + size);
        for (int i = 0; i < size; i++) {
            String filmName = response.jsonPath().getString("results[" + i + "].title");
            if (filmName.equals("A New Hope")) {
                location = String.valueOf(i + 1);
                filmNameMap.put("position", location);
                filmNameMap.put("filmNameDisplayed", filmName);
                ExtentLogger.pass("Film with title as A New Hope is available in location : " + location);
            }

        }
        return filmNameMap;
    }

    public static String validateCharacterName() {
        Response response = ApiUtils.buildRequestForGetCalls().get("people/").then()
                .extract().response();
        ExtentLogger.pass("Get people Api is hit successfully with status code as : " + response.statusCode());
        JsonPath people = new JsonPath(response.asString());
        int sizeCharacter = people.getInt("results.size()");
        for (int i = 0; i < sizeCharacter; i++) {
            String characterName = response.jsonPath().getString("results[" + i + "].name");
            if (characterName.equals("Biggs Darklighter")) {
                String filmUrl = response.jsonPath().getString("results[" + i + "].films[0]");
                filmLocationGot = CharMatcher.digit().retainFrom(filmUrl);
                if (filmLocationGot.equals("1")) {
                    ExtentLogger.pass("Biggs Darklighter is among the character of the film a New Hope");
                }
            }

        }
        return filmLocationGot;
    }


    public LinkedList validateStarShipName() {
        Response response = ApiUtils.buildRequestForGetCalls().get("people/").then()
                .extract().response();
        ExtentLogger.pass("Get people Api is hit successfully with status code as : " + response.statusCode());
        JsonPath people = new JsonPath(response.asString());
        int sizeCharacter = people.getInt("results.size()");

        LinkedList list = new LinkedList<>();
        for (int i = 0; i < sizeCharacter; i++) {
            String characterName = response.jsonPath().getString("results[" + i + "].name");
            if (characterName.equals("Biggs Darklighter")) {
                String starShipUrl = response.jsonPath().getString("results[" + i + "].starships[0]");
                String starShipNumber = CharMatcher.digit().retainFrom(starShipUrl);
                ExtentLogger.pass("StarShip number is :" + starShipNumber);

                Response starShip = ApiUtils.buildRequestForGetCalls().get("starships/" + starShipNumber + "/").then()
                        .extract().response();
                ExtentLogger.pass("Star ship Api is hit successfully ");
                String starShipName = starShip.jsonPath().getString("name");
                String starShipClass = starShip.jsonPath().getString("starship_class");
                list.add(starShipName);
                list.add(starShipClass);
                ExtentLogger.pass("Star ship name is displayed as :" + starShipName);
            }
        }
        return list;
    }


    public LinkedList validateLukeSkyWalkerPilot() {


        Response response = ApiUtils.buildRequestForGetCalls().get("people/").then()
                .extract().response();
        //  ExtentLogger.pass("Get people Api is hit successfully with status code as : " + response.statusCode());
        JsonPath people = new JsonPath(response.asString());
        int sizeCharacter = people.getInt("results.size()");

        LinkedList list = new LinkedList<>();
        LinkedList pilots = new LinkedList<>();
        for (int i = 0; i < sizeCharacter; i++) {
            String characterName = response.jsonPath().getString("results[" + i + "].name");
            if (characterName.equals("Biggs Darklighter")) {
                String starShipUrl = response.jsonPath().getString("results[" + i + "].starships[0]");
                String starShipNumber = CharMatcher.digit().retainFrom(starShipUrl);
                //ExtentLogger.pass("StarShip number is :" + starShipNumber);

                Response starShip = ApiUtils.buildRequestForGetCalls().get("starships/" + starShipNumber + "/").then()
                        .extract().response();
                //   ExtentLogger.pass("Star ship Api is hit successfully ");
                String starShipName = starShip.jsonPath().getString("name");
                String starShipClass = starShip.jsonPath().getString("starship_class");
                list.add(starShipName);
                list.add(starShipClass);
                JsonPath starShipPilot = new JsonPath(starShip.asString());
                int sizePilot = starShipPilot.getInt("pilots.size()");
                pilots = new LinkedList<>();
                for (int j = 0; j < sizePilot; j++) {
                    String pilot = starShip.jsonPath().getString("pilots[" + j + "]");
                    String pilotLocation = CharMatcher.digit().retainFrom(pilot);
                    pilots.add(pilotLocation);
                }
                ExtentLogger.pass("List of pilots number in starship " + starShipNumber + " is :" + pilots);
            }
        }

        for (int i = 0; i < pilots.size(); i++) {
            Response pilotApi = ApiUtils.buildRequestForGetCalls().get("people/" + pilots.get(i) + "/").then()
                    .extract().response();
            String pilotNameDisplayed = pilotApi.jsonPath().getString("name");
            if (pilotNameDisplayed.equals("Luke Skywalker")) {
                pilotName = pilotNameDisplayed;
                ExtentLogger.pass("Pilot name displayed is :" + pilotNameDisplayed + " pilot location is :" + pilots.get(i));
                break;
            }
        }
        return pilots;
    }

}

