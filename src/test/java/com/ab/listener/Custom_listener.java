package com.ab.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.ITestAnnotation;

import com.ab.utility.ExtentManager;
import com.ab.utility.TestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Custom_listener implements ITestListener, IAnnotationTransformer {
	
		static Date d=new Date();
		static String filename="Test-Report"+d.toString().replace(":", "_").replace(" ", "_")+".html";
		public static ExtentReports extent= ExtentManager.getInstance(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+filename);
		public static ThreadLocal<ExtentTest> test=new ThreadLocal<>();	
	
	
		
		public static ExtentTest getTest()
		{
			return test.get();
		}

	    @Override
	    public void onTestStart(ITestResult result) {
	    	
	    	
	    	test.set(extent.createTest(result.getClass().getName()+"@Testcase :"+result.getMethod().getMethodName()));
	        System.out.println("START : " + result.getName());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	    	String methodname=result.getMethod().getMethodName();
	    	String logtext="<b>"+"TEST CASE: "+methodname.toUpperCase()+" PASSED"+"</b>";
	    	Markup m=MarkupHelper.createLabel(logtext, ExtentColor.GREEN);

	    	getTest().pass(m);
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {

	    	System.setProperty("org.uncommons.reportng.escape-output", "false");
	    	TestUtil.captureScreenshot();
	    	Reporter.log("<a href=\""+TestUtil.scrrenshotname+"\">screenshot</a>");
	    	String methodname=result.getMethod().getMethodName();
	    	String logtext="<b>"+"TEST CASE: "+methodname.toUpperCase()+" FAILED"+"</b>";
	    	Markup m=MarkupHelper.createLabel(logtext, ExtentColor.RED);
	    	
	    	getTest().fail("Screenshot of failure",MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.scrrenshotname).build());
	    	getTest().log(Status.FAIL, m);
	        System.out.println("FAIL : " + result.getName());
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {

	    	String methodName = result.getMethod().getMethodName();
			String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			getTest().skip(m);

	    }

	    @Override
	    public void onStart(ITestContext context) {

	        System.out.println("SUITE START");
	    }

	    @Override
	    public void onFinish(ITestContext context) {

	    	if (extent != null) {

				extent.flush();
			}
	    }
	    
	    @Override
	    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
	    	annotation.setRetryAnalyzer(RetryAnalyzer.class);
	    }
	}
	
	
	


