package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
  private static final long TIME_OUT_IN_SECONDS = 120L;
  private static ChromeDriver webDriver;

  public static WebDriver launchWebDriver() {
    System.setProperty("webdriver.chrome.driver", "src/main/java/utils/chromedriver");

    webDriver = new ChromeDriver();
    return webDriver;
  }

  public static void waitForElementToBeVisible(By selector) {
    getWebDriverWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(selector, 0));
  }

  private static WebDriverWait getWebDriverWait() {
    return new WebDriverWait(webDriver, TIME_OUT_IN_SECONDS);
  }
}
