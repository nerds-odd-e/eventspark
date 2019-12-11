package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AddEventPage extends BasePage{

    public AddEventPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(id = "event_name")
    private WebElement eventName;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "event_date")
    private WebElement eventDate;

    public void setEventName(WebElement eventName) {
        this.eventName = eventName;
    }

    public String getEventName() { return eventName.getText(); }

    public void userVisitsAddEventPage() {
        this.goToPage("/event/new");
    }

    public void fillEventNameField(String eventName) {
        this.eventName.sendKeys(eventName);
    }

    public void fillLocationField(String location) {
        this.location.sendKeys(location);
    }

    public void fillSummaryField(String summary) {
        this.summary.sendKeys(summary);
    }

    public void fillEventDateField(String eventDate) {
        this.eventDate.sendKeys(eventDate);
    }
}
