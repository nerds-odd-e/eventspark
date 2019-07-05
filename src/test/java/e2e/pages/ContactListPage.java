package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ContactListPage extends BasePage {
    ContactListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    @FindBy(how = How.CSS, using = "#address")
    private WebElement address;

    @FindBy(id = "contact-list-title")
    private WebElement title;

    @FindBy(how = How.CSS, using = "#success-area")
    private WebElement successArea;

    @FindBy(id = "address-list")
    private WebElement addressList;

    public void goToContactPage() {
        driver.get("http://localhost:" + getPort() + "/contact-list");
    }

    public String getTitleText() {
        return title.getText();
    }

    public String getSuccessText() {
        return successArea.getText();
    }

    public String getAddressListText() {
        return addressList.getText();
    }

}
