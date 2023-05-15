package apiTest.day06;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Authorization {

    static String email="sgezer@gmail.com";
    static String password="sg12345678";

    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void loginAndGetToken(){

        Response response = given()
                .accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password",password)
                .when().log().all()
                .post("/allusers/login");

        response.prettyPrint();

        String token = response.path("token");

        System.out.println("token = " + token);

    }

    public static String getToken(){
        Response response = given()
                .accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password",password)
                .when().log().all()
                .post("/allusers/login");

        return response.path("token");
    }

    public static String getToken(String email,String password){
        Response response = given()
                .accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password",password)
                .when().log().all()
                .post("/allusers/login");

        return response.path("token");
    }
    public static Map<String,Object> getTokenWithMap(String email, String password){
        Response response = given()
                .accept(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password",password)
                .when().log().all()
                .post("/allusers/login");
        Map<String ,Object> map=new HashMap<>();
        map.put("token",response.path("token"));

        return map;
    }
}
