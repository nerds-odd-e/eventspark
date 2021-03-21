package e2e;

import com.odde.mailsender.data.Address;
import com.odde.mailsender.service.AddressRepository;
import e2e.pages.ContactListPage;
import e2e.pages.HomePage;
import e2e.pages.ImportCsvPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public class ContactListSteps {
    @Autowired
    private WebDriver driver;

    @Autowired
    private AddressRepository addressRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private ContactListPage contactListPage;

    @Autowired
    private HomePage homePage;

    @Autowired
    private ImportCsvPage importCsvPage;

    @Given("^ContactList address is \"([^\"]*)\"$")
    public void address_is(String address) {
        contactListPage.fillAddressField(address);
    }

    @Given("^ContactList name is \"([^\"]*)\"$")
    public void name_is(String name) {
        contactListPage.fillNameField(name);
    }

    @Given("^ContactList is empty")
    public void clear_contact_list() throws Exception {
        addressRepository.deleteAll();
    }

    @Given("^ContactList has \"([^\"]*)\"$")
    public void add_contact(String address) {
        addressRepository.save(new Address("", address));
    }

    @When("^add new contact to ContactList$")
    public void add() {
        contactListPage.clickAddButton();
    }

    @Then("^ContactList error_area is \"([^\"]*)\"$")
    public void error_area_is(String errorArea) {
        Assert.assertEquals(errorArea, contactListPage.getErrorAreaText());
    }

    @Then("^ContactList address is added \"([^\"]*)\"$")
    public void contact_list_address_has(String address) {
        Assert.assertTrue(contactListPage.addressListContains(address));
    }

    @Then("^ContactList name is added \"([^\"]*)\"$")
    public void contact_list_name_has(String name) {
        Assert.assertTrue(contactListPage.addressListContains(name));
    }

    @When("^create email$")
    public void create_email() {
        contactListPage.clickCreateEmailButton();
    }

    @Then("^Email form address is \"([^\"]*)\"$")
    public void mail_sender_address_is(String address) {
        Assert.assertEquals(address, homePage.getInputAddressText());
    }

    @Given("^No ContactList is checked$")
    public void no_contact_list_is_checked() {
        contactListPage.uncheckAllMailAddresses();
    }

    @Given("^checked ContactList is \"([^\"]*)\"$")
    public void checked_contact_list_is(String address) {
        contactListPage.selectContactWithAddress(address);
    }

    @Given("^checked all ContactList$")
    public void checked_all_contact_list() {
        contactListPage.clickSelectAllCheckbox();
    }

    @Given("user visits contact page")
    public void userVisitsContactPage() {
        contactListPage.goToContactPage();
    }

    @When("click Import CSV link")
    public void click_link() {
        contactListPage.clickImportCsvButton();
    }

    @Then("browser moves to import page")
    public void move_to_import_page() {
        Assert.assertTrue(importCsvPage.isImportCsvPage());
    }
}