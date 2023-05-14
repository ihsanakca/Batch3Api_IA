package apiTest.day04;

import apiTemplates.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_01_POJO_Deserailization {
    /**
     //TASK
     //base url = https://gorest.co.in
     //end point = /public/v2/users
     //path parameter = {id} --> 1597739
     //send a get request with the above credentials
     //parse to Json object to pojo (custom java class)
     //verify that the body below
     /*
     {
     "id": 1597739,
     "name": "Gajbaahu Sharma",
     "email": "sharma_gajbaahu@hammes.test",
     "gender": "female",
     "status": "inactive"
     }
     */

    @Test
    public void test1(){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 1597739)
                .when().log().all()
                .get("https://gorest.co.in/public/v2/users/{id}");

        Assert.assertEquals(response.statusCode(),200);

        User user = response.as(User.class);

        int id=user.getId();
        int expectedId=1597739;

        Assert.assertEquals(id,expectedId);

        System.out.println("user.getName() = " + user.getName());

        System.out.println("user.getEmail() = " + user.getEmail());


    }
}
