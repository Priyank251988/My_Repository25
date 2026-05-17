package com.ab.testcases;

import java.time.Duration;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ab.utility.DataProviders;

public class LoginTest extends com.ab.base.Basetest {
	@Test(dataProviderClass=DataProviders.class,dataProvider="LoginTestDP")
	public void loginTest(Hashtable<String,String>data) {
		
		 
		type("email_xpath", data.get("Username"));
		click("next_xpath");
		
		type("password_xpath", data.get("Password"));
		click("signin_xpath");
		log.debug("Login submitted");
		
		// wait for the application to finish login and the main UI to be ready
				WebDriverWait wait = new WebDriverWait(com.ab.base.Basetest.getDriver(), Duration.ofSeconds(20));
				
				Assert.assertTrue(isElementPresent("//*[@id=\"tab1\"]/span"), "Fvorites should be present");
		
	}

}
