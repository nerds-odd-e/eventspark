package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImportCsvPage extends BasePage {

    @FindBy(id = "csvfile")
    private WebElement fileField;

    @FindBy(id = "import")
    private WebElement importButton;

    @FindBy(id = "forceButton")
    private WebElement forceButton;

    @FindBy(how = How.CSS, using = "#error-area")
    private WebElement errorArea;

    public ImportCsvPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public void userVisitsContactPage() {
        this.goToPage("/import-csv");
    }

    public boolean isImportCsvPage() {
        return this.getCurrentUrl().contains("import-csv");
    }

    public void selectFile(File uploadFile) {
        fileField.sendKeys(uploadFile.getAbsolutePath());
    }

    public void clickImport() {
        importButton.click();
    }

    public String getErrorText() {
        return errorArea.getText();
    }

    public void waitAndClickForceButton() {
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.elementToBeClickable(forceButton));
        forceButton.click();
    }
}
