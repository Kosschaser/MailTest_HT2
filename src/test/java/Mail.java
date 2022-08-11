import org.junit.Assert;
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
import java.util.List;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class Mail {

    WebDriver driver;
    private static final String LOGIN = "testotestovich7@gmail.com";
    private static final String PASS = "testpassword1029";
    private static final String MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private static final String MAIL_TO = "Dmytromailto@gmail.com";


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

        WebElement recipient = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//textarea[@name='to']"))));
        WebElement messageField = driver.findElement(By.id(":9n"));
        WebElement sendMail = driver.findElement(By.id(":87"));

        recipient.sendKeys(MAIL_TO);
        messageField.sendKeys(MESSAGE);
        sendMail.click();

        WebElement sentList = driver.findElement(By.id(":4a"));
        sentList.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("#sent"));
        List<WebElement> messagesSent = driver.findElements((By.xpath("//tr[@class='zA yO']")));

        Assert.assertTrue(!messagesSent.isEmpty());

    }

}
