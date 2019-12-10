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

    public String getTitleText() {
        return title.getText();
    }

    public String getLocationText() {
        return location.getText();
    }

    public String getCreateUserNameText() {
        return createUserName.getText();
    }

    public void userVisitsEventDetailPage(String eventName) {
        this.goToPage("/event/" + eventName);
    }
}
