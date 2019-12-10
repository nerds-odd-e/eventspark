package e2e;

import e2e.pages.EventDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;

import java.time.LocalDateTime;
public class EventDetailSteps {
    @Autowired
    private EventDetailPage eventDetailPage;

    @Autowired
    private EventRepository eventRepository;

    @Given("ゴスペルワークショップのイベント名のデータが{int}件DBにあること")
    public void ゴスペルワークショップのイベント名のデータが_件dbにあること(Integer int1) {
        eventRepository.deleteAll();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Event event = Event.builder()
                .id("1")
                .eventName("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .createUserName("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .eventStartDateTime(currentDateTime)
                .eventEndDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detailText("ゴスペルワークショップ")
                .build();
        eventRepository.insert(event);
    }

    @When("{string}のイベント詳細ページを表示する")
    public void _のイベント詳細ページを表示する(String title) {
        eventDetailPage.userVisitsEventDetailPage(title);
    }

    @Then("{string}のイベントの内容とチケットの内容を表示する。")
    public void _のイベントの内容とチケットの内容を表示する(String title) {
        Event expectedEvent = eventRepository.findByEventName(title);

        Assert.assertEquals(expectedEvent.getEventName(), eventDetailPage.getTitleText());
        Assert.assertEquals(expectedEvent.getLocation(), eventDetailPage.getLocationText());
        Assert.assertEquals(expectedEvent.getCreateUserName(), eventDetailPage.getCreateUserNameText());
        Assert.assertEquals(expectedEvent.getSummary(), eventDetailPage.getSummaryText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventStartDateTime()), eventDetailPage.getStartDateText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventEndDateTime()), eventDetailPage.getEndDateText());
        Assert.assertEquals(expectedEvent.getDetailText(), eventDetailPage.getDetailText());
    }

    @When("イベントプレビューページを表示する")
    public void イベントプレビューページを表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("ゴスペルワークショップのイベントのプレビュー内容とチケットの内容を表示する。")
    public void ゴスペルワークショップのイベントのプレビュー内容とチケットの内容を表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
