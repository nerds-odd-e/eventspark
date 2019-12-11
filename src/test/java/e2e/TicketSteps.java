package e2e;

import e2e.pages.AddTicketPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TicketSteps {

    @Autowired
    private AddTicketPage addTicketPage;

    @Given("チケット追加画面を表示している")
    public void チケット追加画面を表示している() {
        // FIXME イベント画面から遷移する
        addTicketPage.goToAddTicketPage("a");
    }

    public static class チケットを登録する {

        @Autowired
        private AddTicketPage addTicketPage;

        @When("以下の入力を行い「登録」ボタンをクリックすると")
        public void 以下の入力を行い_登録_ボタンをクリックすると(io.cucumber.datatable.DataTable dataTable) {
            Map<Object, String> table = dataTable.asMap(String.class, String.class);
            addTicketPage.fillTicketName(table.get("チケット名"));
            addTicketPage.fillTicketPrice(table.get("金額"));
            addTicketPage.fillTicketTotal(table.get("枚数"));
            addTicketPage.fillTicketLimit(table.get("上限数"));
            addTicketPage.submit();
        }

        @Then("イベント詳細ページが表示され")
        public void イベント詳細ページが表示され() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("イベント詳細ページに入力したチケット情報が表示されている")
        public void イベント詳細ページに入力したチケット情報が表示されている() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

    }

    public static class 全ての項目を空で登録ボタンを押下 {
        @Autowired
        private AddTicketPage addTicketPage;

        @When("全てのフォームを空にして「登録」ボタンをクリックすると")
        public void 全てのフォームを空にして_登録_ボタンをクリックすると() {
            addTicketPage.fillTicketName("");
            addTicketPage.fillTicketPrice("");
            addTicketPage.fillTicketTotal("");
            addTicketPage.fillTicketLimit("");
            addTicketPage.submit();
        }

        @Then("チケット登録画面が表示された状態で")
        public void チケット登録画面が表示された状態で() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("画面上に「チケット名を入力してください」が表示され")
        public void 画面上に_チケット名を入力してください_が表示され() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("画面上に「金額を入力してください」が表示され")
        public void 画面上に_金額を入力してください_が表示され() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("画面上に「枚数を入力してください」が表示され")
        public void 画面上に_枚数を入力してください_が表示され() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }

        @Then("画面上に「一人当たりの上限数を入力してください」が表示される")
        public void 画面上に_一人当たりの上限数を入力してください_が表示される() {
            // Write code here that turns the phrase above into concrete actions
            throw new cucumber.api.PendingException();
        }
    }
}
