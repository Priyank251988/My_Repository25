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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Basetest {
	
	public static Properties  or;
	public static Properties  config;
	public static FileInputStream fis;
	// Use ThreadLocal to support parallel test execution where each thread has its own WebDriver
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public Logger log=Logger.getLogger(this.getClass().getName());
	
	@BeforeMethod
	public void setUp()
	{
		
		System.out.println("1111");
		try {
			// Load properties once
			if (config == null) {
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\ab\\properties\\or.properties");
				or=new Properties();
				or.load(fis);
                
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\ab\\properties\\config.properties");
				config=new Properties();
				config.load(fis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// create a fresh browser instance for each test method invocation
		if (driver.get() == null) {
			String browser = config.getProperty("browser");
			if (browser == null) {
				throw new RuntimeException("Browser not specified in config.properties");
			}
			WebDriver wd;
			if (browser.equalsIgnoreCase("chrome")) {
				wd = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				wd = new EdgeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				wd = new FirefoxDriver();
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}
			
			driver.set(wd);
		}
		getDriver().get(config.getProperty("testurl"));
		log.debug("Navigated to "+config.getProperty("testurl"));
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
		
	}

	/**
	 * Waits for common loader overlay (id = tree_lodar11) to disappear.
	 * This helps avoid ElementClickInterceptedException when an overlay blocks clicks.
	 */
	public void waitForLoaderToDisappear() {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
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
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		waitForLoaderToDisappear();
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
		try {
			el.click();
		} catch (org.openqa.selenium.ElementClickInterceptedException ex) {
			log.warn("Click intercepted for locator " + locator + ", falling back to JS click", ex);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
		}
	}
	
	public boolean isElementPresent(String locator)
	{
		try {
			getDriver().findElement(org.openqa.selenium.By.xpath(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@AfterMethod
	public void tearDown()
	{
		if(getDriver()!=null)
		{
			try {
				getDriver().quit();
			} catch (Exception e) {
				// ignore
			}
			driver.remove();
		}
	}

	// Helper to get the WebDriver for the current thread
	public static WebDriver getDriver() {
		return driver.get();
	}
}
