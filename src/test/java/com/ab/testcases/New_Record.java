package com.ab.testcases;

import java.time.Duration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ab.base.Basetest;
import com.ab.utility.DataProviders;

public class New_Record extends Basetest {
	@Test(dataProviderClass=DataProviders.class,dataProvider="LoginTestDP")
	public void new_Record(Hashtable<String,String>data)
	
	{
		
		type("email_xpath", data.get("Username"));
		click("next_xpath");
		
		type("password_xpath", data.get("Password"));
		click("signin_xpath");
		log.debug("Login submitted");
		
		WebDriverWait wait = new WebDriverWait(com.ab.base.Basetest.getDriver(), Duration.ofSeconds(20));
		
		Assert.assertTrue(isElementPresent("//*[@id=\"tab1\"]/span"), "Fvorites should be present");
		
		By newBtnLocator = By.xpath("//*[@id='top_New']");
		  // use safeClick helper to avoid click interception by overlays/loaders
		  safeClick(newBtnLocator);
		 
		  click("action_xpath");
		  click("create_xpath");
		  Set<String>handl= getDriver().getWindowHandles();
		  Iterator<String>it=handl.iterator();
		  String firstwindow=it.next();
		  String secondwindow=it.next();
		  getDriver().switchTo().window(secondwindow);
		  type("field_xpath", "sdsdsdsds");
		  click("save_xpath");
		  
		  wait.until(ExpectedConditions.visibilityOfElementLocated(
			        By.id("DivNavigation")));
		  Reporter.log("New record created successfully");
		  Assert.fail("Intentional failure to test reporting");
		  
		  
		  
		  
		
		  
		 
	}
	

}
