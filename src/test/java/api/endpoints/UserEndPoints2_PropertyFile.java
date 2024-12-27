package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class UserEndPoints2_PropertyFile {
    //to read end points from property file
    public static ResourceBundle getURL() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("routes");
        return resourceBundle;
    }

    public static Response createUser(User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(getURL().getString("post_url"));
        return response;
    }

    public static Response readUser(String usernames) {
        Response response = given()
                .pathParam("username", usernames)
                .when()
                .get(getURL().getString("get_url"));
        return response;
    }

    public static Response updateUser(String usernames, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", usernames)
                .body(payload)
                .when()
                .put(getURL().getString("update_url"));
        return response;
    }

    public static Response deleteUser(String userName) {
        Response response = given()
                .pathParam("username", userName)
                .when()
                .delete(getURL().getString("delete_url"));

        return response;
    }

}
