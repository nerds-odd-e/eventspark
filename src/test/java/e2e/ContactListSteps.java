package e2e;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.core.Is.is;

public class ContactListSteps {
    @Autowired
    private WebDriver driver;

    @LocalServerPort
    private int port;

    @Given("^ContactList address is \"([^\"]*)\"$")
    public void address_is(String address) throws Throwable {
        driver.findElement(By.id("address")).sendKeys(address);
    }

    @Given("^ContactList name is \"([^\"]*)\"$")
    public void name_is(String name) throws Throwable {
        driver.findElement(By.id("name")).sendKeys(name);
    }

    @Given("^ContactList is empty")
    public void clear_contact_list() throws Throwable {
        AddressBook addressBook = new AddressBook();
        addressBook.save();
    }

    @Given("^ContactList has \"([^\"]*)\"$")
    public void add_contact(String address) throws Throwable {
        AddressBook addressBook = new AddressBook();
        addressBook.add(new AddressItem(address));
        addressBook.save();
    }

    @When("^add$")
    public void add() throws Throwable {
        driver.findElement(By.id("add")).click();
    }

    @Then("^ContactList error_area is \"([^\"]*)\"$")
    public void error_area_is(String errorArea) throws Throwable {
        String actual = driver.findElement(By.id("error-area")).getText();
        Assert.assertEquals(errorArea, actual);
    }

    @Then("^ContactList address is added \"([^\"]*)\"$")
    public void contact_list_address_has(String address) throws Throwable {
        String html = driver.findElement(By.id("address-list")).getText();
        Assert.assertThat(html.contains(address), is(true));
    }

    @Then("^ContactList name is added \"([^\"]*)\"$")
    public void contact_list_name_has(String name) throws Throwable {
        String html = driver.findElement(By.id("address-list")).getText();
        Assert.assertThat(html.contains(name), is(true));
    }

    @When("^create email$")
    public void create_email() throws Throwable {
        driver.findElement(By.id("create-email")).click();
    }

    @Then("^MailSender address is \"([^\"]*)\"$")
    public void mail_sender_address_is(String address) throws Throwable {
        String html = driver.findElement(By.id("address")).getAttribute("value");
        Assert.assertEquals(address, html);
    }

    @Given("^No ContactList is checked$")
    public void no_contact_list_is_checked() throws Throwable {
    }

    @Given("^checked ContactList is \"([^\"]*)\"$")
    public void checked_contact_list_is(String address) throws Throwable {
        driver.findElement(By.cssSelector("input[value='" + address + "']")).click();
    }

    @Given("^checked all ContactList$")
    public void checked_all_contact_list() throws Throwable {
        driver.findElement(By.id("all")).click();
    }

    @Given("user visits contact page")
    public void userVisitsContactPage() {
        driver.get("http://localhost:" + port + "/contact-list");
    }

    @When("click link")
    public void click_link() {
        driver.findElement(By.id("import-csv")).click();
    }

    @Then("move to import page")
    public void move_to_import_page() {
        String url = driver.getCurrentUrl();
        Assert.assertEquals("import-csv", url.substring(url.length() - 10));
    }
}