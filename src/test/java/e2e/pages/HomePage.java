package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class HomePage {

    private WebDriver driver;
    private Environment environment;

    public HomePage(WebDriver driver, Environment environment) {
        this.driver = driver;
        this.environment = environment;

        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CSS, using = "#address")
    private WebElement address;

    @FindBy(how = How.CSS, using = "#subject")
    private WebElement subject;

    @FindBy(how = How.CSS, using = "#body")
    private WebElement body;


    private String getPort() {
        return environment.getProperty("local.server.port");
    }

    public void userVisitsSendPage() {
        driver.get("http://localhost:" + getPort());
    }

    public String getInputAddressText() {
        return getValue(address);
    }

    public String getInputSubjectText() {
        return getValue(subject);
    }

    public String getInputBodyText() {
        return getValue(body);
    }

    private String getValue(WebElement webElement) {
        return webElement.getAttribute("value");
    }
}
