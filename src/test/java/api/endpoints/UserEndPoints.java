package api.endpoints;

import api.payload.User;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class UserEndPoints {
   public static Response createUser(User payload){
        Response response =  given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);
        return response;
    }

    public static Response readUser(String usernames){
        Response response =  given()
                .pathParam("username",usernames)
                .when()
                .get(Routes.get_url);
        return response;
    }
    public static Response updateUser(String usernames, User payload){
        Response response =  given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username",usernames)
                .body(payload)
                .when()
                .put(Routes.update_url);
        return response;
    }

    public static Response deleteUser(String userName)
    {
        Response response=given()
                .pathParam("username",userName)
                .when()
                .delete(Routes.delete_url);

        return response;
    }

}
