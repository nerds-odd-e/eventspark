package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImportCsvPage extends BasePage {

    @FindBy(id = "csvfile")
    private WebElement fileField;

    @FindBy(id = "import")
    private WebElement importButton;

    @FindBy(how = How.CSS, using = "#error-area")
    private WebElement errorArea;

    public ImportCsvPage(WebDriver driver, Environment environment) {
        super(driver, environment);
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

}
