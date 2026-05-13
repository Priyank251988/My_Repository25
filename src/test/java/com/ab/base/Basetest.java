package com.ab.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class Basetest {
	
	public static Properties  or;
	public static Properties  config;
	public static FileInputStream fis;
	public static WebDriver driver;
	public Logger log=Logger.getLogger(this.getClass().getName());
	
	@BeforeSuite
	public void setUp()
	{
		System.out.println("1111");
		if(driver==null)
		try {
			fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\ab\\properties\\or.properties");
			or=new Properties();
			or.load(fis);
			
			fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\ab\\properties\\config.properties");
			config=new Properties();
			config.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (config.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
		}
		else if (config.getProperty("browser").equalsIgnoreCase("edge"))
		{
			driver=new EdgeDriver();
		}
		 else if (config.getProperty("browser").equalsIgnoreCase("firefox"))
		 {
			 driver=new FirefoxDriver();
		 }
		driver.get(config.getProperty("testurl"));
		log.debug("Navigated to "+config.getProperty("testurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		
	}

	/**
	 * Waits for common loader overlay (id = tree_lodar11) to disappear.
	 * This helps avoid ElementClickInterceptedException when an overlay blocks clicks.
	 */
	public void waitForLoaderToDisappear() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("tree_lodar11")));
		} catch (Exception e) {
			// ignore - loader might not be present
		}
	}

	/**
	 * Clicks a locator safely: waits for loader to disappear, waits for clickable,
	 * attempts a normal click and falls back to JS click if intercepted.
	 */
	public void safeClick(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		waitForLoaderToDisappear();
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
		try {
			el.click();
		} catch (org.openqa.selenium.ElementClickInterceptedException ex) {
			log.warn("Click intercepted for locator " + locator + ", falling back to JS click", ex);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
		}
	}
	
	public boolean isElementPresent(String locator)
	{
		try {
			driver.findElement(org.openqa.selenium.By.xpath(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@AfterSuite
	public void tearDown()
	{
		if(driver!=null)
		{
			driver.quit();
		}
	}
}
