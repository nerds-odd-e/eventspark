package e2e;

import e2e.pages.HomePage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class MailTemplateSteps {
    @Autowired
    private HomePage homePage;

    @When("^click load button$")
    public void click_load_button() {
        homePage.loadTemplate();
    }
}
