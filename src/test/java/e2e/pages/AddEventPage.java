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

    @FindBy(id = "event-name")
    private WebElement eventName;

    public void setEventName(WebElement eventName) {
        this.eventName = eventName;
    }

    public String getEventName() { return eventName.getText(); }

    public void userVisitsAddEventPage() {
        this.goToPage("/event/new");
    }


}
