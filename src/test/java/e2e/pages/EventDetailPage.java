package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@Component
public class EventDetailPage extends BasePage {
    public EventDetailPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(id = "name")
    private WebElement eventName;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "owner")
    private WebElement ownner;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "start_date")
    private WebElement startDate;

    @FindBy(id = "end_date")
    private WebElement endDate;

    @FindBy(id = "detail")
    private WebElement detail;

    @FindBy(id = "ticket_name")
    private WebElement ticketName;

    @FindBy(id = "ticket_list")
    private WebElement ticketList;

    @FindBy(id = "max_ticket")
    private WebElement maxTicket;

    @FindBy(id = "register_button")
    private WebElement registerButton;

    @FindBy(id = "success_message")
    private WebElement successMessage;

    @FindBy(id = "ticket_message")
    private WebElement ticketMessage;

    public String getEventNameText() {
        return eventName.getText();
    }

    public String getLocationText() {
        return location.getText();
    }

    public String getCreateUserNameText() {
        return ownner.getText();
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

    public String getRegisterButtonURL() throws UnsupportedEncodingException {
        return URLDecoder.decode(registerButton.getAttribute("href"), "UTF-8");
    }

    public String getRegisterURL() {
        return "http://localhost:" + getPort() + "/register_form/";
    }

    public void userVisitsEventDetailPage(String eventName) {
        this.goToPage("/event/" + eventName);
    }

    public String getMaxTicketText() {
        return maxTicket.getText();
    }

    public String getTicketNameText() {
        return ticketName.getText();
    }

    public String getTicketMessageText() {
        return ticketMessage.getText();
    }

    public boolean getTicketList() {
        return ticketList.isDisplayed();
    }

    public void assertCurrentPage(String eventName) throws UnsupportedEncodingException {
        String expected = "http://localhost:" + getPort() + "/event/" + URLEncoder.encode(eventName, StandardCharsets.UTF_8.name());
        assertThat(driver.getCurrentUrl(), equalTo(expected));
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }
}
