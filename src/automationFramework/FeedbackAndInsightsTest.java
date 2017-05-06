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

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.common.io.payload.PayloadProcessor;
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

public class FeedbackAndInsightsTest extends Commons {

	public WebDriver driver=null;
	String[][] FeedbackFormData ;
	Boolean isUSerLogin=false;

	Boolean existingContactIsUseable=false;
	Boolean existinginstitutionIsUseable=false;
	Boolean CreateNewInstitution=false;
	Boolean existingContactIsEditable=false;
	Boolean NewAddedIDAInput=false;
	Boolean IsContactFieldPopUpPromptedWhenClicked=false;
	Boolean POPUP_Institution_StartOver=false;
	Boolean  popUp_CreateNewContact=false;
	Boolean  popUp_ContinueEditingThisContact=false;
	Boolean existingContactAttribute =false;
	Boolean newContactAttribute=false;
	Boolean selectnewlyaddedContactAttribute=false;
	Boolean ContactAttributeIsRemoveable=false;
	Boolean addmultipleContact=false;
	Boolean removeContactAttribute=false;
	Boolean IsShowContactDetails=false;

	String[][] InvalidLoginData;
	String UserName="";
	String Password="";

	Boolean IDSCheckBox=false;
	Boolean PayerCheckBox=false;
	Boolean POPUP_IDSMember_StartOver=false;
	Boolean  popUp_POPUP_IDSMember_CreateNew=false;
	Boolean  popUp_ContinueEditing_POPUP_IDSMembert=false;


	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) {


		try {

			InvalidLoginData=(String[][]) ExcelUtils.ReadExcelData(2);
			UserName=InvalidLoginData[0][1];
			Password=InvalidLoginData[0][2];

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

	@Test(priority=1)
	public void RequiredFieldValidationFeedbackForm() throws Exception {

		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);

		try {
			if(isUSerLogin!=true){
				existingUserSignIn(UserName, Password);
			}
			Actions action = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
			action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();
			isUSerLogin=true;
		} catch (NoSuchElementException e) {
			printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
			result=false;
			driver.navigate().to(Constant.URL+"#topic/feedback");
		}

		try {
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
			driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
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


	/**
	 * This function requires Fresh/ New data for testing 
	 * It reads third tab of excel sheet for test data
	 * This function reads 1st row as test data
	 * @throws Exception
	 */
	//@Test(priority=2)
	public void submitNewFeedback() throws Exception
	{
		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}


			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			//selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[0][2],FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
			selectOption(driver,feedbackFormData[0][3],FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
			FeedbackAndInsightElements.txtbox_ContactAttributes(driver);

			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[0][4]);
			// select the element from list
			List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options.size()) {
				options.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}

			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();

			}


			Thread.sleep(500);
			TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),feedbackFormData[0][5]);
			//selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).sendKeys(Keys.TAB);
			//selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
			selectOption(driver,feedbackFormData[0][6],FeedbackAndInsightElements.Dropdown_InstitutionTypeButton(),FeedbackAndInsightElements.Dropdown_InstitutionTypeMenu());
			String player= (feedbackFormData[0][7].equalsIgnoreCase("Yes")? "true" :"false");
			String IDS= (feedbackFormData[0][8].equalsIgnoreCase("Yes")? "true" :"false");
			String CurrentState=driver.findElement(FeedbackAndInsightElements.chkbox_payer()).getAttribute("data-checked");
			if(!CurrentState.equalsIgnoreCase(player)){
				driver.findElement(FeedbackAndInsightElements.chkbox_payer()).click();
			}

			String CurrentState1=driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).getAttribute("data-checked");

			if(!CurrentState1.equalsIgnoreCase(IDS)){
				driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).click();
				TypeInField(driver,FeedbackAndInsightElements.txtbox_IDSInput(),feedbackFormData[0][9]);
				driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).sendKeys(Keys.TAB);
			}


			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).click();
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.CONTROL + "a");
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.DELETE);
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys("12/12/12");
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			Thread.sleep(2000);
			driver.navigate().refresh();
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
			if(driver.getCurrentUrl().contains("#home")){
				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();

				if(driver.getCurrentUrl().contains("#home")){
					try {
						driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
					} 
					catch (NoSuchElementException e) {
						printer("LogedInhomepageElement.link_AddFeedback Not found on feedback page");

					}
				}

				TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
				selectOptionWithIndex(0, driver,Constant.UL_ID_1);

				if(driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).getAttribute("value").contains(feedbackFormData[0][1])){
					softAssert.assertTrue(true);
				}
				else{
					softAssert.fail("New added contact Not searched");
				}

				if(driver.findElement(FeedbackAndInsightElements.institutionList()).getAttribute("value").contains(feedbackFormData[0][5])){
					CreateNewInstitution=true;
				}
				else{
					CreateNewInstitution=false;
				}


				if(driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).getAttribute("value").contains(feedbackFormData[0][9])){
					NewAddedIDAInput=true;
				}
				else{
					NewAddedIDAInput=false;
				}
				CurrentState=driver.findElement(FeedbackAndInsightElements.chkbox_payer()).getAttribute("data-checked");
				if(CurrentState.equalsIgnoreCase(player)){
					PayerCheckBox=true;
				}
				else{
					PayerCheckBox=false;
				}

				CurrentState1=driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).getAttribute("data-checked");

				if(CurrentState1.equalsIgnoreCase(IDS)){

					IDSCheckBox=true;
				}
				else{
					IDSCheckBox=false;
				}



				System.out.println("CreateNewInstitution "+CreateNewInstitution);
				System.out.println("NewAddedIDAInput "+NewAddedIDAInput);
				System.out.println("PayerCheckBox "+PayerCheckBox);
				System.out.println("IDSCheckBox "+IDSCheckBox);

			}
			else{

				softAssert.fail("User is not able to select newly created contact attribute");
			}



		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}


	/**
	 * This function requires Fresh/ New data for testing 
	 * It reads third tab of excel sheet for test data
	 * This function reads 2nd row as test data
	 * @throws Exception
	 */
//	@Test(priority=3)
	public void ContactAttribute() throws Exception{

		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}
			//driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[1][1]);
			//selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[1][2],FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
			selectOption(driver,feedbackFormData[1][3],FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
			FeedbackAndInsightElements.txtbox_ContactAttributes(driver);
			// To check if Contact attribute is removable
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),"ToRemove");
			// select the element from list
			List<WebElement> options2= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options2.size()) {
				options2.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}
			List<WebElement> selectedItems= driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("ul"));
			System.out.println(selectedItems.size());
			for (WebElement opt : selectedItems) {
				//System.out.println(opt.getText());
				opt.findElement(By.tagName("a")).click();

			}

			List<WebElement> selectedItems1= driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("ul"));
			if(selectedItems1.size()>0){
				removeContactAttribute=true;
				//System.out.println("Contact attribute removed ");
			}
			else{
				removeContactAttribute=false;
				//System.out.println("Contact attribute Not removed ");
			}


			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[1][4]);
			// select the element from list
			List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options.size()) {
				options.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}

			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[1][4]+"Add multiple");
			// select the element from list
			List<WebElement> options3= driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("ul"));
			int Countsize=0;
			if(0<=options3.size()) {
				options.get(0).click();

			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}
			List<WebElement> options4= driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("li"));
			for (WebElement opt : options4) {
				Countsize++;
				System.out.println("Count Size"+Countsize +" "+opt.getText());
			}
			if(Countsize>1) {
				addmultipleContact=true;
				System.out.println("Added Multiple");
			}
			else{
				addmultipleContact=false;
				System.out.println("Not Added Multiple");
			}



			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();

			}


			Thread.sleep(500);
			TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),feedbackFormData[1][5]);
			selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).sendKeys(Keys.TAB);
			//selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
			selectOption(driver,feedbackFormData[1][6],FeedbackAndInsightElements.Dropdown_InstitutionTypeButton(),FeedbackAndInsightElements.Dropdown_InstitutionTypeMenu());
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			Thread.sleep(2000);
			driver.navigate().refresh();
			wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
			if(driver.getCurrentUrl().contains("#home")){
				newContactAttribute=true;
				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();

				if(driver.getCurrentUrl().contains("#home")){
					try {
						driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
					} catch (NoSuchElementException e) {
						printer("LogedInhomepageElement.link_AddFeedback Not found on feedback page");
						result=false;
					}
				}

				TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[1][4]);
				// select the element from list
				List<WebElement> options1= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
				if(0<=options1.size()) {
					options1.get(0).click();
					selectnewlyaddedContactAttribute=true;
					softAssert.assertTrue(true);
				}


			}
			else{
				newContactAttribute=false;
				selectnewlyaddedContactAttribute=false;
				softAssert.fail("User is not able to select newly created contact attribute");
			}



		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}

	/**
	 * This function requires existing data for testing  which is created newly
	 * It reads 3rd tab of excel sheet for test data
	 * This function reads 1nd row as test data
	 * @throws Exception
	 */
	//@Test(priority=4)
	public void IDSMemberVerification() throws Exception{
		driver.navigate().to(Constant.URL);
		SoftAssert softAssert = new SoftAssert();
		try {


			Actions action = new Actions(driver);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);

			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);


			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.txtbox_IDSInput()));
			driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();

			}
			Thread.sleep(500);
			if(driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).getAttribute("value").isEmpty()){
				POPUP_IDSMember_StartOver=true;
			}
			else{
				POPUP_IDSMember_StartOver=false;
			}
			driver.navigate().refresh();
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.txtbox_IDSInput()));
			driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).click();
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpcreateNew()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpcreateNew()).click();

			}
			Thread.sleep(500);
			if(driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).getAttribute("value").contains(feedbackFormData[0][9])){
				popUp_POPUP_IDSMember_CreateNew=true;
			}
			else{
				popUp_POPUP_IDSMember_CreateNew=false;
			}

			driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).sendKeys(" New");
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.urlContains("#home"));
			if(driver.getCurrentUrl().contains("#home")){
				softAssert.assertTrue(true);
				popUp_POPUP_IDSMember_CreateNew=true;
				existingContactIsUseable=true;
			}
			else{
				popUp_POPUP_IDSMember_CreateNew=false;
				existingContactIsUseable=false;
				softAssert.fail("Form Not submited successfully");
			}



		} catch (Exception e) {
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();     
	}
	
	/**
	 * This function requires existing data for testing  which is created newly
	 * It reads 3rd tab of excel sheet for test data
	 * This function reads 1nd row as test data
	 * @throws Exception
	 */

//	@Test(priority=5)
	public void popUp_ContinueEditing_POPUP_IDSMembert(){

		driver.navigate().to(Constant.URL);
		SoftAssert softAssert = new SoftAssert();
		try {


			Actions action = new Actions(driver);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);

			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);
			driver.navigate().to(Constant.URL);

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}


			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.txtbox_IDSInput()));
			driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpContinueEditing()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpContinueEditing()).click();

			}
			Thread.sleep(500);
			driver.findElement(FeedbackAndInsightElements.txtbox_IDSInput()).sendKeys(" &Edited");
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.urlContains("#home"));
			if(driver.getCurrentUrl().contains("#home")){
				softAssert.assertTrue(true);
			}
			else{
				softAssert.fail("Form Not submited successfully");
			}




		} catch (Exception e) {
			softAssert.fail("Some thing went wronge");
		}
		softAssert.assertAll();    
	}

//	@Test(priority=6)
	public void POPUP_ContactName_StartOver() throws Exception{

		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
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
				submitFeedback(feedbackFormData[0][1],CreateNew,userExistingContact,feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8],feedbackFormData[0][9],feedbackFormData[0][10],feedbackFormData[0][11]);

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



	@Test(priority=7)
	public void POPUP_ContactName_CreateNew() throws Exception{

		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
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
				submitFeedback(feedbackFormData[0][1],CreateNew,userExistingContact,feedbackFormData[0][2],feedbackFormData[0][3],feedbackFormData[0][4],feedbackFormData[0][5],feedbackFormData[0][6],feedbackFormData[0][7],feedbackFormData[0][8],feedbackFormData[0][9],feedbackFormData[0][10],feedbackFormData[0][11]);

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



	@Test(priority=8)
	public void POPUP_Institution_CreateNew() throws Exception{
		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				result=false;
			}

			//driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			//selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[0][2],FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
			selectOption(driver,feedbackFormData[0][3],FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[0][4]);
			// select the element from list
			List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options.size()) {
				options.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}
			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpcreateNew()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpcreateNew()).click();
				POPUP_Institution_StartOver=true;

			}
			Thread.sleep(500);
			TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),feedbackFormData[0][4]);
			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).sendKeys(Keys.TAB);
			//selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
			selectOption(driver,feedbackFormData[0][6],FeedbackAndInsightElements.Dropdown_InstitutionTypeButton(),FeedbackAndInsightElements.Dropdown_InstitutionTypeMenu());
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			if(!driver.getCurrentUrl().contains("#home")){
				softAssert.assertTrue(true);
			}
			else{
				softAssert.fail("User is not able to submit the feebback form when clicked on startover button");
			}


		}
		catch(Exception ex){
			System.out.println(ex.getMessage());

		}


	}

	@Test(priority=9)
	public void POPUP_Institution_ContinueEditing() throws Exception{
		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}
			//driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			//selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[0][2],FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
			selectOption(driver,feedbackFormData[0][3],FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),feedbackFormData[0][4]);
			// select the element from list
			List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options.size()) {
				options.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
			}
			TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),feedbackFormData[0][5]);
			selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());

			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
			//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
			if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpContinueEditing()));
				driver.findElement(FeedbackAndInsightElements.btn_PopUpContinueEditing()).click();


			}
			Thread.sleep(500);

			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).sendKeys(" Edited");
			driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).sendKeys(Keys.TAB);

			selectOption(driver,feedbackFormData[0][6],FeedbackAndInsightElements.Dropdown_InstitutionTypeButton(),FeedbackAndInsightElements.Dropdown_InstitutionTypeMenu());
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.urlContains("#home"));
			if(driver.getCurrentUrl().contains("#home")){
				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),feedbackFormData[0][5]+" Edited");
				selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
				if(driver.findElement(FeedbackAndInsightElements.institutionList()).getAttribute("value").equalsIgnoreCase(feedbackFormData[0][5])){
					softAssert.assertTrue(true);
				}
				else
				{
					softAssert.fail("User edited institution not saved");
				}
			}
			else{
				softAssert.fail("User is not able to submit the feebback form when clicked on Continue Editing button");
			}
			softAssert.assertAll();

		}
		catch(Exception ex){
			System.out.println(ex.getMessage());

		}


	}

//	@Test(priority=10)
	public void POPUP_ContactName_ContinueEdiing() throws Exception{

		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		String[][] feedbackFormData ;
		feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}

			//existingContactIsSearchable
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.txtbox_contactname()));
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][0]);
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




	@Test(priority=11)
	public void POPUP_IDSMember_StartOver(){
		assertTrue(POPUP_IDSMember_StartOver);
	}
	
	@Test(priority=12)
	public void popUp_POPUP_IDSMember_CreateNew(){
		assertTrue(popUp_POPUP_IDSMember_CreateNew);
	}
	
	@Test(priority=13)
	public void RemoveIDSMember() throws Exception{
		driver.navigate().to(Constant.URL);
		SoftAssert softAssert = new SoftAssert();
		try {


			Actions action = new Actions(driver);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);

			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);


			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				//result=false;
			}
			
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
			selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			Thread.sleep(1000);
			
			String IDS= (feedbackFormData[0][8].equalsIgnoreCase("Yes")? "true" :"false");
			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.chkbox_IDS()));
			String CurrentState=driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).getAttribute("data-checked");
			if(CurrentState.equalsIgnoreCase(IDS)){
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.chkbox_IDS()));
				driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).click();
			}
			selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
			driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			
			Thread.sleep(1000);
			wait.until(ExpectedConditions.urlContains("#home"));
			if(driver.getCurrentUrl().contains("#home")){
				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				Thread.sleep(1000);
				driver.manage().deleteAllCookies();
				TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
				selectOptionWithIndex(0, driver,"ui-id-13");
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.chkbox_IDS()));
				 CurrentState=driver.findElement(FeedbackAndInsightElements.chkbox_IDS()).getAttribute("data-checked");
				if(CurrentState.equalsIgnoreCase("false")){
					softAssert.assertTrue(true);
				}
				else
				{
					softAssert.fail("IDS Member Not removed");
					softAssert.assertTrue(false);
				}
			}
			else{
				softAssert.fail("User is not able to submit the feebback form when clicked on Continue Editing button");
			}



		} catch (Exception e) {
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();      
	}

	@Test(priority=14)
	public void selectnewlyaddedContactAttribute(){

		assertTrue(selectnewlyaddedContactAttribute);

	}
	
	@Test(priority=15)
	public void existingContactIsUseable(){
		assertTrue(existingContactIsUseable);
	}
	

	@Test(priority=16)
	public void addmultipleContactAttribute(){

		assertTrue(addmultipleContact);
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(addmultipleContact);
		softAssert.assertAll();
	}

	//@Test(priority=3)
	public void removeContactAttribute(){

		assertTrue(removeContactAttribute);
		/*SoftAssert softAssert = new SoftAssert();
   		softAssert.assertTrue(addmultipleContact);
   		softAssert.assertAll();*/
	}






	//@Test(priority=8)
	public void POPUP_Institution_StartOver() throws Exception{
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(POPUP_Institution_StartOver);	
		softAssert.assertAll();
	}


	//@Test(priority=7)
	public void ExistingInstitutionName(){
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(existinginstitutionIsUseable);
		softAssert.assertAll();
	}




	//@Test(priority=1)	
	public void DateFieldVarivication() throws Exception{
		SoftAssert softAssert = new SoftAssert();
		try {


			Actions action = new Actions(driver);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);

			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);


			try {
				if(isUSerLogin!=true){
					existingUserSignIn(UserName,Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).click().perform();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				result=false;
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.datePicker_IDSInput()));
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).click();
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.CONTROL + "a");
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.DELETE);
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys("12/12/16");
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.TAB);
			Thread.sleep(1000);
			//System.out.println("Manually "+ driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).getText());

			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).clear();
			//driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).;
			DateFormat dateFormat = new SimpleDateFormat("dd"); 
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			String today = dateFormat.format(cal.getTime()); 
			if(today.toCharArray()[0]=='0'){
				today=(String) Character.toString(today.toCharArray()[1]);
			}

			System.out.println(today);
			driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).click();
			driver.findElement(By.linkText(today)).click();
			softAssert.assertTrue(true);


			driver.findElement(FeedbackAndInsightElements.Link_handleContactDetails()).click();
			Thread.sleep(500);
			String LinkText= driver.findElement(FeedbackAndInsightElements.Link_handleContactDetails()).getText();
			System.out.println(LinkText +" Link text");
			boolean ISContactDetailsVisible= driver.findElement(FeedbackAndInsightElements.Div_contact_details()).isDisplayed();
			System.out.println(ISContactDetailsVisible +" Link text");
			if(LinkText.contains("show contact details")){
				System.out.println("IN IF");
				if(!ISContactDetailsVisible){
					IsShowContactDetails=true;
				}
				else{
					IsShowContactDetails=false;
				}
				driver.findElement(FeedbackAndInsightElements.Link_handleContactDetails()).click();
			}




		} catch (Exception e) {
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();     

	}

	//@Test(priority=2)	
	public void IsShowContactDetailsBlock() throws Exception{
		System.out.println(IsShowContactDetails);
		assertTrue(IsShowContactDetails);
	}


	public void TypeInField(WebDriver driver, By eleLocator, String value) throws InterruptedException{
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


	public void selectOption(WebDriver driver,String option,By dropdownLocator,By menuLocator) {
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
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),ContactName);
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

				//driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Date);
				selectOption(driver,Time,FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
				driver.findElement(FeedbackAndInsightElements.btn_Post()).click();

			}	
			else{
				if(!CreateNew){
					driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).clear();
					TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),ContactName);
				}

				driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);



				selectOption(driver,primaryrole,FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
				selectOption(driver,recognitionlevel,FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
				FeedbackAndInsightElements.txtbox_ContactAttributes(driver);
				TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(driver),contactattributes);
				// select the element from list
				List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
				if(0<=options.size()) {
					options.get(0).click();
				}
				else{
					driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes(driver)).sendKeys(Keys.TAB);
				}
				driver.findElement(FeedbackAndInsightElements.txtbox_institutionList()).click();
				//wait.until(ExpectedConditions.presenceOfElementLocated(FeedbackAndInsightElements.popUp_RequiredFieldValidation()));
				if(driver.findElements(FeedbackAndInsightElements.popUp_RequiredFieldValidation()).size()>0){
					Thread.sleep(1000);
					wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_PopUpStartOver()));
					driver.findElement(FeedbackAndInsightElements.btn_PopUpStartOver()).click();
					POPUP_Institution_StartOver=true;

				}
				Thread.sleep(500);
				TypeInField(driver,FeedbackAndInsightElements.txtbox_institutionList(),institution);
				selectOptionWithIndex(0, driver,FeedbackAndInsightElements.institutionList());
				selectOption(driver,institutiontype,FeedbackAndInsightElements.Dropdown_InstitutionTypeButton(),FeedbackAndInsightElements.Dropdown_InstitutionTypeMenu());

				String CurrentState=driver.findElement(FeedbackAndInsightElements.chkbox_payer()).getAttribute("data-checked");
				if(!CurrentState.equalsIgnoreCase(Player)){
					driver.findElement(FeedbackAndInsightElements.chkbox_payer()).click();
				}
				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).click();
				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.CONTROL + "a");
				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.DELETE);
				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys("12/12/12");
				driver.findElement(FeedbackAndInsightElements.datePicker_IDSInput()).sendKeys(Keys.TAB);
				selectOption(driver,Time,FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

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
