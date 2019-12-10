package e2e;

import e2e.pages.EventDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class EventDetailSteps {
    @Autowired
    private EventDetailPage eventDetailPage;

    @Given("ゴスペルワークショップのイベント名のデータが{int}件DBにあること")
    public void ゴスペルワークショップのイベント名のデータが_件dbにあること(Integer int1) {
    }

    @When("イベント詳細ページを表示する")
    public void イベント詳細ページを表示する() {
        eventDetailPage.userVisitsEventDetailPage("aa");
    }

    @Then("{string}のイベントの内容とチケットの内容を表示する。")
    public void _のイベントの内容とチケットの内容を表示する(String title) {
        Assert.assertEquals(title, eventDetailPage.getTitleText());
    }

    @When("イベントプレビューページを表示する")
    public void イベントプレビューページを表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("ゴスペルワークショプのイベントのプレビュー内容とチケットの内容を表示する。")
    public void ゴスペルワークショプのイベントのプレビュー内容とチケットの内容を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
