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

    public String getTitleText() {
        return title.getText();
    }

    @FindBy(id = "location")
    private WebElement location;

    public String getLocationText() {
        return location.getText();
    }


    public void setTitle(WebElement title) {
        this.title = title;
    }

    public void userVisitsEventDetailPage(String eventName) {
        this.goToPage("/event/" + eventName);
    }
}
