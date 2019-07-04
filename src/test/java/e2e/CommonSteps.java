package e2e;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import e2e.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommonSteps {

    @Autowired
    private HomePage homePage;

    @Given("user visits our homepage")
    public void userVisitsSendPage() {
        homePage.userVisitsSendPage();
    }

    @And("address is filled with \"([^\"]*)\"")
    public void addressIsFilledWith(String address) {
        String actual = homePage.getInputAddressText();
        assertThat(actual, is(address));
    }

    @And("subject is filled with \"([^\"]*)\"")
    public void subjectIsFilledWith(String subject) {
        String actual = homePage.getInputSubjectText();
        assertThat(actual, is(subject));
    }

    @And("body is filled with \"([^\"]*)\"")
    public void bodyIsFilledWith(String body) {
        String actual = homePage.getInputBodyText();
        assertThat(actual, is(body));
    }
}
