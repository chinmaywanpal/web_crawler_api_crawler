package jobs.rest;

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

  /*
   * Create individual rows
   * */
  private static void populateExcelSheet(Item item, Workbook workbook, Sheet sheet, int rowCount) {
    Row row = sheet.createRow(rowCount);
    String id = item.getRecordID();

    /*
     * Add new record if record doesn't exist already
     * */
    if (!recordExists(workbook, sheet, id)) {

      Cell cell = row.createCell(0);
      cell.setCellValue(id);

      cell = row.createCell(1);
      cell.setCellValue(item.getStatus());

      cell = row.createCell(2);
      cell.setCellValue(item.getStreetName());

      cell = row.createCell(3);
      cell.setCellValue(item.getZipCode());

      ExcelHelper.writeToExcelSheet(workbook, "briq.xlsx");
    }
  }

  /*
   * Read originally created excel file
   * */
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
    for (int i = 0; i < sheet.getLastRowNum(); i++) {
      Cell cell = sheet.getRow(i).getCell(0);
      if (id.equalsIgnoreCase(cell.getStringCellValue())) {
        return true;
      }
    }
    return false;
  }
}
