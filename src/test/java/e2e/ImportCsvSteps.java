package e2e;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import e2e.pages.ContactListPage;
import e2e.pages.ImportCsvPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ImportCsvSteps {
    @Autowired
    private ContactListPage contactListPage;

    @Autowired
    private ImportCsvPage importCsvPage;

    @Given("user visits import csv page")
    public void userVisitsContactPage() {
        importCsvPage.userVisitsContactPage();
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
        assertEquals("Contact List", contactListPage.getTitleText());
    }

    // erro / warn case
    @Then("show error {string} at import page")
    public void show_error_at_import_page(String string) {
        assertEquals(string, importCsvPage.getErrorText());
    }

    @Then("show message {string}")
    public void show_message(String string) {
        assertEquals(string, contactListPage.getSuccessText());
    }

    @Then("ContactList multiple values are added {string}")
    public void contactlist_multiple_values_are_added(String string) {
        for (String value: string.split(";")) {
            String html = contactListPage.getAddressListText();
            assertThat(html.contains(value), is(true));
        }
    }

    @When("click modal yes")
    public void click_modal_yes() {
        importCsvPage.waitAndClickForceButton();
    }
}
