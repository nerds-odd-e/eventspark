package e2e.pages;

import e2e.ContactListSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactListPage extends BasePage {


    @FindBy(how = How.CSS, using = "#error-area")
    private WebElement errorArea;

    @FindBy(id = "address-list")
    private WebElement addressList;

    @FindBy(how = How.CSS, using = "#create-email")
    private WebElement createEmailButton;

    @FindBy(how = How.CSS, using = "#address")
    private WebElement addressField;

    @FindBy(id = "contact-list-title")
    private WebElement title;

    @FindBy(how = How.CSS, using = "#success-area")
    private WebElement successArea;

    @FindBy(how = How.CSS, using = "#name")
    private WebElement nameField;

    @FindBy(how = How.CSS, using = "#add")
    private WebElement addButton;

    @FindBy(how = How.NAME, using = "mailAddress")
    private List<WebElement> mailAddressCheckboxes;

    @FindBy(how = How.ID, using = "all")
    private WebElement selectAllContactsCheckbox;

    @FindBy(how = How.ID, using = "import-csv")
    private WebElement clickImportCsvButton;

    ContactListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

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

    public void fillAddressField(String input) {
        addressField.sendKeys(input);
    }

    public void fillNameField(String input) {
        nameField.sendKeys(input);
    }

    public String getAddressFieldContents() {
        return addressField.getAttribute("value");
    }

    public void clickAddButton() {
        addButton.click();
    }

    public String getErrorAreaText() {
        return errorArea.getText();
    }

    public boolean addressListContains(String query) {
        return addressList.getText().contains(query);
    }

    public void clickCreateEmailButton() {
        createEmailButton.click();
    }

    public void uncheckAllMailAddresses() {
        mailAddressCheckboxes.forEach((checkbox) -> {
            if (checkbox.isSelected()) checkbox.click();
        });
    }

    public void clickSelectAllCheckbox() {
        selectAllContactsCheckbox.click();
    }

    public void clickImportCsvButton() {
        clickImportCsvButton.click();
    }

    public void selectContactWithAddress(String address) {
        driver.findElement(By.cssSelector("input[value='" + address + "']")).click();
    }
}
