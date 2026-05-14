package com.ab.utility;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Data_Util {
	
	
	public static Object[][] getData(String testCase,ExcelReader excel)
	{
		System.out.println(Constants.DataSheet);
		int rowcnt=excel.getRowCount(Constants.DataSheet);
		System.out.println("Total rows in excel sheet: "+rowcnt);
		String testname=testCase;
		int testcaserownum=1;
		for(testcaserownum=1;testcaserownum<=rowcnt;testcaserownum++)
		{
			if(excel.getCellData(Constants.DataSheet,0,testcaserownum).equalsIgnoreCase(testname))
			{
				break;
			}
			
		}
		// print only the numeric start row as requested
		System.out.println("Test case start rownum:"+testcaserownum);
		
		int testdatastartrownum=testcaserownum+2;
		int testdataendrow=0;
		// Determine last row safely to avoid infinite loop when excel.getCellData
		// returns an error message instead of empty string for out-of-range rows.
		
		for (int r = testdatastartrownum; r <= rowcnt; r++) {
			String cell = excel.getCellData(Constants.DataSheet, 0, r);
			if (cell == null || cell.trim().isEmpty()) {
				break; // reached end of data for this test
			}
			// If getCellData returns an error text like "row X or column Y does not exist in xls",
			// treat it as end of data as well to avoid accidental infinite loops.
			if (cell.toLowerCase().startsWith("row ") && cell.toLowerCase().contains("does not exist")) {
				break;
			}
			testdataendrow++;
		}
		System.out.println("Total Data rows are:"+testdataendrow);
		
		int testcolumnnamerow=testcaserownum+1;
		
		int totalcols=excel.getLastcolumn(Constants.testsheetXL_Path, Constants.DataSheet,testcolumnnamerow);
		
		System.out.println("Total Columns in test case are:"+totalcols);

		
		Object[][] data=new Object[testdataendrow][1];
		int k=0;
		for(int i=testdatastartrownum;i<(testdatastartrownum+testdataendrow);i++)
		{
			Hashtable<String, String> table = new Hashtable<String, String>();
			for(int j=0;j<totalcols;j++)
			{
				String testdata=excel.getCellData(Constants.DataSheet, j,i);
				String colname=excel.getCellData(Constants.DataSheet, j,testcolumnnamerow);
				table.put(colname, testdata);
			}
			data[k][0]=table;
			k++;
		}
		
		return data;
		
		
	}

}
