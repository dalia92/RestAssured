package RestAssured;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration.ConfigurationException;
import org.hamcrest.MatcherAssert;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CandidateStepDefinition {

        Properties prop=new Properties();
        FileInputStream file;
        {
            try
            {
                file = new FileInputStream("./src/test/resources/config.properties");
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    RequestSpecification request;
    @Given("Login API is provided")
    public void login_api_is_provided() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        request = RestAssured.given();
    }

    Response response;
    @When("User call login API")
    public void user_call_login_api() throws IOException {
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "Admin");
        requestParams.put("password", "admin123");
        request.header("Content-Type", "application/json");
        System.out.print(request.body(requestParams.toString()));
        response = request.post("/opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Then("a token will be generated")
    public void a_token_will_be_generated() throws IOException {
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        System.out.print(response.asString());
        String token= response.jsonPath().getString("token");
        System.out.println(token);
        Utils.setEnvVariable(token);
    }

    @Given("Token API is provided")
    public void token_api_is_provided() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        request = RestAssured.given();
    }

    @When("User call token API")
    public void user_call_token_api() {
        Header authorizationHeader = new Header("Authorization", prop.getProperty("token"));
        request.header(authorizationHeader);
        response = request.get("/opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Then("Cookies will be generated")
    public void cookies_will_be_generated() throws ConfigurationException, IOException {
        CookieFilter filter = new CookieFilter();
        String url = "/opensource-demo.orangehrmlive.com/web/index.php/auth/login";
        String body = "userName=Admin&&password=admin123";
        Response response = RestAssured.given().filter(filter).body(body).post(url).andReturn();
        RestAssured.given().filter(filter).body(body).post(url);
    }

    @Given("Cookies API is provided")
    public void cookies_api_is_provided() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        request = RestAssured.given();
    }

    @When("User call Add Candidate API")
    public void user_call_add_candidate_api() {
        Header authorizationHeader = new Header("Authorization", prop.getProperty("token"));
        request.header(authorizationHeader);
        response = request.get("/opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Then("the candidate info will be added")
    public void the_candidate_info_will_be_added() {
        System.out.println("Response Body is =>  " + response.asString());
        String candidate=response.jsonPath().getString("id");
        Assert.assertEquals(statusCode, 200 ,"Correct status code returned");
        MatcherAssert.assertThat("Validate json schema" , response.getBody().asString);
    }

    @Given("Cookies API2 is provided")
    public void cookies_api2_is_provided() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl2");
        request = RestAssured.given();
    }

    @When("User call Delete Candidate API")
    public void user_call_delete_candidate_api() {
        Header authorizationHeader = new Header("Authorization", prop.getProperty("token2"));
        request.header(authorizationHeader);
        response = request.get("/opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Then("the candidate info will be deleted")
    public void the_candidate_info_will_be_deleted() {
        System.out.println("Response Body is =>  " + response.asString());
        String deleteCandidate=response.jsonPath().getString("id");
    }
}