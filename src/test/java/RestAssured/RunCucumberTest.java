package RestAssured;


import cucumber.api.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

public class RunCucumberTest {
    @RunWith(Cucumber.class)
    @CucumberOptions(plugin = {"pretty","html:target/cucumber-reports/cucumber.html",
            "json:target/cucumber-reports/cucumber.json"})
}
