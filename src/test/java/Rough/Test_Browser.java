package Rough;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ab.base.Basetest;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;



public class Test_Browser extends Basetest {

	@Test
	public void testLoginAndClickNew() throws InterruptedException {
		
		// locate and interact with login fields
		WebElement user = getDriver().findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Login_txtUsrEmailId\"]"));
		user.sendKeys("priyank.sharma@awarebase.com");
		getDriver().findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Login_btnCheckUserType\"]")).click();
		WebElement pass = getDriver().findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Login_txtUsrPwd\"]"));
		pass.sendKeys("Pass@Pass@123");
		getDriver().findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Login_btnLogin\"]")).sendKeys(Keys.ENTER);
		log.debug("Login submitted");
		
		//Thread.sleep(30000); // Wait for the dropdown to appear

		// wait for the application to finish login and the main UI to be ready
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
		
		Assert.assertTrue(isElementPresent("//*[@id=\"tab1\"]/span"), "Fvorites should be present");
		// Wait for the Favorites page to load and the "New" button (upper-left) to be clickable.
		
		  By newBtnLocator = By.xpath("//*[@id='top_New']");
		  // use safeClick helper to avoid click interception by overlays/loaders
		  safeClick(newBtnLocator);
		  Thread.sleep(2000); // brief wait for the dropdown to appear
		 
	}
}
