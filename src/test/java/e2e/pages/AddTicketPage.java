package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AddTicketPage extends BasePage {

    @FindBy(id = "ticket-name")
    private WebElement ticketNameField;

    AddTicketPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToAddTicketPage(String eventName) {
        this.goToPage("/admin/event/"+eventName+"/ticket");
    }

    public void fillTicketName(String ticketName) {
        ticketNameField.sendKeys(ticketName);
    }
}
