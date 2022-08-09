import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class Mail {

    WebDriver driver;
    private static final String LOGIN = "testotestovich7@gmail.com";
    private static final String PASS = "testpassword1029";

    public void waitForVisibility(long timeToWait, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForPageLoadComplete(long timeToWait) {
        new WebDriverWait(driver, Duration.ofSeconds(timeToWait)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @BeforeClass
    public static void setUp () {
        chromedriver().setup();
    }

    @Before
    public void beforeTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://www.google.com/");
    }

    @Test
    public void sendMail() {
        driver.get("https://mail.google.com/");

        WebElement loginButtonInitial = driver.findElement(By.linkText("Войти"));
        loginButtonInitial.click();

        WebElement mailInput = driver.findElement(By.name("identifier"));
        WebElement mailConfirm = driver.findElement(By.id("identifierNext"));
        mailInput.sendKeys(LOGIN);
        mailConfirm.click();

        WebElement passInput = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.name("password")));
        WebElement passConfirm = driver.findElement(By.id("passwordNext"));
        passInput.sendKeys(PASS);
        passConfirm.click();

        waitForPageLoadComplete(20);

        WebElement composeMail = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='z0']")));
        composeMail.click();

    }

}
