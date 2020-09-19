import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    WebDriver driver = setProperties();

    @Before
    public void before() {
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    @After
    public void after() throws IOException {
        driver.quit();
        if (System.getProperty("os.name").contains("Windows")) {
            Process process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
            process.destroy();
        }
    }

    private ChromeDriver setProperties() {
        Properties props = new Properties();
        try {
            FileInputStream fin = new FileInputStream("src\\test\\resources\\config\\application.properties");
            props.load(fin);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver", props.getProperty("chromeDriverPath"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized", "disable-infobars", "--no-sandbox", "--no-sandbox-and-elevated",
                "--ignore-certificate-errors");
        return new ChromeDriver(options);
    }
}
