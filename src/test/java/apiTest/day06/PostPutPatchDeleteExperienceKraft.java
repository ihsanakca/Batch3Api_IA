package apiTest.day06;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostPutPatchDeleteExperienceKraft {

    String email = "sgezer@gmail.com";
    String password = "sg12345678";
    int experienceId;

    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test (priority = 0)
    public void addNewExperienceWithPost() {
        /**
         * HTTP method (GET, POST, PATCH vb)
         * Base URL
         * EndPoint
         * Headers (Gerekiyorsa)
         * Parameters Path or Query (Gerekiyorsa)
         * Body (POST,PUT,PATCH metodları için zorunludur)
         * Token-Authorization (gerekiyorsa)
         */

        String requestBody = "{\n" +
                "  \"job\": \"Junior Developer\",\n" +
                "  \"company\": \"Kraft Techex\",\n" +
                "  \"location\": \"USA\",\n" +
                "  \"fromdate\": \"1990-01-01\",\n" +
                "  \"todate\": \"1999-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .body(requestBody)
                .headers(Authorization.getTokenWithMap(email, password))
                .when()
                .post("/experience/add")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        experienceId = response.path("id");
        System.out.println("experienceId = " + experienceId);
    }

    @Test (priority = 1)
    public void updateWithPutMethod() {
        String requestBody = "{\n" +
                "  \"job\": \"Simyaci\",\n" +
                "  \"company\": \"Kraft Techex\",\n" +
                "  \"location\": \"USA\",\n" +
                "  \"fromdate\": \"1990-01-01\",\n" +
                "  \"todate\": \"1999-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .body(requestBody)
                .headers(Authorization.getTokenWithMap(email, password))
                .queryParam("id", experienceId)
                .when()
                .put("/experience/updateput")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 2)
    public void updateWithPatchMethod() {
        String requestBody = "{\n" +
                "  \"job\": \"Biyolog\"\n" +
                "}";


        Response response = given().accept(ContentType.JSON)
                .body(requestBody)
                .headers(Authorization.getTokenWithMap(email, password))
                .pathParam("id", experienceId)
                .when()
                .patch("/experience/updatepatch/{id}")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 3)
    public void updateWithPatchMethod_2() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("job", "saadas");
        requestBody.put("company", "kalskdms");
        requestBody.put("location", "Turkey");

        //experienceId = 1065

        Response response = given().accept(ContentType.JSON)
                .and()
                .body(requestBody)
                .and()
                .headers(Authorization.getTokenWithMap(email, password))
                .and()
                .pathParam("id", experienceId)
                .when()
                .patch("/experience/updatepatch/{id}")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test (priority = 4)
    public void deleteExperience() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", experienceId)
                .headers(Authorization.getTokenWithMap(email, password))
                .when()
                .delete("/experience/delete/{id}")
                .prettyPeek();

        Assert.assertEquals(response.statusCode(), 200);
    }
}
