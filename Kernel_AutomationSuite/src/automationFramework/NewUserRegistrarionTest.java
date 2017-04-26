package automationFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObject.*;
import utility.*;


import utility.Constant;

public class NewUserRegistrarionTest extends Commons {
	public WebDriver driver=null;

	String[][] InvalidSignUpData ;
	String[][] validSignUpData;

	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) {
		System.out.println(browser);
		//System.setProperty("webdriver.chrome.driver", "D:\\Installables\\Drivers\\Newfolder\\chromedriver.exe");
		//System.setProperty("webdriver.ie.driver", "D:\\Nirmal\\Automation\\Jars\\IEDriverServer.exe");
		try {
			InvalidSignUpData=(String[][]) ExcelUtils.ReadExcelData(1);
			validSignUpData=(String[][]) ExcelUtils.ReadExcelData(0);
		} catch (Exception e) {

			printer("Test Data Not Loaded");
		}
		if(browser.contains("Chrome")){
			driver = new ChromeDriver();
		}
		else{
			driver = new InternetExplorerDriver();
		}

		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}


	/*@Test(priority=1,dataProvider="getData")
	public void SignUpwithallvalidData(String Email,String LastName ,String newPassword,String ConfirmPassword)  {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		System.out.println("In NEW user");
		try{
			NewUserSignUp ( Email, LastName , newPassword, ConfirmPassword);
		}
		catch(NoSuchElementException ex){
			printer("Element Not found on registration page");
		}	
	}

	@DataProvider(name="getData")
	public Object[][] getData() throws Exception
	{
		return ExcelUtils.ReadExcelData(0); 
	}
	 */
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/*
	 * To check if required field validation working fine
	 */
	@Test(priority=2)
	public void SignUpwithfieldsempty ()  {
		SoftAssert softAssert = new SoftAssert();
		int result=0;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		System.out.println("In NEW user");
		try{
			NewUserSignUp ("","" ,"","");
			if(driver.findElements(registerPageElements.validationMsg_Email()).size()>0){
				result++;
			}
			else{
				printer("Validation Message for Email not displayed");
			}
			if(driver.findElements(registerPageElements.validationMsg_LastName()).size()>0){
				result++;
			}
			else{
				printer("Validation Message for LastName not displayed");
			}
			if(driver.findElements(registerPageElements.validationMsg_NewPassword()).size()>0){
				result++;
			}
			else{
				printer("Validation Message for New Passwordnot displayed");
			}
			if(driver.findElements(registerPageElements.validationMsg_ConfirmPassword()).size()>0){
				result++;
			}
			else{
				printer("Validation Message for Confirm Password not displayed");
			}
			softAssert.assertEquals(result, 4);
			softAssert.assertAll();


		}
		catch(NoSuchElementException ex){
			printer("Element Not found on registration page");
		}	
	}


	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Test(priority=3)
	public void SignUpwithInvalidPassword() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SoftAssert softAssert = new SoftAssert();
		int result=0;
		try{
			for(int i=1;i<=InvalidSignUpData[0].length-1;i++){
				if(i<InvalidSignUpData[0].length){
					NewUserSignUp (validSignUpData[0][0],validSignUpData[0][1] ,InvalidSignUpData[0][i],InvalidSignUpData[0][i]);
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(registerPageElements.InvalidPasswordMsg())));
					if(driver.findElements(registerPageElements.InvalidPasswordMsg()).size()>0){
						result++;
					}
					else{
						printer("Validation Message for Invalid Password not displayed");
					}
				}
				else if(i==InvalidSignUpData[0].length-1){
					NewUserSignUp (validSignUpData[0][0],validSignUpData[0][1] ,InvalidSignUpData[0][i],InvalidSignUpData[0][i]+"sdf");
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(registerPageElements.passwordNotMatchMsg())));
					if(driver.findElements(registerPageElements.passwordNotMatchMsg()).size()>0){
						result++;
					}
					else{
						printer("Validation Message for Confirm passward not displayed");
					}
				}

				driver.navigate().refresh();
			}


			softAssert.assertEquals(result, InvalidSignUpData[0].length-1);
			softAssert.assertAll();


		}
		catch(NoSuchElementException ex){
			printer("Element Not found on registration page for SignUpwithInvalidPassword");
		}	
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/*
 * To check if user is able to Sign up with Invalid email
 */
	@Test(priority=4)
	public void SignUpwithInvalidEmail() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SoftAssert softAssert = new SoftAssert();
		int result=0;
		try{
			for(int i=1;i<=InvalidSignUpData[0].length-1;i++){
				if(i<InvalidSignUpData[0].length){
					NewUserSignUp (InvalidSignUpData[0][i],validSignUpData[0][1] ,validSignUpData[0][2],validSignUpData[0][3]);
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(registerPageElements.InvalidEmailMsg())));
					if(driver.findElements(registerPageElements.InvalidEmailMsg()).size()>0){
						result++;
					}
					else{
						printer("Validation Message for Invalid Password not displayed when user tried with"+InvalidSignUpData[0][i]);
					}
				}

				driver.navigate().refresh();
			}


			softAssert.assertEquals(result, InvalidSignUpData[0].length-1);
			softAssert.assertAll();


		}
		catch(NoSuchElementException ex){
			printer("Element Not found on registration page SignUpwithInvalidEmail");
		}	
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
// Closing the browser at the end of this test case	
	@AfterClass
	public void closeBrowser(){
		driver.close();
	}
	
	/*
	 * Common function for user registration
	 */
		 
		public void NewUserSignUp (String Email,String LastName ,String newPassword,String ConfirmPassword)  {
			WebDriverWait wait = new WebDriverWait(driver, 300);
			try{
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.navigate().to(Constant.URL);
				try {
					driver.findElement(homePageElements.link_SignIn()).click();
				} catch (NoSuchElementException  e) {
					System.out.println("homePageElements.link_SignIn");
				}
				try {
					driver.findElement(loginPageElements.link_SignUp()).click();
				} catch (NoSuchElementException  e) {
					System.out.println("loginPageElements.link_SignUp");
				}
				wait.until(ExpectedConditions.elementToBeClickable(registerPageElements.txtbox_newPassword()));
				try {
					driver.findElement(registerPageElements.txtbox_newPassword()).sendKeys(newPassword);
				} catch (NoSuchElementException  e) {
					System.out.println("txtbox_newPassword");
				}
				try {
					driver.findElement(registerPageElements.txtbox_Email()).sendKeys(Email);
				} catch (NoSuchElementException  e) {
					System.out.println("txtbox_Email");
				}
				try {
					driver.findElement(registerPageElements.txtbox_LastName()).sendKeys(LastName);
				} catch (NoSuchElementException  e) {
					System.out.println("txtbox_LastName");
				}
				try {
					driver.findElement(registerPageElements.txtbox_ConfirmPassword()).sendKeys(ConfirmPassword);
				} catch (NoSuchElementException  e) {
					System.out.println("txtbox_ConfirmPassword");
				}
				try {
					wait.until(ExpectedConditions.elementToBeClickable(registerPageElements.btn_Next()));
					driver.findElement(registerPageElements.btn_Next()).click();
				} catch (NoSuchElementException  e) {
					System.out.println("btn_Next");
				}
			}
			catch(NoSuchElementException ex){
				printer("Element Not found on registration page ");
			}	
		}

}
