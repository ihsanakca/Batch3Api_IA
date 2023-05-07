package apiTest.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class SimpleGETTest {

    String petStoreBaseURL="https://petstore.swagger.io/v2";

    @Test
    public void test1(){
        Response response = RestAssured.get(petStoreBaseURL + "/store/inventory");

        // print status code
        int statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);

        response.prettyPrint();
    }
}
