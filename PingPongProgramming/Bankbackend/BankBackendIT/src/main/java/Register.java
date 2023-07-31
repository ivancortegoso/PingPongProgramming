import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.request.CreateUserRequestBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Register {
    public static ValidatableResponse registerCorrectParams() throws JsonProcessingException {
        ObjectWriter ow;
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        CreateUserRequestBuilder createUserRequestBuilder = new CreateUserRequestBuilder();
        createUserRequestBuilder.setFirstName("testname3");
        createUserRequestBuilder.setLastName("testlastname3");
        createUserRequestBuilder.setBirthDate("04/04/1998");
        createUserRequestBuilder.setPhoneNumber(123456789);
        createUserRequestBuilder.setAddress("testaddress3");
        createUserRequestBuilder.setDocumentId("12345678C");
        createUserRequestBuilder.setEmail("testuser3@solera.com");
        createUserRequestBuilder.setUsername("testusername3");
        createUserRequestBuilder.setPassword("password");
        CreateUserRequest userRequest = createUserRequestBuilder.createCreateUserRequest();
        String json = ow.writeValueAsString(userRequest);
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(json);
        Response response = request.post("api/public/register");
        return response.then();
    }

    public static String registerExistingEmail() throws JsonProcessingException {
        ObjectWriter ow;
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        CreateUserRequestBuilder createUserRequestBuilder = new CreateUserRequestBuilder();
        createUserRequestBuilder.setFirstName("testname4");
        createUserRequestBuilder.setLastName("testlastname4");
        createUserRequestBuilder.setBirthDate("1998-04-04");
        createUserRequestBuilder.setPhoneNumber(123456789);
        createUserRequestBuilder.setAddress("test address 4");
        createUserRequestBuilder.setDocumentId("12345678D");
        createUserRequestBuilder.setEmail("testuser1@solera.com");
        createUserRequestBuilder.setUsername("testusername4");
        createUserRequestBuilder.setPassword("password");
        CreateUserRequest userRequest = createUserRequestBuilder.createCreateUserRequest();
        String json = ow.writeValueAsString(userRequest);
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(json);
        Response response = request.post("api/public/register");
        String parsedJson = response.getBody().asString();
        return parsedJson;
    }
}
