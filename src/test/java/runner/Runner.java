package test.java.runner;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features",
        glue = "main/java/steps",
        monochrome = true,
        strict = true)

public class Runner extends AbstractTestNGCucumberTests {
    public static WebDriver driver;
    public static JavascriptExecutor js;
    public static boolean condition;
    public static Actions actions;

    @Parameters("browser")
    @BeforeTest
    public void setup(String browser) throws Exception {

        System.out.println(new Date() + " : running for browser ......" + browser);
        String path = System.getProperty("user.dir");
        String osName = System.getProperty("os.name");
        System.out.println("path -> " + path);
        System.out.println("osName -> " + osName);

        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", path +"/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {

            if (osName.contains("Mac")) {
                System.setProperty("webdriver.chrome.driver", path + "/chromedriver");
                driver = new ChromeDriver();
            }else{
                System.setProperty("webdriver.chrome.driver", path + "/chromedriver_windows.exe");
                driver = new ChromeDriver();
            }

        } else if (browser.equalsIgnoreCase("MobileChrome")) {

            System.setProperty("webdriver.chrome.driver", path +"./chromedriver_windows.exe");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", "Samsung Galay A9 Pro");
            caps.setCapability("udid", "c55e5ac2");
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "8.0.0");
            caps.setCapability("noReset", true);
            caps.setCapability("automationName", "UiAutomator2");
            caps.setCapability("browserName", "Chrome");
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        } else {
            throw new Exception("Browser is not correct");
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @AfterTest
    public void teardown() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

}
