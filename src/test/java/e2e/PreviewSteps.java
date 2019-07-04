package e2e;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class PreviewSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;


    @When("^preview$")
    public void preview() {
        driver.findElement(By.id("preview")).click();
    }

    @Then("^show preview (\\d+)")
    public void showPreviewWindow(int index) {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, containsString("/preview/" + index));
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

    @When("click next button")
    public void clickNextButton() {
        driver.findElement(By.id("next")).click();
    }

    @When("click previous button")
    public void clickPreviousButton() {
        driver.findElement(By.id("prev")).click();
    }

    @Then("previous button is disabled")
    public void previousButtonIsDisabled() {
        assertFalse(driver.findElement(By.id("prev")).isEnabled());
    }

    @Then("next button is disabled")
    public void nextButtonIsDisabled() {
        assertFalse(driver.findElement(By.id("next")).isEnabled());
    }

    @Then("previewed address is \"([^\"]*)\"$")
    public void previewedAddressIs(String address) {
        String previewedAddress = driver.findElement(By.id("address-preview")).getText();
        assertThat(previewedAddress, is(address));
    }


    @Then("^variables are replaced with \"([^\"]*)\" in body$")
    public void variablesAreReplacedInBody(String name) {
        String body = driver.findElement(By.id("body-preview")).getText();
        assertThat(body, is("Hello " + name));
    }

    @Then("^variables are replaced with \"([^\"]*)\" in subject$")
    public void variablesAreReplacedInSubject(String name) {
        String subject = driver.findElement(By.id("subject-preview")).getText();
        assertThat(subject, is("Hi " + name));
    }

}
