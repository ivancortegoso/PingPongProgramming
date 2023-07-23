import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.request.CreateUserRequestBuilder;
import com.solera.bankbackend.service.UserService;
import io.jsonwebtoken.lang.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static io.restassured.RestAssured.given;

public class RegisterIT {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }
    @Test
    public void registerCorrectParams() throws JSONException, JsonProcessingException {
        Register.registerCorrectParams().statusCode(201);
    }
    @Test
    public void registerExistsEmail() throws JSONException, JsonProcessingException {
        Assert.isTrue(Register.registerExistingEmail().contains("Email already exists!"));
    }
}
