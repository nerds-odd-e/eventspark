package e2e;

import e2e.pages.AddTicketPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class AddTicketSteps {

    @Autowired
    private AddTicketPage addTicketPage;

    @Given("チケット追加画面を表示している")
    public void チケット追加画面を表示している() {
        addTicketPage.goToAddTicketPage("a");
    }

    @When("チケット名{string}を入力する")
    public void チケット名を入力する(String ticketName) {
        addTicketPage.fillTicketName(ticketName);
    }

    @When("金額に「」を入力する")
    public void 金額に_を入力する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("枚数に「」を入力する")
    public void 枚数に_を入力する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("一人当たりの上限数に「」を入力する")
    public void 一人当たりの上限数に_を入力する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("「登録」ボタンをクリックする")
    public void 登録_ボタンをクリックする() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("画面遷移しない")
    public void 画面遷移しない() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("画面上に「チケット名を入力してください」を表示する")
    public void 画面上に_チケット名を入力してください_を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("画面上に「金額を入力してください」を表示する")
    public void 画面上に_金額を入力してください_を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("画面上に「枚数を入力してください」を表示する")
    public void 画面上に_枚数を入力してください_を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("画面上に「一人当たりの上限数を入力してください」を表示する")
    public void 画面上に_一人当たりの上限数を入力してください_を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
