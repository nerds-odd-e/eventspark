package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PreviewSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;


    @When("^preview$")
    public void preview() throws Throwable {
        driver.findElement(By.id("preview")).click();
    }

    @Then("^show preview window")
    public void showPreviewWindow() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, containsString("/preview"));
    }

    @Then("^show home window")
    public void showHomeWindow() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, containsString("/home"));
    }

    @When("press back to home button")
    public void pressBackToHomeButton() {
        driver.findElement(By.id("back-to-home")).click();
    }
}
