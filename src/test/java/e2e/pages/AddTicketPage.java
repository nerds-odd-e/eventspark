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
    @FindBy(id = "ticket-price")
    private WebElement ticketPriceField;
    @FindBy(id = "ticket-total")
    private WebElement ticketTotalField;
    @FindBy(id = "ticket-limit")
    private WebElement ticketLimitField;
    @FindBy(id = "register")
    private WebElement registerButton;

    AddTicketPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToAddTicketPage(String eventName) {
        this.goToPage("/admin/event/" + eventName + "/ticket");
    }

    public void fillTicketName(String ticketName) {
        ticketNameField.sendKeys(ticketName);
    }

    public void fillTicketPrice(String price) {
        ticketPriceField.sendKeys(price);
    }

    public void fillTicketTotal(String total) {
        ticketTotalField.sendKeys(total);
    }

    public void fillTicketLimit(String limit) {
        ticketLimitField.sendKeys(limit);
    }

    public void submit() {
        registerButton.submit();
    }
}
