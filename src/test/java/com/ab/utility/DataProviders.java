package com.ab.utility;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginTestDP")
	public static Object[][] getDataSuite1(Method m)
	{
		ExcelReader excel=new ExcelReader(Constants.testsheetXL_Path);
		String testcase=m.getName();
		return Data_Util.getData(testcase, excel);
	}
	
	

}
