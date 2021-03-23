package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EventListPage extends BasePage {

    EventListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToPage() {
        this.goToPage("/event");
    }

}
