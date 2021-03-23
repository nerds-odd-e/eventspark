package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EventNewPage extends BasePage {
    public EventNewPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }
    @FindBy(id = "name")
    private WebElement name;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "owner")
    private WebElement owner;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "startDateTime")
    private WebElement startDate;

    @FindBy(id = "endDateTime")
    private WebElement endDate;

    @FindBy(id = "detail")
    private WebElement detail;

    @FindBy(id = "imagePath")
    private WebElement image;

    @FindBy(id = "add")
    private WebElement submitButton;


    public void goToPage() {
        this.goToPage("/owner/event/new");
    }

    public void fillNameField(String input) {
        name.sendKeys(input);
    }

    public void fillLocationField(String input) {
        location.sendKeys(input);
    }

    public void fillOwnerField(String input) {
        owner.sendKeys(input);
    }

    public void fillSummaryField(String input) {
        summary.sendKeys(input);
    }

    public void fillStartDateField(String input) {
        if (input == null) {
            return;
        }
        startDate.sendKeys(input);
    }

    public void fillEndDateField(String input) {
        endDate.sendKeys(input);
    }

    public void fillDetailField(String input) {
        detail.sendKeys(input);
    }

    public void fillImageField(String input) {
        image.sendKeys(input);
    }

    public void submit() { submitButton.click(); }

}
