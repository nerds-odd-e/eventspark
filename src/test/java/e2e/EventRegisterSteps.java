package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.service.MailInfo;
import e2e.pages.EventRegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

    @Given("以下が入力されている")
    public void 以下が入力されている(io.cucumber.datatable.DataTable dataTable) {
        名前が入力されている(dataTable.cell(0, 1));
        メールアドレスが入力されている(dataTable.cell(1, 1));
        チケット種別が選択されている(dataTable.cell(2, 1));
        枚数が入力されている(dataTable.cell(3, 1));
    }
//    public void 以下が入力されている(List<RegisterForm> registerForm) {
//        名前が入力されている(registerForm.get(0).getName());
//        メールアドレスが入力されている(registerForm.get(0).getAddress());
//        チケット種別が選択されている(registerForm.get(0).getTicketType());
//        枚数が入力されている(registerForm.get(0).getTicketCount().toString());
//    }

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
    public void 名前が入力されている(String input) {
        eventRegisterPage.fillUserName(input);

    }

    @And("メールアドレス{string}が入力されている")
    public void メールアドレスが入力されている(String input) {
        eventRegisterPage.fillMailAddress(input);
    }


    @And("枚数{int}が入力されている")
    public void 枚数が入力されている(String input) {
        eventRegisterPage.fillTicketCount(input);

    }

    @And("チケット種別{int}が選択されている")
    public void チケット種別が選択されている(String input) {
        eventRegisterPage.fillTicketType(input);
    }
}
