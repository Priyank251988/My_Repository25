package com.ab.utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.ab.base.Basetest;

public class TestUtil extends Basetest{
	public static String scrrenshotname;

	public static void captureScreenshot()
	{
	
		File srcfile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		Date d=new Date();
		
		try {
			scrrenshotname="Error_Screenshot"+d.toString().replace(":", "_").replace(" ", "_")+".png";
			
			System.out.println(System.getProperty("user.dir")+"\\target\\surefire-reports\\html");
			FileUtils.copyFile(srcfile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+scrrenshotname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
