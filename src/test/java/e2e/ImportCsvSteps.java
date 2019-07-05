package e2e;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import e2e.pages.ImportCsvPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ImportCsvSteps {
    @Autowired
    private WebDriver driver;

    @Autowired
    private ImportCsvPage importCsvPage;

    @LocalServerPort
    private int port;

    @Given("user visits import csv page")
    public void userVisitsContactPage() {
        importCsvPage.userVisitsContactPage();
//        driver.get("http://localhost:" + port + "/import-csv");
    }

    @Given("select {string} CSV")
    public void select_CSV(String string) {
        importCsvPage.selectFile(new File("src/test/resources/" + string));
    }

    @When("click import button")
    public void click_import_button() {
        importCsvPage.clickImport();
    }

    @Then("move to contact list")
    public void move_to_contact_list() {
        // Contact List
        String actual = driver.findElement(By.id("contact-list-title")).getText();
        assertEquals("Contact List", actual);
    }


    // erro / warn case
    @Then("show error {string} at import page")
    public void show_error_at_import_page(String string) {
        assertEquals(string, importCsvPage.getErrorText());
    }

    @Then("show message {string}")
    public void show_message(String string) {
        // Contact List
        String actual = driver.findElement(By.id("success-area")).getText();
        assertEquals(string, actual);
    }

    @Then("ContactList multiple values are added {string}")
    public void contactlist_multiple_values_are_added(String string) {
        String[] values = string.split(";");
        for (String v: values) {
            String html = driver.findElement(By.id("address-list")).getText();
            assertThat(html.contains(v), is(true));
        }
    }

    @When("click modal yes")
    public void click_modal_yes() {
        importCsvPage.waitAndClickForceButton();
    }
}
