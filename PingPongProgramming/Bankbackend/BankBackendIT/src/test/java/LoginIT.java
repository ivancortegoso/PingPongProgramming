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
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "testusername");
        requestParams.put("password", "password");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("api/public/login");
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
    }
    @Test
    public void loginCorrectUsernameEmptyPassword() throws JSONException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "testusername");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("api/public/login");
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(500);
    }
}
