package com.excelr.test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PetShopTest {
    private String baseUrl="https://petstore.swagger.io/v2/";

    @Test
    public void getPetByIdTest()
    {
        int petId=102;
        String resource="pet/"+petId;

        RestAssured
                .given()
                .when().get(baseUrl+resource)
                .then().statusCode(200);
    }

    @Test
    public void getPetDetailByIdTest()
    {
        int petId=901;
        String resource="pet/"+petId;

       String output= RestAssured
                .given()
                .when().get(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        Reporter.log(output);
        System.out.println(output);
        Assert.assertTrue(output.contains("Liger"));
    }

    @Test
    public void getPetByStatusTest()
    {
        String resource="pet/findByStatus";

      String actualResult=  RestAssured
                .given().queryParam("status","available").queryParam("status","sold")
                .when().get(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        System.out.println(actualResult);
    }

    @Test
    public void AddPetTest()
    {
        String resource="pet";

        String actualResult=  RestAssured
                .given().headers("Content-Type","application/json").body("{\n" +
                        "    \"id\": 901,\n" +
                        "    \"category\": {\n" +
                        "        \"id\": 0,\n" +
                        "        \"name\": \"string\"\n" +
                        "    },\n" +
                        "    \"name\": \"doggie-901\",\n" +
                        "    \"photoUrls\": [\n" +
                        "        \"string\"\n" +
                        "    ],\n" +
                        "    \"tags\": [\n" +
                        "        {\n" +
                        "            \"id\": 0,\n" +
                        "            \"name\": \"string\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"status\": \"available\"\n" +
                        "}")
                .when().post(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        System.out.println(actualResult);
    }

    @Test
    public void AddPetFromJsonTest() throws FileNotFoundException {

        FileInputStream file=new FileInputStream("test-data/data.json");
        JsonPath json=new JsonPath(file);

        String jsonBody=json.prettify();


        String resource="pet";

        String actualResult=  RestAssured
                .given().headers("Content-Type","application/json").body(jsonBody)
                .when().post(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        System.out.println(actualResult);
    }

    @Test
    public void UpdatePetFromJsonTest() throws FileNotFoundException {

        FileInputStream file=new FileInputStream("test-data/data.json");
        JsonPath json=new JsonPath(file);

        String jsonBody=json.prettify();


        String resource="pet";

        String actualResult=  RestAssured
                .given().headers("Content-Type","application/json").body(jsonBody)
                .when().put(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        System.out.println(actualResult);
    }

    @Test
    public void deletePetByIdTest()
    {
        int petId=102;
        String resource="pet/"+petId;

        String actualResult= RestAssured
                .given()  //.headers("api_key","")
                .when().delete(baseUrl+resource)
                .then().statusCode(200).extract().asString();

        System.out.println(actualResult);
    }

}
