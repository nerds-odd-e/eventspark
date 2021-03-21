package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactListPage extends BasePage {


    @FindBy(id = "error-area")
    private WebElement errorArea;

    @FindBy(id = "address-list")
    private WebElement addressList;

    @FindBy(id = "create-email")
    private WebElement createEmailButton;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "contact-list-title")
    private WebElement title;

    @FindBy(id = "success-area")
    private WebElement successArea;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "add")
    private WebElement addButton;

    @FindBy(name = "mailAddress")
    private List<WebElement> mailAddressCheckboxes;

    @FindBy(id = "all")
    private WebElement selectAllContactsCheckbox;

    @FindBy(id = "import-csv")
    private WebElement clickImportCsvButton;

    ContactListPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void goToContactPage() {
        this.goToPage("/contact-list");
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
