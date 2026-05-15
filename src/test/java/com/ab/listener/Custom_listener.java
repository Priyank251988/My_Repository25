package com.ab.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.ab.utility.TestUtil;

public class Custom_listener implements ITestListener {
	
	

	    @Override
	    public void onTestStart(ITestResult result) {

	        System.out.println("START : " + result.getName());
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {

	        System.out.println("PASS : " + result.getName());
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {

	    	System.setProperty("org.uncommons.reportng.escape-output", "false");
	    	TestUtil.captureScreenshot();
	    	Reporter.log("<a href=\""+TestUtil.scrrenshotname+"\">screenshot</a>");
	        System.out.println("FAIL : " + result.getName());
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {

	        System.out.println("SKIP : " + result.getName());
	    }

	    @Override
	    public void onStart(ITestContext context) {

	        System.out.println("SUITE START");
	    }

	    @Override
	    public void onFinish(ITestContext context) {

	        System.out.println("SUITE FINISH");
	    }
	}
	
	
	


