package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EventDetailPage extends BasePage {
    public EventDetailPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(id = "title")
    private WebElement title;

    @FindBy(id = "location")
    private WebElement location;

    @FindBy(id = "create_username")
    private WebElement createUserName;

    @FindBy(id = "summary")
    private WebElement summary;

    @FindBy(id = "start_date")
    private WebElement startDate;

    @FindBy(id = "end_date")
    private WebElement endDate;

    @FindBy(id = "detail")
    private WebElement detail;

    public String getTitleText() {
        return title.getText();
    }

    public String getLocationText() {
        return location.getText();
    }

    public String getCreateUserNameText() {
        return createUserName.getText();
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

    public void userVisitsEventDetailPage(String eventName) {
        this.goToPage("/event/" + eventName);
    }
}
