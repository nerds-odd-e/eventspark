package e2e;

import com.icegreen.greenmail.util.GreenMail;
import com.odde.mailsender.service.MailInfo;
import e2e.pages.EventDetailPage;
import e2e.pages.EventRegisterPage;
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
import static org.junit.Assert.assertThat;

public class EventRegisterSteps {
    @Autowired
    private EventRegisterPage eventRegisterPage;
    @Autowired
    private GreenMail greenMail;
    @Autowired
    private EventDetailPage eventDetailPage;

    @Given("以下が入力されている")
    public void 以下が入力されている(io.cucumber.datatable.DataTable dataTable) {
        名前が入力されている(dataTable.cell(0, 1));
        苗字が入力されている(dataTable.cell(1, 1));
        会社名が入力されている(dataTable.cell(2, 1));
        メールアドレスが入力されている(dataTable.cell(3, 1));
        チケットが選択されている(dataTable.cell(4, 1));
        枚数が入力されている(dataTable.cell(5, 1));
    }

    @Given("会社名{string}が入力されている")
    private void 会社名が入力されている(String input) {
        eventRegisterPage.fillCompanyName(input);
    }

    @Given("苗字{string}が入力されている")
    private void 苗字が入力されている(String input) {
        eventRegisterPage.fillLastName(input);
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
    public void 名前が入力されている(String input) {
        eventRegisterPage.fillFirstName(input);
    }

    @And("メールアドレス{string}が入力されている")
    public void メールアドレスが入力されている(String input) {
        eventRegisterPage.fillMailAddress(input);
    }


    @And("枚数{int}が入力されている")
    public void 枚数が入力されている(String input) {
        eventRegisterPage.fillTicketCount(input);
    }

    @And("チケット{int}が選択されている")
    public void チケットが選択されている(String input) {
        eventRegisterPage.fillTicketId(input);
    }


    @Then("参加者登録画面エラーエリアに{string}と表示される")
    public void 参加者登録画面エラーエリアに_と表示される(String string) {
        Assert.assertEquals(string, eventRegisterPage.getErrorText());
    }

    @And("ゴスペルワークショップの参加登録ページが表示されている")
    public void ゴスペルワークショップの参加登録ページが表示されている() {
        eventRegisterPage.goToEventRegisterPage();
    }

    @Then("{string}のイベント詳細ページが表示される")
    public void のイベント詳細ページが表示される(String input) {
        eventDetailPage.userVisitsEventDetailPage(input);
    }
}
