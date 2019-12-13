package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
    @FindBy(id = "error-area")
    private WebElement errorArea;

    AddTicketPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToAddTicketPage(String eventName) {
        try {
            String encoded = URLEncoder.encode(eventName, "UTF-8");
            this.goToPage("/owner/event/" + encoded + "/ticket");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
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

    public boolean isCurrentPage(String eventName) throws UnsupportedEncodingException {
        String currentUrl = URLDecoder.decode(driver.getCurrentUrl(), "UTF-8");
        boolean contains = currentUrl.contains("/owner/event/" + eventName + "/ticket");
        return contains;
    }


    public String getErrorText() {
        return errorArea.getText();
    }

    public boolean errorAreaExists() {
        return driver.findElements(By.id("error-area")).size() != 0;
    }

}
