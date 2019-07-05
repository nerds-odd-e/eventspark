package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PreviewPage extends BasePage {

    @FindBy(id = "back-to-home")
    private WebElement backToHomeButton;

    @FindBy(how = How.CSS, using = "#prev")
    private WebElement prevButton;

    @FindBy(id = "next")
    private WebElement nextButton;

    @FindBy(id = "address-preview")
    private WebElement addressPreview;

    @FindBy(id = "body-preview")
    private WebElement bodyPreview;

    @FindBy(id = "subject-preview")
    private WebElement subjectPreview;

    public PreviewPage(WebDriver driver, Environment environment) {
        super(driver, environment);
    }

    public boolean isPreviewNumber(int index) {
        return driver.getCurrentUrl().contains("/preview/" + index);
    }

    public void goToPreviousPreviewPage() {
        prevButton.click();
    }

    public void goToNextPreviewPage() {
        nextButton.click();
    }

    public void goBackToHome() {
        backToHomeButton.click();
    }

    public boolean isNextButtonDisabled() {
        return isDisabled(nextButton);
    }

    public boolean isPrevButtonDisabled() {
        return isDisabled(prevButton);
    }

    public String getAddressPreviewContent() {
        return addressPreview.getText();
    }

    public String getBodyPreviewContent() {
        return bodyPreview.getText();
    }

    public String getSubjectPreviewContent() {
        return subjectPreview.getText();
    }

    private boolean isDisabled(WebElement element) {
        return !element.isEnabled();
    }
}
