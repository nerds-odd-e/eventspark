package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.service.MailInfo;
import e2e.pages.EventRegisterPage;
import e2e.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EventRegisterSteps {
    @Autowired
    private EventRegisterPage eventRegisterPage;
    @Autowired
    private GreenMail greenMail;

    @Given("user visits register page")
    public void userVisitsRegisterPage() {
    }

    @When("購入ボタンを押す")
    public void 購入ボタンを押す() {
        eventRegisterPage.purchase();
    }

    @Then("^支払メールを送信する$")
    public void 支払メールを送信する(List<MailInfo> mails) throws Throwable {
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage message = receivedMessages[0];
        assertThat(((InternetAddress) message.getFrom()[0]).getAddress(), is(mails.get(0).getFrom()));
        assertThat(((InternetAddress) message.getRecipients(Message.RecipientType.TO)[0]).getAddress(), is(mails.get(0).getTo()));
        assertThat(message.getSubject(), is(mails.get(0).getSubject()));
        assertThat(message.getContent().toString(), containsString(mails.get(0).getBody()));
    }


    @And("参加登録完了画面を表示する")
    public void 参加登録完了画面を表示する() {
            eventRegisterPage.goToPurchasedPage();
        }

    @Given("名前{string}が入力されている")
    public void 名前が入力されている(String arg0) {
        String actual = eventRegisterPage.getUserName();
        Assert.assertEquals(actual, is(arg0));

    }

    @And("メールアドレス{string}が入力されている")
    public void メールアドレスが入力されている(String arg0) {
        String actual = eventRegisterPage.getMailAddress();
        Assert.assertEquals(actual, is(arg0));
    }


    @And("枚数{int}が入力されている")
    public void 枚数が入力されている(int arg0) {
        Integer actual = eventRegisterPage.getTicketNumber();
        Assert.assertEquals(actual, is(arg0));

    }

    @And("チケット種別{int}が選択されている")
    public void チケット種別が選択されている(int arg0) {
        Integer actual = eventRegisterPage.getTicketType();
        Assert.assertEquals(actual, is(arg0));
    }
}
