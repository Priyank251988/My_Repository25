package com.ab.listener;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	private int retryCount = 0;
	private int maxRetryCount = 2;
	
	@Override
	public boolean retry(ITestResult result) {
		
		Throwable exception = result.getThrowable();
		
		if (exception instanceof TimeoutException ||
		    exception instanceof WebDriverException) {
			
			if (retryCount < maxRetryCount) {
				System.out.println("Retrying test: " + result.getName() + " | Attempt: " + (retryCount + 1));
				retryCount++;
				return true;
			}
		}
		
		return false;
	}
}
