import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

public class BankAccount {
    public static ValidatableResponse createBankAccount(String name, double balance, String username, String password) throws JSONException {
        String bearerToken = LogIn.GetJWT(username, password);
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", name);
        requestParams.put("balance", balance);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.header("Authorization", bearerToken);
        request.body(requestParams.toString());
        Response response = request.post("api/bankaccount");
        ValidatableResponse validatableResponse = response.then();
        return validatableResponse;
    }
}
