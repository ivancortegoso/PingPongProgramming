import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

public class LogIn {
    public static Response login(String username, String password) throws JSONException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        requestParams.put("password", password);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("api/public/login");
        return response;
    }
    public static ValidatableResponse loginEmptyPassword(String username) throws JSONException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", username);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestParams.toString());
        Response response = request.post("api/public/login");
        ValidatableResponse validatableResponse = response.then();
        return validatableResponse;
    }
    public static String GetJWT(String username, String password) throws JSONException {
        Response response = LogIn.login(username, password);
        JSONObject authorization = new JSONObject(response.body().asString());
        String bearerToken = "Bearer " + authorization.get("token");
        return bearerToken;
    }
}
