package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommonSteps {

    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @Given("user visits our homepage")
    public void userVisitsSendPage() {
        driver.get("http://localhost:" + port);
    }

    @And("address is filled with \"([^\"]*)\"")
    public void addressIsFilledWith(String address) {
        String actual = driver.findElement(By.id("address")).getAttribute("value");
        assertThat(actual, is(address));
    }

    @And("subject is filled with \"([^\"]*)\"")
    public void subjectIsFilledWith(String subject) {
        String actual = driver.findElement(By.id("subject")).getAttribute("value");
        assertThat(actual, is(subject));
    }

    @And("body is filled with \"([^\"]*)\"")
    public void bodyIsFilledWith(String body) {
        String actual = driver.findElement(By.id("body")).getAttribute("value");
        assertThat(actual, is(body));
    }
}
