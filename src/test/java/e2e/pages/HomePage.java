package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class HomePage extends BasePage {

    public HomePage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public boolean isCurrentPage() {
        return driver.getCurrentUrl().contains("/home");
    }

    @FindBy(how = How.CSS, using = "#address")
    private WebElement address;

    @FindBy(how = How.CSS, using = "#subject")
    private WebElement subject;

    @FindBy(how = How.CSS, using = "#body")
    private WebElement body;

    @FindBy(how = How.CSS, using = "#preview")
    private WebElement previewButton;

    @FindBy(how = How.CSS, using = "#send")
    private WebElement sendButton;

    @FindBy(how = How.CSS, using = "#error-area")
    private WebElement errorArea;

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

    public void goToPreview() {
        previewButton.click();
    }

    private String getValue(WebElement webElement) {
        return webElement.getAttribute("value");
    }

    public void fillAddressField(String input) {
        address.sendKeys(input);
    }

    public void fillSubjectField(String input) {
        subject.sendKeys(input);
    }

    public void fillBodyField(String input) {
        body.sendKeys(input);
    }

    public void sendEmail() {
        sendButton.click();
    }

    public String getErrorText() {
        return errorArea.getText();
    }

    public boolean errorAreaExists() {
        return driver.findElements(By.id("error-area")).size() != 0;
    }
}
