package jobs.selenium.cranewatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebDriverUtils;

public class PageProcessor {
  private ArrayList<String> pagesToCrawl = new ArrayList<>();
  private final By[] SELECTOR_VIEW_MAP_BUTTON =
      new By[] {By.cssSelector(".hidden--xs[data-ct=\"OPT: View Map Asset\"]")};
  private final String SELECTOR_IFRAME = ".cw-map";
  private final String SELECTOR_MAP_PINS = "#map_gc image";

  public PageProcessor() throws InterruptedException, IOException {
    populatePages();
    navigateToMap();
  }

  /*
   * Add pages here
   * */
  public void populatePages() {
    pagesToCrawl.add("https://www.bizjournals.com/milwaukee/feature/crane-watch");
    pagesToCrawl.add("https://www.bizjournals.com/seattle/feature/crane-watch");
  }

  public void navigateToMap() throws InterruptedException, IOException {
    WebDriver webDriver = WebDriverUtils.launchWebDriver();
    for (String page : pagesToCrawl) {
      webDriver.navigate().to(page);
      WebDriverUtils.waitForElementToBeVisible(SELECTOR_VIEW_MAP_BUTTON[0]);
      clickOnViewMapButton(webDriver);
      webDriver.switchTo().defaultContent(); // get outside all iFrames
      Thread.sleep(1000);
      // Dialog sporadically doesn't appear if pin is not in the view
      ((JavascriptExecutor) webDriver).executeScript("scrollBy(0, 500);");
      Thread.sleep(1000);
      webDriver.switchTo().frame(webDriver.findElement(By.cssSelector(SELECTOR_IFRAME)));
      WebDriverUtils.waitForElementToBeVisible(By.cssSelector(SELECTOR_MAP_PINS));
      new ItemProcessor(webDriver);
    }
  }

  private void clickOnViewMapButton(WebDriver webDriver) {
    Arrays.stream(SELECTOR_VIEW_MAP_BUTTON)
        .map(webDriver::findElements)
        .flatMap(List::stream)
        .forEach(WebElement::click);
  }
}
