package apiTest.day04;

import apiTemplates.Education;
import apiTemplates.Experience;
import apiTemplates.KraftUserExample;
import groovy.lang.DelegatesTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class Test_02_POJO_Deserialization_Kraft {
    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }
    /**
     * TASK
     * base url = https://www.krafttechexlab.com/sw/api/v1
     * end point /allusers/getbyid/{id}
     * id parameter value is 62
     * send the GET request
     * then status code should be 200
     * get all data into a custom class (POJO) by de-serilization
     */

    @Test
    public void test1(){
        Response response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(),200);
       // response.prettyPrint();

        KraftUserExample[] userInfo = response.as(KraftUserExample[].class);

        int length = userInfo.length;
        System.out.println("length = " + length);

        //id'yi alalım
        System.out.println("userInfo[0].getId() = " + userInfo[0].getId());

        //name alalım
        System.out.println("userInfo[0].getName() = " + userInfo[0].getName());

        //skillsin ilkini alalım
        List<String> skills = userInfo[0].getSkills();
        System.out.println("skills.get(0) = " + skills.get(0));
        System.out.println("userInfo[0].getSkills().get(0) = " + userInfo[0].getSkills().get(0));

        //ilk education bilgisinin school bilgisini alalım
        List<Education> education = userInfo[0].getEducation();
        System.out.println("education.get(0).getSchool() = " + education.get(0).getSchool());
        System.out.println("userInfo[0].getEducation().get(0).getSchool() = " + userInfo[0].getEducation().get(0).getSchool());

        //ikinci experience bilgisinin company bilgisini alalım
        List<Experience> experience = userInfo[0].getExperience();
        System.out.println("experience.get(1).getCompany() = " + experience.get(1).getCompany());
        System.out.println("userInfo[0].getExperience().get(1).getCompany() = " + userInfo[0].getExperience().get(1).getCompany());
    }
}
