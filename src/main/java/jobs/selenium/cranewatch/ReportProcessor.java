package jobs.selenium.cranewatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.ExcelHelper;

class ReportProcessor {

  private static void populateExcelSheet(Item item, Workbook workbook, Sheet sheet, int rowCount) {
    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    // Assumption that projectName_Location is unique
    String id = item.getProjectName() + "_" + item.getLocation();

    if (!recordExists(workbook, sheet, id)) {

      Cell cell = row.createCell(0);
      cell.setCellValue(id);

      cell = row.createCell(1);
      cell.setCellValue(item.getProjectName());

      cell = row.createCell(2);
      cell.setCellValue(item.getLocation());

      cell = row.createCell(3);
      cell.setCellValue(item.getDescription());

      cell = row.createCell(4);
      cell.setCellValue(item.getCost());

      cell = row.createCell(5);
      cell.setCellValue(item.getAccessURL());

      cell = row.createCell(6);
      cell.setCellValue(item.getEstimatedCompletion());

      cell = row.createCell(7);
      cell.setCellValue(item.getOwner());

      cell = row.createCell(8);
      cell.setCellValue(item.getStatus());

      cell = row.createCell(9);
      cell.setCellValue(item.getLatitude());

      ExcelHelper.writeToExcelSheet(workbook, "briq.xlsx");
    }
  }

  static void createReport(Item item, int rowCount) throws IOException {
    FileInputStream file = new FileInputStream(new File("briq.xlsx"));

    // Create Workbook instance holding reference to .xlsx file
    XSSFWorkbook workbook = new XSSFWorkbook(file);

    // Get first/desired sheet from the workbook
    XSSFSheet sheet = workbook.getSheetAt(0);

    populateExcelSheet(item, workbook, sheet, rowCount);

    file.close();
  }

  /*
   * Check if record exists
   * */
  private static boolean recordExists(Workbook workbook, Sheet sheet, String id) {
    for (int i = 1; i < sheet.getLastRowNum(); i++) {
      Cell cell = sheet.getRow(i).getCell(0);
      if (id.equalsIgnoreCase(cell.getStringCellValue())) {
        return true;
      }
    }
    return false;
  }
}
