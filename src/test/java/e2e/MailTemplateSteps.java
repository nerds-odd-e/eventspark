package e2e;

import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

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
