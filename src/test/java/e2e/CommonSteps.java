package e2e;

import e2e.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommonSteps {

    @Autowired
    private HomePage homePage;

    @Given("user visits our homepage")
    public void userVisitsSendPage() {
        homePage.userVisitsSendPage();
    }

    @And("address is filled with {string}")
    public void addressIsFilledWith(String address) {
        String actual = homePage.getInputAddressText();
        assertThat(actual, is(address));
    }

    @Then("subject is filled with {string}")
    public void subjectIsFilledWith(String subject) {
        String actual = homePage.getInputSubjectText();
        assertThat(actual, is(subject));
    }

    @And("body is filled with {string}")
    public void bodyIsFilledWith(String body) {
        String actual = homePage.getInputBodyText();
        assertThat(actual, is(body));
    }
}
