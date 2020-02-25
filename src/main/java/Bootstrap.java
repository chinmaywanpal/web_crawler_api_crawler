import java.io.File;
import java.io.IOException;
import java.util.Timer;
import jobs.rest.SFGovJob;
import jobs.selenium.cranewatch.CraneWatchJob;
import org.apache.poi.ss.usermodel.Workbook;
import utils.ExcelHelper;

public class Bootstrap {

  public static void main(String args[]) throws IOException {
    // One time Setup - Create excel document
    String fileName = "briq.xlsx";
    Workbook wb = ExcelHelper.setupWorkbook();
    ExcelHelper.setUpExcelSheet(wb, "sheet1");

    File excelFile = new File(fileName);
    // if file already exists this will do nothing
    excelFile.createNewFile();

    // create valid excel file
    ExcelHelper.writeToExcelSheet(wb, fileName);

    // Schedule craneWatchJob that runs once a week
    Timer timer = new Timer();
    CraneWatchJob craneWatchJob = new CraneWatchJob();
    timer.scheduleAtFixedRate(craneWatchJob, 0, 604800000);

    // Schedule JSON job that runs once a day
    SFGovJob sfGovJob = new SFGovJob();
    timer.scheduleAtFixedRate(sfGovJob, 5000000, 86400000);
  }
}
