package automationFramework;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObject.FeedbackAndInsightElements;
import pageObject.LogedInhomepageElement;
import pageObject.homePageElements;
import pageObject.loginPageElements;
import utility.Commons;
import utility.Constant;
import utility.ExcelUtils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class FeedbackAndInsightsTest extends Commons{

	public WebDriver driver=null;
	String[][] FeedbackFormData ;

	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) {


		try {

			FeedbackFormData=(String[][]) ExcelUtils.ReadExcelData(2);
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

	@Test
	public void RequiredFieldValidationFeedbackForm() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);

		try {
			existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
			Actions action = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
			action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
		} catch (NoSuchElementException e) {
			printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
			result=false;
		}

		try {
			driver.findElement(LogedInhomepageElement.link_AddFeedback());
		} catch (NoSuchElementException e) {
			printer("LogedInhomepageElement.link_AddFeedback Not found on feedback page");
			result=false;
		}

		try{
			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();
		}
		catch(NoSuchElementException ex){
			printer("FeedbackAndInsightElements.btn_Post Not found on feedback page");
			result=false;
		}
		// Clicking on Ok button
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidationMsg()).size()>0){
				driver.findElement(FeedbackAndInsightElements.btn_PopUpOK()).click();
				int RequiredfieldsCount=driver.findElements(FeedbackAndInsightElements.error_RequiredField()).size();
				if(RequiredfieldsCount>0){
					softAssert.assertEquals(RequiredfieldsCount, NumberOfReqFieldsOnFeedbackForm);
				}
			}

		} catch (NoSuchElementException e) {
			printer("POP.btn_Post Not found on feedback page");
			result=false;
		}

		softAssert.assertTrue(result);
		softAssert.assertAll();

	}

	@Test
	public void ContactNameField(){
		Boolean existingContactIsSearchable=false;
		Boolean existingContactIsEditable=false;
		Boolean IsContactFieldPopUpPromptedWhenClicked=false;
		Boolean popUp_StartOverButton=false;
		Boolean  popUp_CreateNewContact=false;
		Boolean  popUp_ContinueEditingThisContact=false;
		
		TestNG v= new TestNG();
		v.setDefaultTestName("sdas");
		
		
		
		
		try{
			//Add  new contact and submit the Feedback
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys();

			//Use Existing contact and submit the feedback
			
			
			//Verify the newly added contact name is available for selection in the next feedback
		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
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
	@BeforeSuite
	public void setsysproperties(){
		System.setProperty("webdriver.chrome.driver", "D:\\Installables\\Drivers\\Newfolder\\chromedriver.exe");
		System.setProperty("webdriver.ie.driver", "D:\\Installables\\Drivers\\Newfolder\\IEDriverServer.exe");
	}
}
