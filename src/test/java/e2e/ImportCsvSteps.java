package e2e;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class ImportCsvSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @Given("select {string} CSV")
    public void select_CSV(String string) {
        String url = System.getenv("HOME") + "/workspace/eventspark/src/test/resources/" + string;
        driver.findElement(By.id("csvfile")).sendKeys(url);
    }

    @When("click import button")
    public void click_import_button() {
        driver.findElement(By.id("import")).click();
    }

    @Then("move to contact list")
    public void move_to_contact_list() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }
//
//    @Then("show message {string}")
//    public void show_message(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
//    }
//
//    @Then("list has name {string}")
//    public void list_has_name(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
//    }
//
//    @Then("list has email {string}")
//    public void list_has_email(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
//    }

    @Given("user visits import csv page")
    public void userVisitsContactPage() {
        driver.get("http://localhost:" + port + "/import-csv");
    }

}