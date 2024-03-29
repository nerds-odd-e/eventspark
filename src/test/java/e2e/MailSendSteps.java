package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.service.MailInfo;
import e2e.pages.HomePage;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class MailSendSteps {
    @Autowired
    private GreenMail greenMail;

    @Autowired
    private HomePage homePage;

    @Given("^address is \"([^\"]*)\"$")
    public void address_is(String address) {
        homePage.fillAddressField(address);
    }

    @Given("^subject is \"([^\"]*)\"$")
    public void subject_is(String subject) {
        homePage.fillSubjectField(subject);
    }

    @Given("^body is \"([^\"]*)\"$")
    public void body_is(String body) {
        homePage.fillBodyField(body);
    }

    @When("^send$")
    public void send() {
        homePage.sendEmail();
    }

    @Then("^error_area is \"([^\"]*)\"$")
    public void error_area_is(String errorArea) {
        Assert.assertEquals(errorArea, homePage.getErrorText());
    }

    @Then("^error_area is none$")
    public void error_area_is_none() {
        assertFalse(homePage.errorAreaExists());
    }


    @DataTableType
    public MailInfo mailInfoEntry(java.util.Map<String, String> entry) {
        return new MailInfo(entry.get("from"), entry.get("to"), entry.get("subject"), entry.get("body"));
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
    public void error_areaContains(String errorMessage) {
        Assert.assertTrue(homePage.getErrorText().contains(errorMessage));
    }
}