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

    public void goToContactPage() {
        driver.get("http://localhost:" + getPort() + "/contact-list");
    }

}
