package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.core.env.Environment;

public abstract class BasePage {

    WebDriver driver;
    Environment environment;

    BasePage(WebDriver driver, Environment environment) {
        this.driver = driver;
        this.environment = environment;

        PageFactory.initElements(driver, this);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
