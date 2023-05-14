package apiTest.day05;

import apiTemplates.PostKraftNewUser;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Test_02_PostMethod {
    Faker faker = new Faker();

    @BeforeClass
    public void beforeClassSetUp() {
        baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    /**
     * //TASK
     * /*
     * baseUrl = https://www.krafttechexlab.com/sw/api/v1
     * endpoint = /allusers/register
     * Given accept type and Content type is JSON
     * And request json body is:
     * {
     * "name": "Melih Gezer",
     * "email": "mgezer@mgezer.com",
     * "password": "Mg12345678",
     * "about": "from Bursa",
     * "terms": "3"
     * }
     * When user sends POST request
     * Then status code 200
     * And content type should be application/json
     * And json payload/response/body should contain:
     * a new generated id that is special for user
     * verify name and email
     */

    @Test
    public void postWithString() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String about = faker.internet().domainWord();
        String terms = faker.number().digit();


        String jsonBody = " {\n" +
                "     \"name\": \"" + name + "\",\n" +
                "     \"email\": \"" + email + "\",\n" +
                "     \"password\": \"" + password + "\",\n" +
                "     \"about\": \"" + about + "\",\n" +
                "     \"terms\": \"" + terms + "\"\n" +
                "     }";

        Response response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonBody)   //serialization
                .when().log().all()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertNotNull(response.path("id"));
        Assert.assertEquals(response.path("name"), name);

        response.prettyPrint();

    }

    @Test
    public void postWithMap() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String about = faker.internet().domainWord();
        String terms = faker.number().digit();


        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        jsonBody.put("about", about);
        jsonBody.put("terms", terms);

        Response response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonBody)   //serialization
                .when().log().all()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertNotNull(response.path("id"));
        Assert.assertEquals(response.path("name"), name);
        Assert.assertEquals(response.path("email"), email);

        response.prettyPrint();

    }

    @Test
    public void postWithCustomClass() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String about = faker.internet().domainWord();
        String terms = faker.number().digit();

        PostKraftNewUser postKraftNewUser = new PostKraftNewUser();

        postKraftNewUser.setName(name);
        postKraftNewUser.setEmail(email);
        postKraftNewUser.setPassword(password);
        postKraftNewUser.setAbout(about);
        postKraftNewUser.setTerms(terms);


        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postKraftNewUser)  //serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.path("name"),name);


    }
    @Test
    public void postWithCustomClass_2() {
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String about = faker.internet().domainWord();
        String terms = faker.number().digit();

        PostKraftNewUser postKraftNewUser = new PostKraftNewUser(name,email,password,about,terms);

        Response response = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(postKraftNewUser)  //serialization
                .when()
                .post("/allusers/register");

        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.path("name"),name);

        response.prettyPrint();


    }
}