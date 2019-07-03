package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.service.MailInfo;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MailSendSteps {
    @Autowired
    private WebDriver driver;

    @Autowired
    private GreenMail greenMail;

    @LocalServerPort
    private int port;


    @Given("user visits our homepage")
    public void userVisitsSendPage() {
        driver.get("http://localhost:" + port);
    }

    @Given("^address is \"([^\"]*)\"$")
    public void address_is(String address) throws Throwable {
        driver.findElement(By.id("address")).sendKeys(address);
    }

    @Given("^subject is \"([^\"]*)\"$")
    public void subject_is(String subject) throws Throwable {
        driver.findElement(By.id("subject")).sendKeys(subject);
    }

    @Given("^body is \"([^\"]*)\"$")
    public void body_is(String body) throws Throwable {
        driver.findElement(By.id("body")).sendKeys(body);
    }

    @When("^send$")
    public void send() throws Throwable {
        driver.findElement(By.id("send")).click();
    }

    @When("^preview$")
    public void preview() throws Throwable {
        driver.findElement(By.id("preview")).click();
    }

    @When("press back to home button")
    public void pressBackToHomeButton() {
        driver.findElement(By.id("back-to-home")).click();
    }

    @Then("^error_area is \"([^\"]*)\"$")
    public void error_area_is(String errorArea) throws Throwable {
        String actual = driver.findElement(By.id("error-area")).getText();
        Assert.assertEquals(errorArea, actual);
    }

    @Then("^error_area is none$")
    public void error_area_is_none() throws Throwable {
        try {
            driver.findElement(By.id("error-area"));
            fail();
        } catch (NoSuchElementException e) {
            /* こちらが正常系*/
        }
    }

    @Then("^should receive the following emails:$")
    public void should_receive_the_following_emails(List<MailInfo> mails) throws Throwable {
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        for (int i = 0; i < mails.size(); i++) {
            MimeMessage message = receivedMessages[i];
            assertThat(((InternetAddress)message.getFrom()[0]).getAddress(), is(mails.get(i).getFrom()));
            assertThat(((InternetAddress)message.getRecipients(Message.RecipientType.TO)[0]).getAddress(), is(mails.get(i).getTo()));
            assertThat(message.getSubject(), is(mails.get(i).getSubject()));
            assertThat(message.getContent().toString(), containsString(mails.get(i).getBody()));
        }
    }

    @And("stop the mail server")
    public void mailServerIsStopping() {
        greenMail.stop();
    }

    @And("start the mail server")
    public void startTheMailServerBack() {
        greenMail.start();
    }

    @Then("^error_area contains \"([^\"]*)\"$")
    public void error_areaContains(String errorMessage) throws Throwable {
        String actual = driver.findElement(By.id("error-area")).getText();
        Assert.assertTrue(actual.contains(errorMessage));
    }

    @Then("^show preview window")
    public void showPreviewWindow() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, containsString("/preview"));
    }

    @Then("^show home window")
    public void showHomeWindow() {
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl, containsString("/home"));
    }

    @And("^variables are replaced with \"([^\"]*)\" in body$")
    public void variablesAreReplacedInBody(String name) {
//        String body = driver.findElement(By.id("body")).getText();
        String body = driver.findElement(By.id("body")).getText();
        assertThat(body, is("Hi " + name));
    }

    @And("^variables are replaced with \"([^\"]*)\" in subject$")
    public void variablesAreReplacedInSubject(String name) {
        String subject = driver.findElement(By.id("subject")).getText();
        assertThat(subject, is("Hi " + name));
    }

    @And("address is filled with \"([^\"]*)\"")
    public void addressIsFilledWith(String address) {
        String actual = driver.findElement(By.id("address")).getAttribute("value");
        assertThat(actual, is(address));
    }

    @And("subject is filled with \"([^\"]*)\"")
    public void subjectIsFilledWith(String subject) {
        String actual = driver.findElement(By.id("subject")).getAttribute("value");
        assertThat(actual, is(subject));
    }

    @And("body is filled with \"([^\"]*)\"")
    public void bodyIsFilledWith(String body) {
        String actual = driver.findElement(By.id("body")).getAttribute("value");
        assertThat(actual, is(body));
    }
}