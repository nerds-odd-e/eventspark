package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class EventDetailPage extends BasePage {
    public EventDetailPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(id = "event_name")
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

    @FindBy(id = "register_button")
    private WebElement registerButton;

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
}
