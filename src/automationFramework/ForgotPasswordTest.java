package automationFramework;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.collect.ImmutableMap;

import pageObject.homePageElements;
import pageObject.loginPageElements;
import pageObject.passwordResetPageElements;
import utility.Commons;
import utility.Constant;
import utility.ExcelUtils;
/**
 *To check the functionality of Forgot Password
 */
public class ForgotPasswordTest extends Commons {
	public WebDriver driver=null;
	// Variable required for storing the data fetched from Excel
	String[][] PasswordResetData ;

	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) throws IOException {
		System.out.println(browser);
		try {

			PasswordResetData=(String[][]) ExcelUtils.ReadExcelData(2);
		} catch (Exception e) {

			printer("Test Data Not Loaded from excel");
		}
		System.out.println(System.getProperty("os.name"));
		String OS= System.getProperty("os.name");
		if(OS.contains("Windows")){
			System.out.println("Windows");

			if(browser.contains("Chrome"))
			{
				driver = new ChromeDriver();
			}
			else
			{
				driver = new InternetExplorerDriver();
			}

		}
		else { 

			ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File("/usr/bin/chromedriver"))	
					.usingAnyFreePort()
					.withEnvironment(ImmutableMap.of("DISPLAY",":2"))
					.build();
			service.start();

			driver = new ChromeDriver(service);

		}
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
// user with valid email address
	@Test(priority=1)
	public void ForgotPasswardWorking ()  {
		System.out.println(" ForgotPasswardWorking : START");
		SoftAssert softAssert = new SoftAssert();
		try{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.Link_ForgotMyPassword()).click();
			if(driver.findElements(passwordResetPageElements.text_PasswordReset()).size()>0 && driver.getCurrentUrl().contains("passwordreset"))
			{
				driver.findElement(passwordResetPageElements.txtbox_emailInput()).sendKeys(PasswordResetData[0][1]);
				driver.findElement(passwordResetPageElements.btn_SendResetRequeset()).click();
				if(driver.findElements(passwordResetPageElements.text_SendResetRequesetSuccess()).size()>0)
				{
					softAssert.assertTrue(true);
					
				}
				else
				{
					printer("User is not able to reset Passward");
					softAssert.assertTrue(false);
				}
			}
			else 
			{
				printer("Forgot Passward Link Not Working on Login page");

			}
		}
		catch(NoSuchElementException Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
		System.out.println(" ForgotPasswardWorking : END");
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
// To check required field validation
	@Test(priority=2)
	public void ForgotPasswardWithEmptyEmail() throws InterruptedException  
	{
		System.out.println(" ForgotPasswardWithEmptyEmail : START");
		SoftAssert softAssert = new SoftAssert();
		try{
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.Link_ForgotMyPassword()).click();
			if(driver.findElements(passwordResetPageElements.text_PasswordReset()).size()>0 && driver.getCurrentUrl().contains("passwordreset"))
			{
				//driver.findElement(passwordResetPageElements.txtbox_emailInput()).sendKeys("");
				driver.findElement(passwordResetPageElements.btn_SendResetRequeset()).click();
				Thread.sleep(5000);
				if(driver.findElements(passwordResetPageElements.EmailRequiredMsg()).size()>0)
				{
					softAssert.assertTrue(true);
				}
				else
				{
					printer("User is not able to reset Passward");
					softAssert.assertTrue(false);
				}
			}
			else 
			{
				printer("Forgot Passward Link Not Working on Login page");

			}
		}
		catch(NoSuchElementException Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
		System.out.println(" ForgotPasswardWithEmptyEmail : END");
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
// User with Invalid email address
	@Test(priority=3)
	public void ForgotPasswardWithInvalidEmail()  
	{
		System.out.println(" ForgotPasswardWithInvalidEmail : START");
		SoftAssert softAssert = new SoftAssert();
		try{
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.Link_ForgotMyPassword()).click();
			if(driver.findElements(passwordResetPageElements.text_PasswordReset()).size()>0 && driver.getCurrentUrl().contains("passwordreset"))
			{
				driver.findElement(passwordResetPageElements.txtbox_emailInput()).sendKeys(PasswordResetData[1][1]);
				driver.findElement(passwordResetPageElements.btn_SendResetRequeset()).click();
				if(driver.findElements(passwordResetPageElements.Reset_EmailValiMsg()).size()>0)
				{
					softAssert.assertTrue(true);
				}
				else
				{
					printer("User is not able to reset Passward");
					softAssert.assertTrue(false);
				}
			}
			else 
			{
				printer("Forgot Passward Link Not Working on Login page");

			}
		}
		catch(NoSuchElementException Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
		System.out.println(" ForgotPasswardWithInvalidEmail : END");
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Closing the browser at the end of this test case	
	@AfterClass
	public void closeBrowser(){
		System.out.println("ForgotPassword Test Completed");
		driver.close();
	}

}
