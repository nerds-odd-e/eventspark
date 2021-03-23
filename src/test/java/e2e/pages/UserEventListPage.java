package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserEventListPage extends BasePage {

    UserEventListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public String eventTitle(int index){
        return driver.findElements(By.className("event-title")).get(index).getText();
    }

    public String eventVenue(int index){
        return driver.findElements(By.className("event-venue")).get(index).getText();
    }

    public String eventSummary(int index){
        return driver.findElements(By.className("event-summary")).get(index).getText();
    }

    public String eventStartDateTime(int index){
        return driver.findElements(By.className("event-start-datetime")).get(index).getText();
    }

    public String eventTicketStatus(int index){
        return driver.findElements(By.className("event-ticket-status")).get(index).getText();
    }

    public void goToUserEventListPage() {
        this.goToPage("/event");
    }

    public int countRows() {
        return driver.findElements(By.className("event-element")).size();
    }

    public void click(String id) {
        driver.findElement(By.id(id)).click();
    }

}
