package e2e;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class EventDetailSteps {

    @Autowired
    private EventRepository eventRespository;

    @Given("ゴスペルワークショップのイベント名のデータが{int}件DBにあること")
    public void ゴスペルワークショップのイベント名のデータが_件dbにあること(Integer int1) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Event event = Event.builder()
                .id("1")
                .eventName("ゴスペルワークショプ")
                .location("東京国際フォーラム")
                .createUserName("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショプのイベントです。")
                .eventStartDateTime(currentDateTime)
                .eventEndDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detailText("ゴスペルワークショプ")
                .build();
        eventRespository.insert(event);
        Optional<Event> result = eventRespository.findById("1");
        assertEquals("ゴスペルワークショップ", result.get().getEventName());

    }

    @When("イベント詳細ページを表示する")
    public void イベント詳細ページを表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("ゴスペルワークショプのイベントの内容とチケットの内容を表示する。")
    public void ゴスペルワークショプのイベントの内容とチケットの内容を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
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
