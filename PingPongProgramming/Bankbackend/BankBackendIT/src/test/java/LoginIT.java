import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

public class LoginIT {
    @BeforeTestClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    public void loginCorrectUsernameCorrectPassword() throws JSONException {
        ValidatableResponse validatableResponse = LogIn.login("testusername1", "password").then();
        validatableResponse.statusCode(200);
    }
    @Test
    public void loginCorrectUsernameEmptyPassword() throws JSONException {
        LogIn.loginEmptyPassword("testusername1").statusCode(500);
    }
}