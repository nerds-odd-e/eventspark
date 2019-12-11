package e2e;

import e2e.pages.AddTicketPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TicketSteps {

    public static class チケットを登録する {

        @Autowired
        private AddTicketPage addTicketPage;

        @Given("チケット追加画面を表示している")
        public void チケット追加画面を表示している() {
            // FIXME イベント画面から遷移する
            addTicketPage.goToAddTicketPage("a");
        }

        @When("以下の入力を行う")
        public void 以下の入力を行う(io.cucumber.datatable.DataTable dataTable) {
            Map<Object, String> table = dataTable.asMap(String.class, String.class);
            addTicketPage.fillTicketName(table.get("チケット名"));
            addTicketPage.fillTicketPrice(table.get("金額"));
            addTicketPage.fillTicketTotal(table.get("枚数"));
            addTicketPage.fillTicketLimit(table.get("上限数"));
        }

        @When("「登録」ボタンをクリックする")
        public void 登録_ボタンをクリックする() {
            addTicketPage.submit();
        }

        @Then("イベント詳細ページに戻る")
        public void イベント詳細ページに戻る() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("入力したチケット情報が表示されている")
        public void 入力したチケット情報が表示されている() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("画面遷移しない")
        public void 画面遷移しない() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }
    }

    public static class 全ての項目を空で登録ボタンを押下 {

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
}
