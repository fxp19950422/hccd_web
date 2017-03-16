package cn.sportsdata.webapp.youth.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static List<Integer> getNumbers(List<Object> list, String[] tempColumnNames) {
		List<Integer> result = new ArrayList<Integer>();
		if (list != null && list.size() > 0) {
			for (int j = 0; j < tempColumnNames.length; ++j) {
				result.add(j, -1);
				String columnName = tempColumnNames[j];
				for (int i = 0; i < list.size(); ++i) {
					String excelColumnName = list.get(i).toString();
					if (columnName.equalsIgnoreCase(excelColumnName)) {
						result.set(j, i);
					}
				}
			}
		}
		return result;
	}
	
	public static List<List<Object>> parseExcel(File file) {
		String fileName = file.getName().toLowerCase();
		
		if (fileName.endsWith(".xls")) {
			return parseExcel2003(file);
		} else if(fileName.endsWith(".xlsx")) {
			return parseExcel2007(file);
		}

		return null;
	}

	private static List<List<Object>> parseExcel2007(File file) {
		OPCPackage pkg = null;
		
		try {
			pkg = OPCPackage.openOrCreate(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return null;
		}
		
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(pkg);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		List<List<Object>> list = new ArrayList<List<Object>>();
		XSSFSheet sheet = wb.getSheetAt(0);

		int rowStart = sheet.getFirstRowNum();
		int rowEnd = sheet.getLastRowNum();
		for (int rowNum = rowStart; rowNum <= rowEnd; ++rowNum) {
			Row r = sheet.getRow(rowNum);
			
			if (r == null) {
				continue;
			}
			
			List<Object> rowList = new ArrayList<Object>();
			int lastColumn = r.getLastCellNum();
			
			boolean isBlankRow = true;
			for (int cn = 0; cn < lastColumn; ++cn) {
				Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
				if (cell == null) {
					rowList.add("");
				} else {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						rowList.add(String.format("%.0f", cell.getNumericCellValue()).trim());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						rowList.add(cell.getStringCellValue().trim());
					}
					
					isBlankRow = false;
				}
			}
			
			if(!isBlankRow) {
				list.add(rowList);
			}
		}

		return list;
	}

	private static List<List<Object>> parseExcel2003(File file) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		
		try {
			InputStream input = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(input);

			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);

			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();
			for (int rowNum = rowStart; rowNum <= rowEnd; ++rowNum) {
				Row r = sheet.getRow(rowNum);
				
				if (r == null) {
					continue;
				}
				
				List<Object> rowList = new ArrayList<Object>();
				int lastColumn = r.getLastCellNum();
				
				boolean isBlankRow = true;
				for (int cn = 0; cn < lastColumn; ++cn) {
					Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (cell == null) {
						rowList.add("");
					} else {
						if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							rowList.add(String.format("%.0f", cell.getNumericCellValue()).trim());
						} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
							rowList.add(cell.getStringCellValue().trim());
						}
						
						isBlankRow = false;
					}
				}
				
				if(!isBlankRow) {
					list.add(rowList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
