import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

public class BankAccountIT {
    @BeforeTestClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }
    @Test
    public void createCorrectBankAccount() throws JSONException {
        ValidatableResponse validatableResponse = BankAccount.createBankAccount("testName", 330, "testusername1", "password");
        validatableResponse.statusCode(200);
    }
}
