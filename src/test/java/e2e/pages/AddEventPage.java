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

    @FindBy(id = "name")
    private WebElement name;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id ="event_detail")
    private WebElement eventDetail;

    @FindBy(id ="event_start_date")
    private WebElement eventStartDate;

    @FindBy(id = "event_end_date")
    private WebElement eventEndDate;

    public void setName(WebElement name) {
        this.name = name;
    }

    public String getName() { return name.getText(); }

    public void userVisitsAddEventPage() {
        this.goToPage("/event/new");
    }

    public void fillNameField(String name) {
        this.name.sendKeys(name);
    }

    public void fillLocationField(String location) {
        this.location.sendKeys(location);
    }

    public void fillSummaryField(String summary) {
        this.summary.sendKeys(summary);
    }

    public void fillEventDetailField(String eventDetail) {
        this.eventDetail.sendKeys(eventDetail);
    }

    public void fillEventStartDateField(String eventStartDate) {
        this.eventStartDate.sendKeys(eventStartDate);
    }

    public void fillEventEndDateField(String eventEndDate) {
        this.eventEndDate.sendKeys(eventEndDate);
    }
}
