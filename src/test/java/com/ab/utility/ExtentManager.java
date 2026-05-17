package com.ab.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	public static ExtentReports extent;
	public static String scrrenshotname;
	
	public static ExtentReports getInstance(String filename)
	{
		ExtentSparkReporter spark = new ExtentSparkReporter(filename);
		spark.config().setReportName("Automation Test Results");
		spark.config().setDocumentTitle("Test Results");
		spark.config().setEncoding("utf-8");
		spark.config().setTheme(Theme.DARK);
		
		if(extent==null)
		{
			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Tester", "Priyank");
			extent.setSystemInfo("Company","AB");
			extent.setSystemInfo("Build", "1.0");
		}
		return extent;
		
	}
	
	
	

	

}
