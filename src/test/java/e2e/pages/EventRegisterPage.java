package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EventRegisterPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstName;

    @FindBy(id = "last-name")
    private WebElement lastName;

    @FindBy(id = "company-name")
    private WebElement companyName;

    @FindBy(id = "mail-address")
    private WebElement mailAddress;

    @FindBy(id = "ticket-type")
    private WebElement ticketType;

    @FindBy(id = "ticket-count")
    private WebElement ticketCount;

    @FindBy(id = "purchase")
    private WebElement purchaseButton;

    @FindBy(id = "error-area")
    private WebElement errorArea;

    EventRegisterPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getMailAddress() {
        return mailAddress.getText();
    }

    public Integer getTicketType() {
        return Integer.parseInt(ticketType.getText());
    }

    public Integer getticketCount() {
        return Integer.parseInt(ticketCount.getText());
    }

    public void purchase() {
        purchaseButton.click();
    }

    public void goToPurchasedPage() {
        this.goToPage("/purchased");
    }

    public void goToEventRegisterPage() { this.goToPage("/register_form/ゴスペルワークショップ"); }

    public void fillFirstName(String input) {
        firstName.sendKeys(input);
    }

    public void fillLastName(String input) {
        lastName.sendKeys(input);
    }

    public void fillCompanyName(String input) {
        companyName.sendKeys(input);
    }
    public void fillMailAddress(String input) {
        mailAddress.sendKeys(input);
    }

    public void fillTicketCount(String input) {
        ticketCount.sendKeys(input);
    }

    public void fillTicketType(String input) {
        ticketType.sendKeys(input);
    }


    public String getErrorText() {
        return errorArea.getText();
    }
}
