import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @Test
    void createUser() {
        String baseUrl = "https://petstore.swagger.io/v2/";
        String body = """
                  {
                  "id": 0,
                  "username": "string",
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 0
                }""";

        Response response = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON) //вместо .header("accept", "application/json").header("Content-Type", "application/json")
                .body(body)
                .when().post("user").andReturn();

        response.body().prettyPrint();
        int actualCode = response.getStatusCode();
        String typeValue = response.jsonPath().getString("type");
        String typeMessage = response.jsonPath().getString("message");


        Assertions.assertEquals(200, actualCode);
        Assertions.assertEquals("unknown", typeValue);
        Assertions.assertNotNull(typeMessage, "Message should not be null");

    }
    @Test
    void createUser2() {
        String baseUrl = "https://petstore.swagger.io/v2/";
        String body = """
                  {
                  "id": 0,
                  "username": "string",
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 0
                }""";

        given()
                .baseUri(baseUrl)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(body).
                when()
                .post("user")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", notNullValue(String.class));

    }
}
