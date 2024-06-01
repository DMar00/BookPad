package sistema;// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class TestSuiteCommentTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void tCCOM2() {
    driver.get("http://localhost:8080/BookPad_war/");
    driver.manage().window().setSize(new Dimension(1552, 832));
    driver.findElement(By.linkText("Accedi")).click();
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys("anna@gmail.com");
    driver.findElement(By.id("paswd")).click();
    driver.findElement(By.id("paswd")).sendKeys("Anna2000!!");
    driver.findElement(By.id("paswd")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Leggi")).click();
    driver.findElement(By.id("txt-commment")).click();
    {
      WebElement element = driver.findElement(By.id("list-comments"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("list-comments"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("list-comments"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("txt-commment")).click();
    driver.findElement(By.id("add-comment")).click();
    vars.put("count", driver.findElement(By.cssSelector("#count-ch-com > span")).getText());
    vars.put("countInt", js.executeScript("return parseInt(arguments[0]);", vars.get("count")));
    vars.put("result", js.executeScript("return arguments[0]>0", vars.get("countInt")));
    assertEquals(vars.get("result").toString(), "false");
  }
}