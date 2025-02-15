import controllers.UserController;
import io.restassured.response.Response;
import models.AddUserResponse;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static testdata.TestData.DEFAULT_USER;
import static testdata.TestData.INVALID_USER;

public class ApiTests {

    UserController userController = new UserController();

    static Stream<User> users() {
        return Stream.of(DEFAULT_USER, INVALID_USER);
    }

    @ParameterizedTest
    @MethodSource("users")
    void createUserParametrizedTest(User user) {
        Response response = userController.createUser(user);
        AddUserResponse createdUserResponse = response.as(AddUserResponse.class);

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(200, createdUserResponse.getCode());
        Assertions.assertEquals("unknown", createdUserResponse.getType());
        Assertions.assertFalse(createdUserResponse.getMessage().isEmpty());
    }

//    Этап 2

    //    @Test
//     void createUserControllerTest(){
//
//        Response response = userController.createDefaultUser();
//        AddUserResponse createdUserResponse = response.as(AddUserResponse.class);
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals(200, createdUserResponse.getCode());
//        Assertions.assertEquals("unknown", createdUserResponse.getType());
//        Assertions.assertFalse(createdUserResponse.getMessage().isEmpty());
//    }
//
//    @Test
//    void createInvalidUserControllerTest() {
//        Response response = userController.createUser(INVALID_USER);
//        AddUserResponse createdUserResponse = response.as(AddUserResponse.class);
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals(200, createdUserResponse.getCode());
//        Assertions.assertEquals("unknown", createdUserResponse.getType());
//        Assertions.assertEquals("0", createdUserResponse.getMessage());
//    }

//    Этап 1

    //    @Test
//    void createUser() {
//        String baseUrl = "https://petstore.swagger.io/v2/";
//        String body = """
//                  {
//                  "id": 0,
//                  "username": "string",
//                  "firstName": "string",
//                  "lastName": "string",
//                  "email": "string",
//                  "password": "string",
//                  "phone": "string",
//                  "userStatus": 0
//                }""";
//
//        Response response = given()
//                .baseUri(baseUrl)
//                .contentType(ContentType.JSON) //вместо .header("accept", "application/json").header("Content-Type", "application/json")
//                .body(body)
//                .when().post("user").andReturn();
//
//        response.body().prettyPrint();
//        int actualCode = response.getStatusCode();
//        String typeValue = response.jsonPath().getString("type");
//        String typeMessage = response.jsonPath().getString("message");
//
//
//        Assertions.assertEquals(200, actualCode);
//        Assertions.assertEquals("unknown", typeValue);
//        Assertions.assertNotNull(typeMessage, "Message should not be null");
//
//    }
//    @Test
//    void createUser2() {
//        String baseUrl = "https://petstore.swagger.io/v2/";
//        String body = """
//                  {
//                  "id": 0,
//                  "username": "string",
//                  "firstName": "string",
//                  "lastName": "string",
//                  "email": "string",
//                  "password": "string",
//                  "phone": "string",
//                  "userStatus": 0
//                }""";
//
//        given()
//                .baseUri(baseUrl)
//                .header("accept", "application/json")
//                .header("Content-Type", "application/json")
//                .body(body).
//                when()
//                .post("user")
//                .then()
//                .statusCode(200)
//                .body("code", equalTo(200))
//                .body("type", equalTo("unknown"))
//                .body("message", notNullValue(String.class));
//
//    }
}
