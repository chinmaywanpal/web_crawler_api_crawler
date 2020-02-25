package utils;

import java.io.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

  public static Sheet setUpExcelSheet(Workbook workbook, String sheetName) {
    return workbook.createSheet(sheetName);
  }

  public static Workbook setupWorkbook() {
    return new XSSFWorkbook();
  }

  public static void writeToExcelSheet(Workbook workbook, String filepath) {
    // Write the output to a file
    FileOutputStream fileOut = null;
    try {
      File yourFile = new File(filepath);
      yourFile.createNewFile(); // if file already exists will do nothing
      fileOut = new FileOutputStream(yourFile, false);
      workbook.write(fileOut);
      fileOut.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
