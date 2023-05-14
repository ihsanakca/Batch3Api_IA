package apiTest.day05;

import apiTemplates.User;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class Test_01_Gson {

    //json-->java   :  de-serialization

    //java-->json   :  serialization

    @Test
    public void jsonToJava_deSerialization() {
/**
 * /**
       {
       "id": 1549066,
      "name": "Leela Kakkar",
       "email": "kakkar_leela@anderson.test",
       "gender": "female",
       "status": "inactive"
       }
 *
 */
        //gson objesi oluşturalım
        Gson gson=new Gson();

        String jsonBody=" {\n" +
                "       \"id\": 1549066,\n" +
                "      \"name\": \"Leela Kakkar\",\n" +
                "       \"email\": \"kakkar_leela@anderson.test\",\n" +
                "       \"gender\": \"female\",\n" +
                "       \"status\": \"inactive\"\n" +
                "       }";

        //de-serialization map ile json->java

        Map <String,Object> mapBody = gson.fromJson(jsonBody, Map.class);

        System.out.println("mapBody.get(\"id\") = " + mapBody.get("id"));
        System.out.println("mapBody.get(\"name\") = " + mapBody.get("name"));

        //de-serialization with custom class (User)  json->java

        User user = gson.fromJson(jsonBody, User.class);
        System.out.println("user.getId() = " + user.getId());
        System.out.println("user.getName() = " + user.getName());

    }

    @Test
    public void javaToJson_SerializationWithMap(){
        /**
         * id:61
         * name:Hasan
         * email: aaa@aa.com
         */

        //gson objesi oluşturalım
        Gson gson=new Gson();

        Map<String ,Object>map=new HashMap<>();
        map.put("id",61);
        map.put("name","Hasan");
        map.put("email","aaa@aa.com");

        System.out.println("map = " + map);

        String json = gson.toJson(map);

        System.out.println("json = " + json);

    }

    @Test
    public void javaToJson_Serialization(){
        /**
         * id:61
         * name:Hasan Yaka
         * email: hYaka@gmail.com
         * gender:male
         * status:inactive
         */

        User user=new User();
        user.setId(61);
        user.setName("Hasan Yaka");
        user.setEmail("hYaka@gmail.com");
        user.setGender("male");
        user.setStatus("inactive");

        System.out.println("user = " + user);

        Gson gson=new Gson();
        String json = gson.toJson(user);

        System.out.println("json = " + json);

    }
    @Test
    public void javaToJson_Serialization_2(){
        /**
         * id:61
         * name:Hasan Yaka
         * email: hYaka@gmail.com
         * gender:male
         * status:inactive
         */

        User user=new User(61,"Hasan Yaka","hYaka@gmail.com","male","inactive");

        System.out.println("user = " + user);

        Gson gson=new Gson();
        String json = gson.toJson(user);

        System.out.println("json = " + json);

    }
}
