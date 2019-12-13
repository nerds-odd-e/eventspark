package e2e.pages;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventDetailForOwnerPage extends BasePage {

    public EventDetailForOwnerPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }
    @FindBy(id = "event_name")
    private WebElement eventName;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "owner")
    private WebElement owner;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "start_date")
    private WebElement startDate;

    @FindBy(id = "end_date")
    private WebElement endDate;

    @FindBy(id = "detail")
    private WebElement detail;

    @FindBy(id = "go_to_add_ticket_page")
    private WebElement goToAddTicketPageButton;

    @FindBy(id = "image")
    private WebElement image;

    @FindBy(className = "ticket_name")
    private List<WebElement> ticketNameList;

    @FindBy(id = "event_url")
    private WebElement eventUrl;

    @FindBy(id = "edit_button")
    private WebElement editButton;

    public String getEventNameText() {
        return eventName.getText();
    }

    public String getLocationText() {
        return location.getText();
    }

    public String getCreateUserNameText() {
        return owner.getText();
    }

    public String getSummaryText() {
        return summary.getText();
    }

    public String getStartDateText() {
        return startDate.getText();
    }

    public String getEndDateText() {
        return endDate.getText();
    }

    public String getDetailText() {
        return detail.getText();
    }

    public void userVisitsEventPreviewPage(String eventName) {
        this.goToPage("/owner/event/" + eventName);
    }

    public void submit() { goToAddTicketPageButton.click(); }

    public String getImageUrl() {
        return image.getAttribute("src");
    }

    public List<String> getTicketNameList() {
        return ticketNameList.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void assertCurrentPage(String eventName) throws UnsupportedEncodingException {
        String currentUrl = URLDecoder.decode(driver.getCurrentUrl(), "UTF-8");
        Assert.assertThat(currentUrl, Matchers.endsWith("/owner/event/" + eventName));
    }

    public String getEventUrlText() {
        return eventUrl.getAttribute("value");
    }

    public String getEditUrl(String eventName) {
        return "http://localhost:" + getPort() + "/owner/event/" + eventName + "/edit";
    }

    public String getEditButtonURL() throws UnsupportedEncodingException {
        return URLDecoder.decode(editButton.getAttribute("href"), "UTF-8");
    }
}
