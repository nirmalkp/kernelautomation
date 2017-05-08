package utility;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;



import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils extends Constant {

	@SuppressWarnings("deprecation")
	public static Object[][] ReadExcelData(int SheetNo) throws Exception {   
		String FilePath = File_TestData;
		String[][] tabArray = null;

		try {


			int totalCols=0;
			int totalRows=0;
			int ci,cj;
			FileInputStream excelFile = new FileInputStream(new File(FilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(SheetNo);
			Iterator<Row> iterator = datatypeSheet.iterator();



			if(iterator.hasNext())
			{

				Row currentRow = iterator.next();
				totalCols = currentRow.getPhysicalNumberOfCells();
				totalRows = datatypeSheet.getLastRowNum();
				tabArray=new String[totalRows][totalCols];
				ci=0;
				while (iterator.hasNext()) {

					currentRow = iterator.next();
					totalCols = currentRow.getPhysicalNumberOfCells();
					totalRows = datatypeSheet.getLastRowNum();
					Iterator<Cell> cellIterator = currentRow.iterator();

					cj=0;
					while (cellIterator.hasNext()) {


						Cell currentCell = cellIterator.next();


						//getCellTypeEnum shown as deprecated for version 3.15
						//getCellTypeEnum ill be renamed to getCellType starting from version 4.0
						try {
							if (currentCell.getCellTypeEnum() == CellType.STRING) {
								//System.out.print(currentCell.getStringCellValue() + "--");
								tabArray[ci][cj++]=currentCell.getStringCellValue();
							} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
								// System.out.print(currentCell.getNumericCellValue() + "--");
								tabArray[ci][cj++]=NumberToTextConverter.toText(currentCell.getNumericCellValue());
							}
						} catch (Exception e) {
							System.out.println(e);
						}


					}
					ci++;
					System.out.println();
					workbook.close();
				}
			}


		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*for (Object[] datatype : tabArray) {

			int colNum = 0;
			for (Object field : datatype) {

				if (field instanceof String) {
					System.out.print(field + "--");
				}
			}
			System.out.println("--");
		}
*/
		return(tabArray);
	}
}