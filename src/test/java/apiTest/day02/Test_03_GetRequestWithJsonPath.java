package apiTest.day02;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class Test_03_GetRequestWithJsonPath {
    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    /**
     * /**
     * <p>
     * *     TASK
     * *     Given accept type is json
     * *     When user sends a GET request to /allusers/alluser
     * *     Then the status Code should be 200
     * *     And Content type json should be "application/json; charset=UTF-8"
     * *
     */

    @Test
    public void testWithJsonPathMethod() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 5)
                .queryParam("page", 1)
                .when()
                .get("/allusers/alluser");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //json path ile devam edelim
        JsonPath jsonPath = response.jsonPath();

        //ilk elemanın id'sinin 1 olduğunu assert edelim
        assertEquals(jsonPath.getInt("id[0]"), 1);

        //beşinci (sonuncu) elemanın id'sinin 33 olduğunu assert edelim
        assertEquals(jsonPath.getInt("id[4]"), 33);
        assertEquals(jsonPath.getInt("id[-1]"), 33);

        //dördüncü elemanın adını assert edelim  adı-->"wilini3845@once"
        assertEquals(jsonPath.getString("name[3]"), "wilini3845@once");

        //bütün id'leri alalım ve assert edelim [1, 5, 24, 29, 33]
        List<Integer> expectedAllIDs = new ArrayList<>(Arrays.asList(1, 5, 24, 29, 33));

        List<Integer> allIds = jsonPath.getList("id");
        assertEquals(allIds, expectedAllIDs);
        System.out.println("allIds = " + allIds);
        System.out.println("----------------------");

        //2.yol (path metodu ile)
        System.out.println("response.path(\"id\") = " + response.path("id"));
        List<Integer>  listofIds= response.path("id");
        System.out.println("----------------------");


        //ilk elemanın skillerini alalım ve ikincisinin "Java" olduğunu assert edelim
        List<String> userOneSkills = jsonPath.getList("skills[0]");
        System.out.println("userOneSkills = " + userOneSkills);
        assertEquals(userOneSkills.get(1), "Java");
        System.out.println("----------------------");

        //2.yol
        assertEquals(jsonPath.getString("skills[0][1]"),"Java");

        //3.yol
        assertEquals(response.path("skills[0][1]"),"Java");

        //bütün elemanların bütün skillerini alalım (List of List )  //path metodu ile de olur
        List<List<String>>  allUsersAllSkills=jsonPath.getList("skills");
        System.out.println("allUsersAllSkills = " + allUsersAllSkills);
        System.out.println("----------------------");

        //ilk elemanın ilk eğitiminin school kaydının  "School or Bootcamp" olduğunu assert edelim (Hepsi path metod ile de olur)
        System.out.println("jsonPath.getString(\"education[0].school[0]\") = " + jsonPath.getString("education[0].school[0]"));
        System.out.println("jsonPath.get(\"education[0][0].school\") = " + jsonPath.get("education[0][0].school"));

        Map<String, Object> userOneFirstEducation = jsonPath.getMap("education[0][0]");
        System.out.println("userOneFirstEducation.get(\"school\") = " + userOneFirstEducation.get("school"));

        Map<String, Object> userOneFirstEducation_1=response.path("education[0][0]");
        System.out.println("userOneFirstEducation_1.get(\"school\") = " + userOneFirstEducation_1.get("school"));
    }
}
