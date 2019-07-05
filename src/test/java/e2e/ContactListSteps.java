package e2e;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import e2e.pages.ContactListPage;
import e2e.pages.HomePage;
import e2e.pages.ImportCsvPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.core.Is.is;

public class ContactListSteps {
    @Autowired
    private WebDriver driver;

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
        AddressBook addressBook = new AddressBook();
        addressBook.save();
    }

    @Given("^ContactList has \"([^\"]*)\"$")
    public void add_contact(String address) throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.add(new AddressItem(address));
        addressBook.save();
    }

    @When("^add$")
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

    @Then("^MailSender address is \"([^\"]*)\"$")
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

    @When("click link")
    public void click_link() {
        contactListPage.clickImportCsvButton();
    }

    @Then("move to import page")
    public void move_to_import_page() {
        Assert.assertTrue(importCsvPage.isImportCsvPage());
    }
}