package e2e;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import javax.annotation.PreDestroy;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;

@ComponentScan(basePackages = {"e2e"})
public class Config {
    private WebDriver driver;
    private GreenMail greenMail;

    public Config() {
        WebDriverManager.chromedriver().setup();
        greenMail = new GreenMail(new ServerSetup(2500, "localhost", PROTOCOL_SMTP)).withConfiguration(new GreenMailConfiguration().withDisabledAuthentication());
    }

    @Bean
    public WebDriver getDriver() {
        if(driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    @Bean
    public GreenMail getGreenMail() {
        greenMail.start();
        return greenMail;
    }

    @PreDestroy
    public void onApplicationShutdown() {
        driver.quit();
        greenMail.stop();
    }
}
