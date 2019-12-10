package e2e.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventRegisterPage {

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

}
