package e2e;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;
import e2e.pages.EventDetailPage;
import e2e.pages.EventDetailForOwnerPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class EventSteps {
    @Autowired
    private EventDetailPage eventDetailPage;

    @Autowired
    private EventDetailForOwnerPage eventDetailForOwnerPage;

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

    @Then("{string}のイベントの内容とチケットの内容のイベント詳細を表示する。")
    public void _のイベントの内容とチケットの内容のイベント詳細を表示する(String title) {
        Event expectedEvent = eventRepository.findByEventName(title);

        Assert.assertEquals(expectedEvent.getEventName(), eventDetailPage.getEventNameText());
        Assert.assertEquals(expectedEvent.getLocation(), eventDetailPage.getLocationText());
        Assert.assertEquals(expectedEvent.getCreateUserName(), eventDetailPage.getCreateUserNameText());
        Assert.assertEquals(expectedEvent.getSummary(), eventDetailPage.getSummaryText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventStartDateTime()), eventDetailPage.getStartDateText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventEndDateTime()), eventDetailPage.getEndDateText());
        Assert.assertEquals(expectedEvent.getDetailText(), eventDetailPage.getDetailText());
    }

    @When("{string}のオーナー用イベント詳細ページを表示する")
    public void _のオーナー用イベント詳細ページを表示する(String eventName) {
        eventDetailForOwnerPage.userVisitsEventPreviewPage(eventName);
    }

    @Then("{string}のイベントの内容とチケットの内容のオーナー用イベント詳細ページを表示する。")
    public void _のイベントの内容とチケットの内容のオーナー用イベント詳細ページを表示する(String eventName) {
        Event expectedEvent = eventRepository.findByEventName(eventName);

        Assert.assertEquals(expectedEvent.getEventName(), eventDetailForOwnerPage.getTitleText());
        Assert.assertEquals(expectedEvent.getLocation(), eventDetailForOwnerPage.getLocationText());
        Assert.assertEquals(expectedEvent.getCreateUserName(), eventDetailForOwnerPage.getCreateUserNameText());
        Assert.assertEquals(expectedEvent.getSummary(), eventDetailForOwnerPage.getSummaryText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventStartDateTime()), eventDetailForOwnerPage.getStartDateText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEventEndDateTime()), eventDetailForOwnerPage.getEndDateText());
        Assert.assertEquals(expectedEvent.getDetailText(), eventDetailForOwnerPage.getDetailText());
    }

    @Given("ゆうこさんが存在する")
    public void ゆうこさんが存在する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("イベント追加ページにゴスペルワークショップの情報を入力する")
    public void イベント追加ページにゴスペルワークショップの情報を入力する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("入力したゴスペルワークショップの情報がDBに登録される")
    public void 入力したゴスペルワークショップの情報がdbに登録される() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("入力したゴスペルワークショップの情報が詳細ページに表示される")
    public void 入力したゴスペルワークショップの情報が詳細ページに表示される() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("イベント追加ページを表示する")
    public void イベント追加ページを表示する() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("イベント名がゴスペルワークショップのイベントのデータが{int}件DBにあること")
    public void イベント名がゴスペルワークショップのイベントのデータが_件DBにあること(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("イベント更新ページに変更内容を入力して確定ボタンを押す")
    public void イベント更新ページに変更内容を入力して確定ボタンを押す() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("変更した内容がイベント詳細ページに表示されていること")
    public void 変更した内容がイベント詳細ページに表示されていること() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

}
