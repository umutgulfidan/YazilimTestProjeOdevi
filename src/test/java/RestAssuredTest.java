import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://localhost:44316";
        useRelaxedHTTPSValidation();
    }

    @Test
    public void GetIlansByQueryTest() {


        given()
                .queryParam("PageSize", 12)
                .queryParam("PageNumber", 1)
                .queryParam("IsDescending", false)
                .when()
                .get("/api/Ilan/getilansbyquery")
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("message", notNullValue())
                .time(lessThan(1000L));



    }

    @Test
    public void Login(){
        given()
                .contentType("application/json")
                .body("{ \"nationalityId\": \"11111111111\", \"password\": \"123456aA.\" }")
                .when()
                .post("/api/Auth/login")
                .then()
                .statusCode(200)
                .body("isSuccess", equalTo(true))
                .body("message", notNullValue())
                .body("data", notNullValue())
                .time(lessThan(1000L));
    }

}
