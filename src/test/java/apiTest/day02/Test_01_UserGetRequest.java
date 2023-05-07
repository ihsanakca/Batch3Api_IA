package apiTest.day02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class Test_01_UserGetRequest {

    String bookStoreBaseURL = "https://bookstore.toolsqa.com/BookStore/v1";
    String kraftBaseURL = "https://www.krafttechexlab.com/sw/api/v1";

    @Test
    public void test1() {
        /** task
         *  Given accept type is JSON
         *  When user send GET request to /Books
         *  Then verify that response status code is 200
         *  and body is JSON format
         *  and response body contains "Richard E. Silverman"
         */
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(bookStoreBaseURL + "/Books");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        //body'i assert edelim

        assertTrue(response.body().asString().contains("Richard E. Silverman"));

    }

    @Test
    public void headersTest() {
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get(kraftBaseURL + "/allusers/getbyid/{id}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //response headers

        System.out.println("response.headers() = " + response.headers());

        assertEquals(response.header("Content-Length"), "721");
        assertEquals(response.header("Connection"), "Upgrade, Keep-Alive");

        assertTrue(response.headers().hasHeaderWithName("Date"));

    }

    @Test
    public void negativeTest() {
        /**
         * /*
         *
         *     Given accept type is json
         *     And path param id is 444
         *     When user sends a get request to "/allusers/getbyid/{id}
         *     Then status code is 404
         *     And content-type is "application/json; charset=UTF-8"
         *     And "No User Record Found..." message should be in response payload
         *
         *
         */

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 444)
                .when().log().all()
                .get(kraftBaseURL + "/allusers/getbyid/{id}");

        assertEquals(response.statusCode(), 404);
        assertEquals(response.contentType(), "application/json; charset=UTF-8");

        assertTrue(response.body().asString().contains("No User Record Found..."));


    }


}
