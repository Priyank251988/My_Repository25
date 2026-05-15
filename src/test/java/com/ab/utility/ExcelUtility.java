package com.ab.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public static Workbook getWorkbook(String filepath) throws IOException
	{
		Workbook wbobj=null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ext=filepath.split("\\.")[2];
		
		if(ext.equalsIgnoreCase("xlsx"))
		{
			wbobj= new XSSFWorkbook(fis);
		}
		else if(ext.equalsIgnoreCase("xls"))
		{
			wbobj= new HSSFWorkbook(fis);
		}
		
		return wbobj;
		
	}
	
	public static int getLastcolumn(String filepath,String sheetname)
	{
		Workbook wbobj=null;
		try {
			 wbobj=getWorkbook(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet shobj=wbobj.getSheet(sheetname);
		Row rownum=shobj.getRow(0);
		int colnum=rownum.getLastCellNum();
		return colnum;
	}
	
	public static int getLastRownum(String filepath,String sheetname)
	{
		Workbook wbobj=null;
		try {
				
				wbobj=getWorkbook(filepath);
	}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet shobj=wbobj.getSheet(sheetname);
		int rownum=shobj.getLastRowNum();
		return rownum;
	}
	
	
	public static String getColName(String sheetname,String filepath,int colnum,int rownum)
	{
		Workbook wbobj=null;
		try
		{
			wbobj=getWorkbook(filepath);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet shobj=wbobj.getSheet(sheetname);
		Row rowobj=shobj.getRow(rownum);
		Cell celobj=rowobj.getCell(colnum);
		String colname=celobj.getStringCellValue();
		return colname;		
		
	}
	
	public static HashMap<String,String> getExcelData(String filepath,String sheetname,int rownum,int colnamerownum)
	{
		Workbook wbobj=null;
		String data=null;
		HashMap<String,String>hmobj=new HashMap<String,String>();
		
		try {
			wbobj=getWorkbook(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet shobj=wbobj.getSheet(sheetname);
		int lstrownum=getLastRownum(filepath, sheetname);
		int lstcolnum=getLastcolumn(filepath, sheetname);
		Row rowobj=shobj.getRow(rownum);
		for(int i=0;i<lstcolnum;i++)
		{
			Cell celobj=rowobj.getCell(i);
			String colname=getColName(sheetname, filepath, i, colnamerownum);
			CellType cltyp=celobj.getCellType();
			if(cltyp==CellType.STRING)
			{
				data=celobj.getStringCellValue();
				
			}
			else if(cltyp==CellType.NUMERIC)
			{
				Double doblval=celobj.getNumericCellValue();
				Integer intval=doblval.intValue();
				data=intval.toString();
			}
			hmobj.put(colname, data);
		}
		
		return hmobj;
		
	}
		

}
