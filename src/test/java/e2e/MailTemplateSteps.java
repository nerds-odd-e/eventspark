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
    public void click_load_button() {
        driver.findElement(By.id("load-template")).click();
    }

}
