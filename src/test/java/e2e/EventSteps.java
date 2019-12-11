package e2e;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.TicketRepository;
import e2e.pages.AddEventPage;
import e2e.pages.AddTicketPage;
import e2e.pages.EventDetailPage;
import e2e.pages.EventDetailForOwnerPage;
import e2e.pages.UserEventListPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class EventSteps {
    @Autowired
    private EventDetailPage eventDetailPage;

    @Autowired
    private AddTicketPage addTicketPage;

    @Autowired
    private EventDetailForOwnerPage eventDetailForOwnerPage;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AddEventPage addEventPage;

    @Autowired
    private UserEventListPage userEventListPage;

    @Given("ゴスペルワークショップのイベント名のデータが{int}件DBにあること")
    public void ゴスペルワークショップのイベント名のデータが_件dbにあること(Integer int1) {
        eventRepository.deleteAll();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Event event = Event.builder()
                .id("1")
                .name("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .owner("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .startDateTime(currentDateTime)
                .endDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detailText("ゴスペルワークショップ")
                .build();
        eventRepository.insert(event);
    }

    @When("{string}のオーナー用イベント詳細ページを表示する")
    public void _のオーナー用イベント詳細ページを表示する(String eventName) {
        eventDetailForOwnerPage.userVisitsEventPreviewPage(eventName);
    }

    @Given("イベント追加ページを表示する")
    public void イベント追加ページを表示する() {
        addEventPage.userVisitsAddEventPage();
    }

    @When("イベント追加ページに以下の情報を入力する")
    public void イベント追加ページに以下の情報を入力する(Map<String, String> datatable) {
        String name = datatable.get("イベント名");
        addEventPage.fillNameField(name);
        String location = datatable.get("場所");
        addEventPage.fillLocationField(location);
        String summary = datatable.get("サマリー");
        addEventPage.fillSummaryField(summary);
        String eventDetail = datatable.get("イベント情報");
        addEventPage.fillEventDetailField(eventDetail);
        String eventStartDate = datatable.get("イベント開始日時");
        addEventPage.fillEventStartDateField(eventStartDate);
        String eventEndDate = datatable.get("イベント終了日時");
        addEventPage.fillEventEndDateField(eventEndDate);

        addEventPage.clickAddButton();
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


    @When("オーナー用イベント詳細ページのチケット追加ボタンを押す")
    public void オーナー用イベント詳細ページのチケット追加ボタンを押す() {
        eventDetailForOwnerPage.submit();
    }

    @Then("チケット追加ページが表示されていること")
    public void チケット追加ページが表示されていること() throws UnsupportedEncodingException {
        assertTrue(addTicketPage.isCurrentPage("ゴスペルワークショップ"));
    }

    @Given("イベントオーナーが複数イベントを登録すると")
    public void イベントオーナーが複数イベントを登録すると() {
        eventRepository.deleteAll();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Stream.of("1", "2").forEach(id -> {
            Event event = Event.builder()
                    .id(id)
                    .name("ゴスペルワークショップ" + id)
                    .location("東京国際フォーラム")
                    .owner("ゆうこ")
                    .createDateTime(currentDateTime)
                    .updateDateTime(currentDateTime)
                    .summary("ゴスペルワークショップのイベントです。")
                    .startDateTime(currentDateTime)
                    .endDateTime(currentDateTime)
                    .publishedDateTime(currentDateTime)
                    .detailText("ゴスペルワークショップ")
                    .build();
            eventRepository.insert(event);

            Ticket ticket = Ticket.builder()
                    .eventId(event.getId())
                    .ticketName("ゴスペルチケット" + id)
                    .ticketPrice(1000)
                    .ticketTotal(100)
                    .ticketLimit(5)
                    .build();
            ticketRepository.insert(ticket);
        });
    }

    @When("公開中のイベント一覧ページをみると")
    public void 公開中のイベント一覧ページをみると() {
        userEventListPage.goToUserEventListPage();
    }

    @Then("一覧に複数イベントが見れる")
    public void 一覧に複数イベントが見れる() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }



    @When("ownerが{string}の詳細ページを見る")
    public void ownerが_の詳細ページを見る(String eventName) {
        eventDetailForOwnerPage.userVisitsEventPreviewPage(eventName);
    }

    @Then("{string}のイベントの内容とチケット追加ボタンが表示される")
    public void _のイベントの内容とチケット追加ボタンが表示される(String eventName) {
        Event expectedEvent = eventRepository.findByName(eventName);

        Assert.assertNotNull(expectedEvent);
        Assert.assertEquals(expectedEvent.getName(), eventDetailForOwnerPage.getTitleText());
        Assert.assertEquals(expectedEvent.getLocation(), eventDetailForOwnerPage.getLocationText());
        Assert.assertEquals(expectedEvent.getOwner(), eventDetailForOwnerPage.getCreateUserNameText());
        Assert.assertEquals(expectedEvent.getSummary(), eventDetailForOwnerPage.getSummaryText());
        Assert.assertEquals(String.valueOf(expectedEvent.getStartDateTime()), eventDetailForOwnerPage.getStartDateText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEndDateTime()), eventDetailForOwnerPage.getEndDateText());
        Assert.assertEquals(expectedEvent.getDetailText(), eventDetailForOwnerPage.getDetailText());
    }

    @When("userが{string}の詳細ページを見る")
    public void userが_の詳細ページを見る(String eventName) {
        eventDetailPage.userVisitsEventDetailPage(eventName);
    }

    @Then("{string}のイベントの内容とチケット購入ボタンが表示される")
    public void _のイベントの内容とチケット購入ボタンが表示される(String eventName) throws UnsupportedEncodingException {
        Event expectedEvent = eventRepository.findByName(eventName);

        Assert.assertEquals(expectedEvent.getName(), eventDetailPage.getEventNameText());
        Assert.assertEquals(expectedEvent.getLocation(), eventDetailPage.getLocationText());
        Assert.assertEquals(expectedEvent.getOwner(), eventDetailPage.getCreateUserNameText());
        Assert.assertEquals(expectedEvent.getSummary(), eventDetailPage.getSummaryText());
        Assert.assertEquals(String.valueOf(expectedEvent.getStartDateTime()), eventDetailPage.getStartDateText());
        Assert.assertEquals(String.valueOf(expectedEvent.getEndDateTime()), eventDetailPage.getEndDateText());
        Assert.assertEquals(expectedEvent.getDetailText(), eventDetailPage.getDetailText());
        Assert.assertEquals(eventDetailPage.getRegisterURL() + expectedEvent.getName(), eventDetailPage.getRegisterButtonURL());
    }

}
