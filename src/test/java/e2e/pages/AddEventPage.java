package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AddEventPage extends BasePage {

    public AddEventPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(id = "name")
    private WebElement name;

    @FindBy(id = "owner")
    private WebElement owner;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "detail")
    private WebElement detail;

    @FindBy(id = "startDateTime")
    private WebElement eventStartDate;

    @FindBy(id = "endDateTime")
    private WebElement eventEndDate;

    @FindBy(id = "add")
    private WebElement addButton;

    public void setName(WebElement name) {
        this.name = name;
    }

    public String getName() {
        return name.getText();
    }

    public void userVisitsAddEventPage() {
        this.goToPage("/owner/event/new");
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

    public void fillDetailField(String detail) {
        this.detail.sendKeys(detail);
    }

    public void fillEventStartDateField(String eventStartDate) {
        this.eventStartDate.sendKeys(eventStartDate);
    }

    public void fillEventEndDateField(String eventEndDate) {
        this.eventEndDate.sendKeys(eventEndDate);
    }

    public void clickAddButton() {
        this.addButton.click();
    }

    public void fillOwnerField(String owner) {
        this.owner.sendKeys(owner);
    }
}
