package automationFramework;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObject.FeedbackAndInsightElements;
import pageObject.LogedInhomepageElement;
import utility.Commons;
import utility.ExcelUtils;

public class ContactAttributesTest extends FeedbackAndInsightsTest {
	
	public WebDriver driver=null;
	String[][] FeedbackFormData ;
	Boolean isUSerLogin=false;
	
	String[][] InvalidLoginData;
	String UserName="";
	String Password="";


	//@Test(priority=1)
	public void ContactAttribute123() throws Exception{

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
			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[1][1]);
			//selectOptionWithIndex(0, driver,Constant.UL_ID_1);
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);
			selectOption(driver,feedbackFormData[1][2],FeedbackAndInsightElements.Dropdown_primaryrole(),FeedbackAndInsightElements.dropdown_primaryRoleMenu());
			selectOption(driver,feedbackFormData[1][3],FeedbackAndInsightElements.Dropdown_recognitionlevel(),FeedbackAndInsightElements.dropdown_recognitionLevelMenu());
			FeedbackAndInsightElements.txtbox_ContactAttributes(driver);
			// To check if Contact attribute is removable
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(),"ToRemove");
			// select the element from list
			List<WebElement> options2= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options2.size()) {
				options2.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes()).sendKeys(Keys.TAB);
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

			
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(),feedbackFormData[1][4]);
			// select the element from list
			List<WebElement> options= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
			if(0<=options.size()) {
				options.get(0).click();
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes()).sendKeys(Keys.TAB);
			}
			
			TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(),feedbackFormData[1][4]+"Add multiple");
			// select the element from list
			List<WebElement> options3= driver.findElement(By.id("s2id_attributes")).findElements(By.tagName("ul"));
			int Countsize=0;
			if(0<=options3.size()) {
				options.get(0).click();
				
			}
			else{
				driver.findElement(FeedbackAndInsightElements.txtbox_ContactAttributes()).sendKeys(Keys.TAB);
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
			selectOption(driver,feedbackFormData[1][9],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());

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

				TypeInField(driver,FeedbackAndInsightElements.txtbox_ContactAttributes(),feedbackFormData[1][4]);
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
    
}
