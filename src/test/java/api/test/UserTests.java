package api.test;

import api.endpoints.Routes;
import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UserTests {
    Faker faker ;
    User userPayload;

    public Logger logger;

    @BeforeClass
    public  void setupData()
    {
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUsername(faker.name().username());

        //logs
         logger = LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1)
    public void testPostUser()
    {
        logger.info("*************** CreatingUser *********");
      Response response = (Response) UserEndPoints.createUser(userPayload);
      response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("*************** User created *********");
    }

    @Test(priority = 2)
    public void getUser()
    {
        logger.info("*************** Reading User Info *********");
        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("***************  User Info Displayed *********");
    }

    @Test
    public void postUserjson()
    {
       Response response= (Response) given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(new File("C:\\Users\\Mahesh\\IdeaProjects\\RestAssuredFromScratch\\src\\test\\resources\\userdata"))
                .when()
                .post("https://petstore.swagger.io/v2/user");

       Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void validatecomplexjson1()
    {
      String response =   given()
                 .get("https://fakestoreapi.com/products")
                 .then()
                 .extract()
                 .response()
                 .asString();

        JsonPath jsonPath = new JsonPath(response);
         int res = Integer.parseInt(jsonPath.getString("rating[0].count"));
         Assert.assertEquals(res,120);
    }

    @Test
    public void validatecomplexjson2()
    {
        String response =   given()
                .get("https://fakestoreapi.com/carts")
                .then()
                .extract()
                .response()
                .asString();

        JsonPath jsonPath = new JsonPath(response);
       int  products = jsonPath.getInt("[0].products[0].productId");
       // List<Map<Integer,Integer>> arr = jsonPath.getJsonObject("products[0]");

                System.out.println("Product ID: " + products);


    }
}
