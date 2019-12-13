package e2e;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import e2e.pages.HomePage;
import e2e.pages.PreviewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PreviewSteps {

    @Autowired
    private PreviewPage previewPage;

    @Autowired
    private HomePage homePage;

    @When("^preview$")
    public void preview() {
        homePage.goToPreview();
    }

    @Then("^show preview (\\d+)")
    public void showPreviewWindow(int index) {
        assertTrue(previewPage.isPreviewNumber(index));
    }

    @Then("^show home window")
    public void showHomeWindow() throws InterruptedException {
        Thread.sleep(3000);
        assertTrue(homePage.isCurrentPage());
    }

    @When("press back to home button")
    public void pressBackToHomeButton() {
        previewPage.goBackToHome();
    }

    @When("click next button")
    public void clickNextButton() {
        previewPage.goToNextPreviewPage();
    }

    @When("click previous button")
    public void clickPreviousButton() {
        previewPage.goToPreviousPreviewPage();
    }

    @Then("previous button is disabled")
    public void previousButtonIsDisabled() {
        assertTrue(previewPage.isPrevButtonDisabled());
    }

    @Then("next button is disabled")
    public void nextButtonIsDisabled() {
        assertTrue(previewPage.isNextButtonDisabled());
    }

    @Then("previewed address is \"([^\"]*)\"$")
    public void previewedAddressIs(String address) {
        assertThat(previewPage.getAddressPreviewContent(), is(address));
    }


    @Then("^variables are replaced with \"([^\"]*)\" in body$")
    public void variablesAreReplacedInBody(String name) {
        assertThat(previewPage.getBodyPreviewContent(), is("Hello " + name));
    }

    @Then("^variables are replaced with \"([^\"]*)\" in subject$")
    public void variablesAreReplacedInSubject(String name) {
        assertThat(previewPage.getSubjectPreviewContent(), is("Hi " + name));
    }

}
