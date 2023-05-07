package apiTest.day01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class SimpleGETTest {

    String petStoreBaseURL = "https://petstore.swagger.io/v2";

    @Test
    public void test1() {
        Response response = RestAssured.get(petStoreBaseURL + "/store/inventory");

        // print status code
        int statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);

        response.prettyPrint();
    }

    @Test
    public void test2() {
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .headers("Accept", ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory");

        assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");

    }

    @Test
    public void test3() {
        //restassured library kullanarak assert yapalım
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json");
    }

    @Test
    public void testContainsMethod() {
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(petStoreBaseURL + "/store/inventory");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        assertTrue(response.body().asString().contains("pending"));

        System.out.println("response.body().asString() = " + response.body().asString());
    }
}
