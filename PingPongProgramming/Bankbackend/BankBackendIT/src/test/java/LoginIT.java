import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class LoginIT {
    @Test
    public void loginCorrectUsernameCorrectPassword() throws JSONException {
        RestAssured.baseURI = "http://localhost:8080/";
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "ivanucci");
        requestParams.put("password", "password");
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("api/public/login");
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
    }
}
