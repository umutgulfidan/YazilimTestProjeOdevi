import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://localhost:44316";
        useRelaxedHTTPSValidation();
        logRequestResponseDetails();

    }
    //Log detailed request response
    public static void logRequestResponseDetails(){
        RestAssured.config = RestAssured.config() .logConfig(LogConfig.logConfig() .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL) .enablePrettyPrinting(true));
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
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
                .contentType(ContentType.JSON)
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
