package apiTest.day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class Test_01_HamcrestMatcher {
    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void requestParamsWithMap() {
/**
 * /*
 *
 *     TASK
 *     Given accept type json
 *     And query  parameter value name Thomas Eduson
 *     And query  parameter value skills Cypress
 *     And query  parameter value pagesize 50
 *     And query  parameter value page 1
 *     When user sends GET request to /allusers/alluser
 *     Then response status code should be 200
 *     And response content-type application/json; charset=UTF-8
 *      */

        Map<String, Object> mapBody = new HashMap<>();
        mapBody.put("pagesize", 50);
        mapBody.put("page", 1);
        mapBody.put("name", "Thomas Eduson");
        mapBody.put("skills", "Cypress");

        Response response = given().accept(ContentType.JSON)
                .queryParams(mapBody)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

    }

    @Test
    public void getOneUser() {
/**   with hamcrestMatcher
 *         given accept type is json
 *         And path param id is 62
 *         When user sends a get request to /allusers/getbyid/{id}
 *         Then status code should be 200
 *         And content type should be "application/json; charset=UTF-8"
 */

        RestAssured.given().accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8");
    }

    @Test
    public void getOneUserWithHamcrestMatcher() {
        /**
         *         given accept type is json
         *         And path param id is 62
         *         When user sends a get request to /allusers/getbyid/{id}
         *         Then status code should be 200
         *         And content type should be "application/json; charset=UTF-8"
         *         user's id should be "62"
         *         user's name should be "Selim Gezer"
         *         user's job should be "QA Automation Engineer"
         *         User's second skill should be "Selenium"
         *         User's third education school name should be "Ankara University"
         *         User's email should be "sgezer@gmail.com"
         *         User's company should be "KraftTech"
         *         The response header Content-Length should be 776
         *         Response headers should have "Date" header
         *
         */
        RestAssured.given().accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=UTF-8")
                .and()
                .body("[0].id", Matchers.equalTo(62),
                        "[0].name", equalTo("Selim Gezer"),
                        "[0].job", equalTo("QA Automation Engineer"),
                        "job[0]", equalTo("QA Automation Engineer"),
                        "[0]['job']", equalTo("QA Automation Engineer"),
                        "[0].skills[1]", equalTo("Selenium"),
                        "skills[0][1]", equalTo("Selenium"),
                        "[0].education[2].school", equalTo("Ankara University"),
                        "education[0][2].school", equalTo("Ankara University"),
                        "education[0].school[2]", equalTo("Ankara University"),
                        "[0].email", equalTo("sgezer@gmail.com"),
                        "[0][\"email\"]", equalTo("sgezer@gmail.com"),
                        "[0]['email']", equalTo("sgezer@gmail.com"),
                        "company[0]", equalTo("KraftTech")
                )
                .and()
                .header("Content-Length", equalTo("776"))
                .headers("Content-Length", "776")
                .header("Content-Length", "776")
                .headers("Date", notNullValue())
                .header("Date", notNullValue());
    }

    @Test
    public void testWithLogs() {
        RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when().log().all()  //request bilgilerini verir
                .get("/allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .log().all();  //response bilgilerini verir
    }

    @Test
    public void getAllUserWithHamcrestMatcher() {
        /**
         *
         *          given accept type is json
         *          And query param pagesize is 30
         *          And query param page is 1
         *          And take the request logs
         *          When user sends a get request to /allusers/alluser
         *          Then status code should be 200
         *          And content type should be application/json; charset=UTF-8
         *          And response header content-length should be 5946
         *          And response header Connection should be Upgrade, Keep-Alive
         *          And response headers has Date
         *          And json data should have "Selim Gezer","Jhon Nash","zafer" for name
         *          And json data should have "QA" for job
         *          And json data should have "İTÜ" for the tenth user's education school
         *          And json data should have "Junior Developer" for the first user's third experience job
         *          And json data should have "Google" for the last user's first experience company
         *          Only take the response headers log.
         *
         */

        List<String> list=new ArrayList<>(Arrays.asList("School or Bootcamp", "School", "School", "School"));

        RestAssured.given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .and()
                .queryParam("page", 1)
                .when().log().all()
                .get("/allusers/alluser")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .header("Content-Length", equalTo("5946"))
                .headers("Content-Length", "5946")
                .headers("Connection", equalTo("Upgrade, Keep-Alive"),  //body gibi beraber assert edilebilir...
                        "Date", notNullValue())
                .body("name", hasItems("Selim Gezer", "Jhon Nash", "zafer"),
                        "job", hasItem("QA"),
                        "education.school",hasItem(list),
                        "education[9].school",hasItem("İTÜ"),
                        "[9].education.school",hasItem("İTÜ"),
                        "[0].experience[2].job",equalTo("Junior Developer"),
                        "experience[0].job[2]",equalTo("Junior Developer"),
                        "experience[0][2].job",equalTo("Junior Developer"),
                        "[0].experience.job[2]",equalTo("Junior Developer"),
                        "experience[-1].company[0]",equalTo("Google"),
                        "experience[29][0].company",equalTo("Google"))      //-1 sonuncu, -2 sondan ikinci vd... kaydı getirir..
                .and()
                .log().headers();  //sadece response headerlarını loglar

    }
}
