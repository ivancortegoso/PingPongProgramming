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
    static ObjectWriter ow;
    @BeforeAll
    public static void setup() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        RestAssured.baseURI = "http://localhost:8080/";
    }
    @Test
    public void registerCorrectParams() throws JSONException, JsonProcessingException {
        CreateUserRequestBuilder createUserRequestBuilder = new CreateUserRequestBuilder();
        createUserRequestBuilder.setFirstName("testname12");
        createUserRequestBuilder.setLastName("testlastname12");
        createUserRequestBuilder.setBirthDate("04/04/1998");
        createUserRequestBuilder.setPhoneNumber(123456789);
        createUserRequestBuilder.setAddress("testaddress");
        createUserRequestBuilder.setDocumentId("987654321A12");
        createUserRequestBuilder.setEmail("testname12@testdomain.com");
        createUserRequestBuilder.setUsername("testusername12");
        createUserRequestBuilder.setPassword("password");
        CreateUserRequest userRequest = createUserRequestBuilder.createCreateUserRequest();
        String json = ow.writeValueAsString(userRequest);
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(json);
        Response response = request.post("api/public/register");
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(201);
    }
    @Test
    public void registerExistsEmail() throws JSONException, JsonProcessingException {
        CreateUserRequestBuilder createUserRequestBuilder = new CreateUserRequestBuilder();
        createUserRequestBuilder.setFirstName("testname");
        createUserRequestBuilder.setLastName("testlastname");
        createUserRequestBuilder.setBirthDate("04/04/1998");
        createUserRequestBuilder.setPhoneNumber(123456789);
        createUserRequestBuilder.setAddress("testaddress");
        createUserRequestBuilder.setDocumentId("987654321A");
        createUserRequestBuilder.setEmail("testname12@testdomain.com");
        createUserRequestBuilder.setUsername("testusername");
        createUserRequestBuilder.setPassword("password");
        CreateUserRequest userRequest = createUserRequestBuilder.createCreateUserRequest();
        String json = ow.writeValueAsString(userRequest);
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(json);
        Response response = request.post("api/public/register");
        ValidatableResponse validatableResponse = response.then();
        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
        Assert.isTrue(parsedJson.toString().contains("Email already exists!"));
    }
}
