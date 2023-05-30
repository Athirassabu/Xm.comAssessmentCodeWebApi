package com.xm.utils;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    public static RequestSpecification buildRequestForGetCalls() {

        return given().baseUri("https://swapi.dev/api/").contentType(ContentType.JSON).when();
    }


}
