package automationFramework;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.inject.Key;

import pageObject.FeedbackAndInsightElements;
import pageObject.LogedInhomepageElement;
import pageObject.homePageElements;
import pageObject.loginPageElements;
import utility.Commons;
import utility.Constant;
import utility.ExcelUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
	Boolean isUSerLogin=false;

	Boolean existingContactIsSearchable=false;
	Boolean existingContactIsEditable=false;
	Boolean IsContactFieldPopUpPromptedWhenClicked=false;
	Boolean popUp_StartOverButton=false;
	Boolean  popUp_CreateNewContact=false;
	Boolean  popUp_ContinueEditingThisContact=false;

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
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void RequiredFieldValidationFeedbackForm() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);

		try {
			if(isUSerLogin!=true){
				existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
			}
			Actions action = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
			action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
			isUSerLogin=true;
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
		// Clicking on OK button
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidationMsg()).size()>0){
				driver.findElement(FeedbackAndInsightElements.btn_PopUpOK()).click();
				int RequiredfieldsCount=driver.findElements(FeedbackAndInsightElements.error_RequiredField()).size();
				if(RequiredfieldsCount>0){
					softAssert.assertEquals(RequiredfieldsCount, NumberOfReqFieldsOnFeedbackForm, "No. of required fields are not matching");
				}
			}

		} catch (NoSuchElementException e) {
			printer("POP.btn_Post Not found on feedback page");
			result=false;
		}

		softAssert.assertTrue(result);
		softAssert.assertAll();

	}


	//@Test
	public void submitNewFeedback() throws Exception{

		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
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

			Boolean userExistingContact=false;
			Boolean CreateNew=false;
			submitFeedback(feedbackFormData[0][0],CreateNew,userExistingContact,feedbackFormData[0][1],feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8]);

		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();
	}



	@Test(priority=1)
	public void ExistingContactNameAndSubmit() throws Exception{

		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=false;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
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

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][0]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			String selectedtext=driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).getAttribute("value");
			softAssert.assertEquals(selectedtext,feedbackFormData[0][0], "Text searched is not matching");

			Boolean userExistingContact=true;
			Boolean CreateNew=false;
			submitFeedback(feedbackFormData[0][0],CreateNew,userExistingContact,feedbackFormData[0][1],feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8]);



		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();
	}

	@Test(priority=2)
	public void POPUP_ContactName_StartOver() throws Exception{


		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
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

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][0]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);


			try {
				popappears(driver.findElement(FeedbackAndInsightElements.txtbox_contactname()));
				wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
				if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
					driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();
					String ContactName= driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).getAttribute("value");
					if(ContactName.length()>0){
						System.out.println("Contact Name textbox Not cleared");						
					}
				}
				else{
					softAssert.fail("PopUp Not appeared");
				}


				Boolean userExistingContact=false;
				Boolean CreateNew=false;
				submitFeedback(feedbackFormData[0][0],CreateNew,userExistingContact,feedbackFormData[0][1],feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8]);

				if(!driver.getCurrentUrl().contains("#home")){
					softAssert.assertTrue(true);
				}
				else{
					softAssert.fail("User is not able to submit the feebback form when clicked on startover button");
				}

			} catch (NoSuchElementException e) {
				printer("POP.btn_Post Not found on feedback page:"+e);
				result=false;
			}

		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();
	}


	@Test(priority=3)
	public void POPUP_ContactName_CreateNew() throws Exception{


		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
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

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][0]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);


			try {
				popappears(driver.findElement(FeedbackAndInsightElements.txtbox_contactname()));
				wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
				if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpcreateNew()));
					driver.findElement(FeedbackAndInsightElements.btn_PopUpcreateNew()).click();
					String ContactName= driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).getAttribute("value");
					if(ContactName.length()>0){
						System.out.println("Contact Name textbox Not cleared");						
					}
				}
				else{
					softAssert.fail("PopUp Not appeared");
				}


				Boolean userExistingContact=false;
				Boolean CreateNew=true;
				submitFeedback(feedbackFormData[0][0],CreateNew,userExistingContact,feedbackFormData[0][1],feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8]);

				if(!driver.getCurrentUrl().contains("#home")){
					softAssert.assertTrue(true);
				}
				else{
					softAssert.fail("User is not able to submit the feebback form when clicked on startover button");
				}

			} catch (NoSuchElementException e) {
				printer("POP.btn_Post Not found on feedback page:"+e);
				result=false;
			}

		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();
	}


	@Test(priority=4)
	public void POPUP_ContactName_ContinueEdiing() throws Exception{


		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn("nirmal@gokernel.com", "Logintmm001");
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
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

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][0]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);


			try {
				popappears(driver.findElement(FeedbackAndInsightElements.txtbox_contactname()));
				wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
				if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpContinueEditing()));
					driver.findElement(FeedbackAndInsightElements.btn_PopUpContinueEditing()).click();
					String ContactName= driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).getAttribute("value");
					if(ContactName.length()>0){
						System.out.println("Contact Name textbox Not cleared");						
					}
				}
				else{
					softAssert.fail("PopUp Not appeared");
				}


				Boolean userExistingContact=false;
				Boolean CreateNew=true;
				submitFeedback(feedbackFormData[0][0]+" Edited",CreateNew,userExistingContact,feedbackFormData[0][1],feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8]);

				if(!driver.getCurrentUrl().contains("#home")){
					softAssert.assertTrue(true);
				}
				else{
					softAssert.fail("User is not able to submit the feebback form when clicked on startover button");
				}

			} catch (NoSuchElementException e) {
				printer("POP.btn_Post Not found on feedback page:"+e);
				result=false;
			}

		}
		catch (NoSuchElementException e) {
			printer("Elements Not found on while checking the functionality of contact field");
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();
	}




	public void TypeInField(By eleLocator, String value) throws InterruptedException{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			String val = value; 
			wait.until(ExpectedConditions.visibilityOfElementLocated(eleLocator));
			WebElement element = driver.findElement(eleLocator);
			
			element.click();
			element.clear();
			for (int i = 0; i < val.length(); i++){
				char c = val.charAt(i);
				String s = new StringBuilder().append(c).toString();
				element.sendKeys(s);
				Thread.sleep(150);
			}       
		} catch (Exception e) {
			
		}

	}


	public void selectOption(String option,By dropdownLocator,By menuLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		// Open the dropdown so the options are visible
		wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
		driver.findElement(dropdownLocator).click();
		// Get all of the options
		List<WebElement> options=driver.findElement(menuLocator).findElements(By.tagName("li"));
		// Loop through the options and select the one that matches
		for (WebElement opt : options) {
			if (opt.getText().equals(option)) {
				opt.click();
				return;
			}
		}
		throw new NoSuchElementException("Can't find " + option + " in dropdown");
	}


	// To prompt the popup
	public void popappears(WebElement element){
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
	}


	public void submitFeedback(String ContactName,Boolean CreateNew,Boolean searchContactName, String primaryrole,String recognitionlevel,String contactattributes,String institution,String institutiontype,String Player,String IDSMember,String IDSInput,String Date,String Time){

		try{
			WebDriverWait wait = new WebDriverWait(driver, 100);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(FeedbackAndInsightElements.txtbox_contactname(),ContactName);
			if(searchContactName){
				selectOptionWithIndex(0, driver,Constant.UL_ID_1);
				Thread.sleep(200);

				String Selected_primaryrole= driver.findElement(FeedbackAndInsightElements.Dropdown_primaryrole()).getText();
				String Selected_Recognitionlevel= driver.findElement(FeedbackAndInsightElements.Dropdown_recognitionlevel()).getText();
				Boolean Selected_contactattributesFound=false; 
				List<WebElement> options=driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("li"));
				// Loop through the options and select the one that matches
				for (WebElement opt : options) {
					if (opt.getText().equals(contactattributes)) {
						Selected_contactattributesFound=true;

					}


				}

				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Date);
				selectOption(Time,FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
				driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			}	
			else{
				if(!CreateNew){
					driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).clear();
					TypeInField(FeedbackAndInsightElements.txtbox_contactname(),ContactName);
				}
				
				driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);



				selectOption(primaryrole,FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
				selectOption(recognitionlevel,FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
				TypeInField(FeedbackAndInsightElements.txtbox_AontactAttributes(),contactattributes);
				// select the element from list
				List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
				if(0<=options.size()) {
					options.get(0).click();
				}
				else{
					driver.findElement(FeedbackAndInsightElements.txtbox_AontactAttributes()).sendKeys(Keys.TAB);
				}
				driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
				if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
					driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();
					
				}
				Thread.sleep(1000);
				TypeInField(FeedbackAndInsightElements.txtbox_institutionList(),institution);
				selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
					selectOption(Time,FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
					driver.findElement(FeedbackAndInsightElements.btn_Post()).click();
				
			}
		}catch(Exception ex){
			printer(ex.getMessage());
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
