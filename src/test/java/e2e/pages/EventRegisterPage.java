package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;

public class EventRegisterPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(id = "mail-address")
    private WebElement mailAddress;

    @FindBy(id = "ticket-type")
    private WebElement ticketType;

    @FindBy(id = "ticket-number")
    private WebElement ticketNumber;

    @FindBy(id = "purchase")
    private WebElement purchaseButton;

    EventRegisterPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getMailAddress() {
        return mailAddress.getText();
    }

    public Integer getTicketType() {
        return Integer.parseInt(ticketType.getText());
    }

    public Integer getTicketNumber() {
        return Integer.parseInt(ticketNumber.getText());
    }

    public void purchase() {
        purchaseButton.click();
    }

    public void goToPurchasedPage() {
        this.goToPage("/purchased");
    }


}