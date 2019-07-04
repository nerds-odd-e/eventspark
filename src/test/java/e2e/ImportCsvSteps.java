package e2e;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.hamcrest.core.Is.is;

public class ImportCsvSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @Given("select {string} CSV")
    public void select_CSV(String string) {
        File file = new File("src/test/resources/" + string);
        driver.findElement(By.id("csvfile")).sendKeys(file.getAbsolutePath());
    }

    @When("click import button")
    public void click_import_button() {
        driver.findElement(By.id("import")).click();
    }

    @Then("move to contact list")
    public void move_to_contact_list() {
        String actual = driver.findElement(By.id("contact-list-title")).getText();
        Assert.assertEquals("Contact List", actual);
    }

    @Given("user visits import csv page")
    public void userVisitsContactPage() {
        driver.get("http://localhost:" + port + "/import-csv");
    }

    // erro / warn case
    @Then("show error {string} at import page")
    public void show_error_at_import_page(String string) {
        String actual = driver.findElement(By.id("error-area")).getText();
        Assert.assertEquals(string, actual);
    }

    @Then("show message {string}")
    public void show_message(String string) {
        String actual = driver.findElement(By.id("success-area")).getText();
        Assert.assertEquals(string, actual);
    }

    @Then("ContactList multiple values are added {string}")
    public void contactlist_multiple_values_are_added(String string) {
        String[] values = string.split(";");
        for (String v: values) {
            String html = driver.findElement(By.id("address-list")).getText();
            Assert.assertThat(html.contains(v), is(true));
        }
    }

    @Then("show warn message {string}")
    public void show_warn_message(String string) {
        String actual = driver.findElement(By.id("warning-area")).getText();
        Assert.assertEquals(string, actual);
    }

    @When("click modal yes")
    public void click_modal_yes() throws InterruptedException {
        // Wait for the modal display
        Thread.sleep(1000);
        driver.findElement(By.id("forceButton")).click();
    }
}
