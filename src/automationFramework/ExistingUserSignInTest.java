package automationFramework;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

public class ExistingUserSignInTest extends Commons {
	public WebDriver driver=null;
	String[][] InvalidLoginData ;





	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) throws IOException {
		System.out.println("Existing User SignIn Test : Started");
		try {

			InvalidLoginData=(String[][]) ExcelUtils.ReadExcelData(2);
		} catch (Exception e) {

			printer("Test Data Not Loaded");
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
	/*
	 * Sign in with valid EmailID and Password (Normal User)
	 * @param priority sets the priority of executing the test case
	 */
	@Test(priority=8)
	public void SigninwithvalidEmailIDandPassword() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SoftAssert softAssert = new SoftAssert();
		try{

			existingUserSignIn(InvalidLoginData[0][1],InvalidLoginData[0][2]);

			wait.until(ExpectedConditions.urlContains("#home"));
			if(driver.findElements(loginPageElements.login_Element()).size()>0){
				printer("User Login Successfully");
				softAssert.assertTrue(true);

				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(loginPageElements.Link_profileInitials())).perform();
				//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("Link_Logout")));

				try {
					//wait.until(ExpectedConditions.visibilityOfElementLocated(loginPageElements.Link_Logout()));
					Thread.sleep(500);
					driver.findElement(loginPageElements.Link_Logout()).click();

				} catch (Exception e) {
					driver.navigate().to(Constant.URL+"#logout");
				}
			}
			else {

				printer("SigninwithvalidEmailIDandPassword : Failed ");
				softAssert.assertTrue(false);
			}

		}
		catch(Exception Ex){

			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign in with Invalid EmailID 
	 */

	@Test(priority=1)
	public void SigninwithInvalidEmailID () {
		SoftAssert softAssert = new SoftAssert();
		try{
			existingUserSignIn(InvalidLoginData[1][1],InvalidLoginData[1][2]);

			if(driver.findElements(loginPageElements.login_EmailValiMsg()).size()>0){
				softAssert.assertTrue(true);
			}
			else {
				printer("SigninwithvalidEmailIDandPassword : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(Exception Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign in with Email ID & Password which is not registered 
	 */

	@Test(priority=2)
	public void nonExistingUserLogin() {
		SoftAssert softAssert = new SoftAssert();
		try{
			existingUserSignIn(InvalidLoginData[2][1],InvalidLoginData[2][2]);

			if(driver.findElements(loginPageElements.AccountDoesNotExistMsg()).size()>0){
				softAssert.assertTrue(true);
			}
			else {
				printer("SigninwithvalidEmailIDandPassword : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(Exception Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign in with Email ID & Password which is not registered 
	 */
	@Test(priority=3)
	public void validEmailAndInvalidPassword () {
		SoftAssert softAssert = new SoftAssert();
		try{
			existingUserSignIn(InvalidLoginData[3][1],InvalidLoginData[3][2]);

			if(driver.findElements(loginPageElements.incorrectPasswordMsg()).size()>0){
				softAssert.assertTrue(true);
			}
			else {
				printer("Sign in with Email ID & Password which is not registered : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(Exception Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign in with Valid Email and empty password
	 */
	@Test(priority=4)
	public void validEmailAndEmptyPassword () {
		SoftAssert softAssert = new SoftAssert();
		try{
			existingUserSignIn(InvalidLoginData[4][1],"");

			if(driver.findElements(loginPageElements.PasswordRequiredMsg()).size()>0){
				softAssert.assertTrue(true);
			}
			else {
				printer("validEmailAndEmptyPassword : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(Exception Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign in with Empty Email and empty password
	 */
	@Test(priority=5)
	public void EmptyEmailAndEmptyPassword () {
		SoftAssert softAssert = new SoftAssert();
		try{
			//existingUserSignIn("","");
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.txtbox_Email()).clear();
			//driver.findElement(loginPageElements.txtbox_Email()).sendKeys(Email); 
			//driver.findElement(loginPageElements.txtbox_Password()).sendKeys(Passward); 
			driver.findElement(loginPageElements.button_Enter()).click();

			if(driver.findElements(loginPageElements.PasswordRequiredMsg()).size()>0 && driver.findElements(loginPageElements.EmailRequiredMsg()).size()>0){
				softAssert.assertTrue(true);
			}
			else {
				printer("EmptyEmailAndEmptyPassword : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(Exception Ex){
			printer("EmptyEmailAndEmptyPassword : Failed ");
			//printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Forgot Password link visible and working
	 * @throws InterruptedException
	 */

	@Test(priority=6)
	public void ForgotPasswardWorking () throws InterruptedException  {
		SoftAssert softAssert = new SoftAssert();
		try{
			driver.manage().deleteAllCookies();
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.Link_ForgotMyPassword()).click();
			//Thread.sleep(5000);
			if(driver.findElements(passwordResetPageElements.text_PasswordReset()).size()>0 && driver.getCurrentUrl().contains("passwordreset"))
			{
				softAssert.assertTrue(true);
			}
			else 
			{
				printer("Forgot Passward Link Working on Login page  : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(NoSuchElementException Ex){
			printer("Forgot Passward Link Working on Login page  : Failed ");
			//printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Sign Up link visible and working
	 * @throws InterruptedException
	 */


	@Test(priority=7)
	public void SignUpLinkWorking () throws InterruptedException  {
		SoftAssert softAssert = new SoftAssert();
		try{
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.link_SignUp()).click();
			Thread.sleep(5000);
			if(driver.findElements(loginPageElements.LoginPage_SignUpText()).size()>0 && driver.getCurrentUrl().contains("register"))
			{
				softAssert.assertTrue(true);
			}
			else 
			{
				printer("SignUp Link Working on Login page  : Failed ");
				softAssert.assertTrue(false);
			}
		}
		catch(NoSuchElementException Ex){
			printer(Ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}


	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Closing the browser after test case execution
	 */



	@AfterClass
	public void closeBrowser(){
		System.out.println("Existing User SignIn Test : Completed");
		driver.close();
	}

	public void existingUserSignIn(String Email,String Passward){
		try {
			driver.navigate().to(Constant.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(homePageElements.link_SignIn()).click();
			driver.findElement(loginPageElements.txtbox_Email()).clear();
			driver.findElement(loginPageElements.txtbox_Email()).sendKeys(Email); 
			driver.findElement(loginPageElements.txtbox_Password()).sendKeys(Passward); 
			driver.findElement(loginPageElements.button_Enter()).click();


		}
		catch (NoSuchElementException e) {

			printer("Element Not found on Login Page");
		}

	}

}
