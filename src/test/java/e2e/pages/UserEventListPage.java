package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserEventListPage extends BasePage {

    UserEventListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToUserEventListPage() {
        this.goToPage("/event");
    }

    public int countRows() {
        return driver.findElements(By.className("event-element")).size();
    }
}
