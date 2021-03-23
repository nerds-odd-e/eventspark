package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import e2e.pages.ContactListPage;
import e2e.pages.EventListPage;
import e2e.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Component;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AdminSigninSteps{
    @Autowired
    private EventListPage eventListPage;

    @Given("A user has not sign-in yet")
    public void a_user_has_not_sign_in_yet() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("I open a event list page")
    public void i_open_a_event_list_page() {
        eventListPage.goToPage();
    }

    @Then("Can see the button for signing as admin.")
    public void can_see_the_button_for_signing_as_admin() {
    }
}