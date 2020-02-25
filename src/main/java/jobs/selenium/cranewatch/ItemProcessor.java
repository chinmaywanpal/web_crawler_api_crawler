package jobs.selenium.cranewatch;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.WebDriverUtils;

class ItemProcessor {
  private final By[] SELECTOR_PIN_LOCATION = new By[] {By.cssSelector("#map_gc image")};
  private final By SELECTOR_DIALOG = By.cssSelector(".esriPopupVisible");
  private final By SELECTOR_TITLE_BAR_NEXT = By.cssSelector(".titleButton.next");
  private final By SELECTOR_TITLE_BAR_NEXT_HIDDEN = By.cssSelector(".titleButton.next.hidden");
  private final By SELECTOR_TITLE_BAR_CLOSE = By.cssSelector(".titleButton.close");
  private final By SELECTOR_MAP_PINS = By.cssSelector("#map_gc image");
  private final WebDriver webDriver;
  private int counter = 0;

  ItemProcessor(WebDriver webDriver) throws IOException, InterruptedException {
    this.webDriver = webDriver;
    /*
     * Get all pins from maps page
     * */
    List<WebElement> pins = getAllPins();
    for (WebElement pin : pins) {
      WebDriverUtils.waitForElementToBeVisible(SELECTOR_MAP_PINS);
      /*
       * SVG needs actionBuilder for interactions
       * */
      Actions builder = new Actions(webDriver);
      builder.click(pin).build().perform();
      Thread.sleep(2000);
      if (webDriver.findElements(SELECTOR_DIALOG).size() > 0) {
        Item item = ExtractInformationFromDialog();
        ReportProcessor.createReport(item, counter);
        counter++;
        /*
         * Some pins contain more than one report.
         * Iterate over all reports and save them
         * */
        while (webDriver.findElements(SELECTOR_TITLE_BAR_NEXT_HIDDEN).size() == 0) {
          item = ExtractInformationFromDialog();
          ReportProcessor.createReport(item, counter);
          counter++;
          webDriver.findElement(SELECTOR_TITLE_BAR_NEXT).click();
        }
        /*
         * Close the dialog
         * */
        webDriver.findElement(SELECTOR_TITLE_BAR_CLOSE).click();
      }
    }
  }

  /*
   * Extract information for each item
   * */
  private Item ExtractInformationFromDialog() {
    final By LOCATION_XPATH = By.xpath("//td[contains(text(),'Location')]/following-sibling::td");
    final By LATITUDE_XPATH = By.xpath("//td[contains(text(),'Latitude')]/following-sibling::td");
    final By PROJECT_NAME_CSS = By.cssSelector(".esriPopupWrapper .mainSection .header");
    final By DESCRIPTION_XPATH =
        By.xpath("//td[contains(text(),'Description')]/following-sibling::td");
    final By OWNER_XPATH = By.xpath("//td[contains(text(),'Owner')]/following-sibling::td");
    final By COST_XPATH = By.xpath("//td[contains(text(),'Cost')]/following-sibling::td");
    final By STATUS_XPATH = By.xpath("//td[contains(text(),'Status')]/following-sibling::td");
    final By COMPLETION_DATE_XPATH =
        By.xpath("//td[contains(text(),'Estimated Completion')]/following-sibling::td");
    final By URL_XPATH = By.xpath("//td[contains(text(),'URL To Story')]/following-sibling::td/a");

    String location = webDriver.findElement(LOCATION_XPATH).getText();
    /*
     * Latitude field doesn't exist for every pin,
     * ignore if field is not preset and proceed
     * */
    String latitude;
    try {
      latitude = webDriver.findElement(LATITUDE_XPATH).getText();
    } catch (Exception e) {
      latitude = null;
    }
    String projectName = webDriver.findElement(PROJECT_NAME_CSS).getText();
    String description = webDriver.findElement(DESCRIPTION_XPATH).getText();
    String owner = webDriver.findElement(OWNER_XPATH).getText();
    String cost = webDriver.findElement(COST_XPATH).getText();
    String status = webDriver.findElement(STATUS_XPATH).getText();
    String completion = webDriver.findElement(COMPLETION_DATE_XPATH).getText();
    String accessURL;
    try {
      accessURL = webDriver.findElement(URL_XPATH).getAttribute("href");
    } catch (Exception e) {
      accessURL = null;
    }
    return new Item(
        location, latitude, projectName, description, owner, cost, status, completion, accessURL);
  }

  private List<WebElement> getAllPins() {
    return webDriver.findElements(SELECTOR_PIN_LOCATION[0]);
  }
}
