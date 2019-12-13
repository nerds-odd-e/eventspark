package e2e;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;
import e2e.pages.AddTicketPage;
import e2e.pages.EventDetailForOwnerPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TicketSteps {

    @Autowired
    private AddTicketPage addTicketPage;

    @Autowired
    private EventRepository eventRepository;

    private Event aEvent = Event.builder()
            .id("1")
            .name("ゴスペルワークショップ")
            .location("東京国際フォーラム")
            .owner("ゆうこ")
            .createDateTime(LocalDateTime.now())
            .updateDateTime(LocalDateTime.now())
            .summary("ゴスペルワークショップのイベントです。")
            .startDateTime(LocalDateTime.now())
            .endDateTime(LocalDateTime.now())
            .publishedDateTime(LocalDateTime.now())
            .detail("ゴスペルワークショップ")
            .imagePath("https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png")
            .build();

    @Before
    public Event setUpEvent() {
        eventRepository.deleteAll();
        eventRepository.insert(aEvent);
        return aEvent;
    }

    @Given("チケット追加画面を表示している")
    public void チケット追加画面を表示している() {
        addTicketPage.goToAddTicketPage(aEvent.getName());
    }

    public static class チケットを登録する {

        @Autowired
        private AddTicketPage addTicketPage;

        @Autowired
        private EventDetailForOwnerPage eventDetailForOwnerPage;

        @When("以下の入力を行い「登録」ボタンをクリックすると")
        public void 以下の入力を行い_登録_ボタンをクリックすると(io.cucumber.datatable.DataTable dataTable) {
            insertTestData(dataTable.asMap(String.class, String.class));
            addTicketPage.submit();
        }

        @Then("イベント詳細ページが表示され")
        public void イベント詳細ページが表示され() throws Exception {
            eventDetailForOwnerPage.assertCurrentPage("ゴスペルワークショップ");
        }

        @Then("イベント詳細ページに入力したチケット情報が表示されている")
        public void イベント詳細ページに入力したチケット情報が表示されている(io.cucumber.datatable.DataTable dataTable) {
            Map<Object, String> table = dataTable.asMap(String.class, String.class);
            assertThat(eventDetailForOwnerPage.getTicketNameList(), hasItem(endsWith(": " + table.get("チケット名"))));
            assertThat(eventDetailForOwnerPage.getTicketPriceList(), hasItem(endsWith(": " + table.get("金額"))));
            assertThat(eventDetailForOwnerPage.getTicketTotalList(), hasItem(endsWith(": " + table.get("枚数"))));
            assertThat(eventDetailForOwnerPage.getTicketLimitList(), hasItem(endsWith(": " + table.get("上限数"))));
        }

        private void insertTestData(Map<Object, String> table) {
            addTicketPage.fillTicketName(table.get("チケット名"));
            addTicketPage.fillTicketPrice(table.get("金額"));
            addTicketPage.fillTicketTotal(table.get("枚数"));
            addTicketPage.fillTicketLimit(table.get("上限数"));
        }

    }

    public static class 全ての項目を空で登録ボタンを押下 {
        @Autowired
        private AddTicketPage addTicketPage;

        @When("全てのフォームを空にして「登録」ボタンをクリックすると")
        public void 全てのフォームを空にして_登録_ボタンをクリックすると() {
            addTicketPage.submit();
        }

        @Then("チケット登録画面が表示された状態になる")
        public void チケット登録画面が表示された状態になる() throws Exception {
            addTicketPage.isCurrentPage("ゴスペルワークショップ");
        }

        @Then("エラーメッセージが表示される")
        public void エラーメッセージが表示される() {
            assertTrue(addTicketPage.errorAreaExists());
        }

    }
}
