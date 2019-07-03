package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.Assert.assertTrue;

public class MailTemplateSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @When("^click load button$")
    public void click_load_button() throws Throwable {
        driver.findElement(By.id("load-template")).click();
    }

    @Then("^subject becomes \"([^\"]*)\"$")
    public void check_subject(String subject) throws Throwable {
        String fieldValue = driver.findElement(By.id("subject")).getText();
        assertTrue(fieldValue.contains(subject));
    }

    @And("^body becomes \"([^\"]*)\"$")
    public void check_body(String body) throws Throwable {
        String fieldValue = driver.findElement(By.id("body")).getText();
        assertTrue(fieldValue.contains(body));
    }

}
