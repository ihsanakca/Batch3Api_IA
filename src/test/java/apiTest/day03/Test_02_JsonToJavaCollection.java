package apiTest.day03;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Test_02_JsonToJavaCollection {
    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void allUserToMap() {
        /**
         * /**
         *          *
         *          *          given accept type is json
         *          *          And query param pagesize is 30
         *          *          And query param page is 1
         *                     When user sends a get request to /allusers/alluser
         *          *          Then status code should be 200
         */
        Response response = given().accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .queryParam("page", 1)
                .when()
                .get("/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);

        //json body'i java collection'a çevirmeye de-serialize denir. tersi de olur serialize denir.

        List<Map<String, Object>> allUserMap = response.body().as(List.class);

        //10.userın adını assert edelim "Selim Gezer"
        String actualName = (String) allUserMap.get(9).get("name");
        String expectedName = "Selim Gezer";
        Assert.assertEquals(actualName, expectedName);

        //10.userın skillerinden ikincisinin Selenium olduğunu verify edelim
        List<String> skillsOfTenth = (List<String>) allUserMap.get(9).get("skills");
        String actualSkill = skillsOfTenth.get(1);
        Assert.assertEquals(actualSkill, "Selenium");
        System.out.println("actualSkill = " + actualSkill);

        //pekiiii DİREKT alabilir miyim?
        String actualSkill2 = (String) ((List<?>) allUserMap.get(9).get("skills")).get(1);
        System.out.println("actualSkill2 = " + actualSkill2);

        //10.userın educationının 3.sünün school adının Ankara University olduğunu verify edelim
        List<Map<String, Object>> educationMapOfTenthUser = (List<Map<String, Object>>) allUserMap.get(9).get("education");
        String actualSchool = (String) educationMapOfTenthUser.get(2).get("school");
        Assert.assertEquals(actualSchool, "Ankara University");
        System.out.println("actualSchool = " + actualSchool);

        Map<String, Object> educationMapOfTenthUserOfThirdEducationRecord = (Map<String, Object>) ((List<?>) allUserMap.get(9).get("education")).get(2);
        System.out.println("educationMapOfTenthUserOfThirdEducationRecord.get(\"school\") = " + educationMapOfTenthUserOfThirdEducationRecord.get("school"));


    }

    @Test
    public void testBookStoreJsonToJava() {
        /**
         * given accep type json
         * request url:https://demoqa.com/Account/v1/User/11
         * then status code 401
         * de-serialize --> json to java collection
         * verify that message is "User not authorized!"
         * verify that code is 1200
         */

        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 11)
                .when()
                .get("https://demoqa.com/Account/v1/User/{id}");

        Assert.assertEquals(response.statusCode(), 401);

        Map<String, Object> jsonMap = response.as(Map.class);
        System.out.println("jsonMap = " + jsonMap);

        //verify the message
        String actualMessage = (String) jsonMap.get("message");
        Assert.assertEquals(actualMessage, "User not authorized!");

        //verify the code
        String code = (String) jsonMap.get("code");
        Assert.assertEquals(code, "1200");

    }
}