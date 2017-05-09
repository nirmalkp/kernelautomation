package automationFramework;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.collect.ImmutableMap;

import pageObject.FeedbackAndInsightElements;
import pageObject.LogedInhomepageElement;
import utility.Commons;
import utility.Constant;
import utility.ExcelUtils;

public class CancelLinkOnFeedback extends Commons {

	Boolean USerPrompedForConfirmPopup=false;
	Boolean YesDeleteFeedbackButtonClick=false;
	Boolean NoEmptyFieldForConfirmPopup=false;
	Boolean PopupCloseButton=false;
	Boolean NoKeepMeHereButtonClick=false;
	String[][] InvalidLoginData;
	String UserName="";
	String Password="";
	Boolean isUSerLogin=false;
	public WebDriver driver=null;
	Boolean removeTopic=false;
	Boolean	FeedbackInsightsection=false;
	Boolean userIsAbletosubmitFormWithQuestion=false;
	Boolean userIsAbletosubmitFormWithMultipletopic=false;
	Boolean Answer_ReplyClick=false;
	Boolean Not_discussedClick=false;
	Boolean UserisabletoReplywithoutenteringMoreDeatils=false;
	Boolean UserClicksonReplyandthenClicksonNotDiscussed =false;
	

	@Parameters({ "browser" })
	@BeforeClass
	public void launchBrowser(String browser) throws IOException {


		try {

			InvalidLoginData=(String[][]) ExcelUtils.ReadExcelData(2);
			UserName=InvalidLoginData[0][1];
			Password=InvalidLoginData[0][2];

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

/*
	@Test(priority=1)
	public void EmptyFieldCancelButtonClick() throws Exception {
		driver.navigate().to(Constant.URL);
		Actions action = new Actions(driver);
		SoftAssert softAssert = new SoftAssert();
		Boolean result=true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		
		
		try{

			try {
				if(isUSerLogin!=true){
					existingUserSignIn(driver,UserName, Password);
				}

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
				driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
				isUSerLogin=true;
			} catch (NoSuchElementException e) {
				printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
				driver.navigate().to(Constant.URL+"#topic/feedback");
				result=false;
			}

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.Cancellink_Click()));
			try {
				driver.findElement(FeedbackAndInsightElements.Cancellink_Click()).click();
				USerPrompedForConfirmPopup=true;
				softAssert.assertTrue(true);
			} catch (Exception e) {
				USerPrompedForConfirmPopup=false;
				softAssert.fail("User Not prompted for Confirmation Popup when clivked on Cancel Link");
			}


			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.CancelPopup_Close()));
			try {
				driver.findElement(FeedbackAndInsightElements.CancelPopup_Close()).click();
				PopupCloseButton=true;
			} catch (Exception e) {
				PopupCloseButton=false;
			}

			driver.navigate().refresh();

			TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),"Entry For Testing");
			driver.findElement(FeedbackAndInsightElements.txtbox_contactname()).sendKeys(Keys.TAB);


			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.Cancellink_Click()));
			try {
				driver.findElement(FeedbackAndInsightElements.Cancellink_Click()).click();
				NoEmptyFieldForConfirmPopup=true;
			} catch (Exception e) {
				softAssert.fail("User Not prompted for Confirmation Popup when clivked on Cancel Link");
				NoEmptyFieldForConfirmPopup=false;
			}
			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.CancelPopup_NokeepMeHereButton()));
			try {
				driver.findElement(FeedbackAndInsightElements.CancelPopup_NokeepMeHereButton()).click();
				NoKeepMeHereButtonClick=true;
			} catch (Exception e) {
				softAssert.fail("User Not prompted for Confirmation Popup when clivked on Cancel Link");
				NoKeepMeHereButtonClick=false;
			}
			driver.navigate().refresh();

			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.Cancellink_Click()));
			try {
				driver.findElement(FeedbackAndInsightElements.Cancellink_Click()).click();
			} catch (Exception e) {
				softAssert.fail("User Not prompted for Confirmation Popup when clivked on Cancel Link");
			}
			wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.CancelPopup_YesDeleteFeedbackButton()));
			try {
				driver.findElement(FeedbackAndInsightElements.CancelPopup_YesDeleteFeedbackButton()).click();
				YesDeleteFeedbackButtonClick=true;
			} catch (Exception e) {
				softAssert.fail("User Not prompted for Confirmation Popup when clivked on Cancel Link");
				YesDeleteFeedbackButtonClick=false;
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			softAssert.assertTrue(false);;
		}
		softAssert.assertTrue(result);
		softAssert.assertAll();

	}
	@Test(priority=2)
	public void ConfirmationPopupClose() {
		assertTrue(PopupCloseButton);
	}
	@Test(priority=3)
	public void USerPrompedForConfirmPopup() {
		assertTrue(USerPrompedForConfirmPopup);
	}
	@Test(priority=4)
	public void USerEnteredDataAndClickedOnCancelButton() {
		assertTrue(NoEmptyFieldForConfirmPopup);
	}
	@Test(priority=4)
	public void NoKeepMeHereButtonClick() {
		assertTrue(NoKeepMeHereButtonClick);
	}
	@Test(priority=4)
	public void YesDeleteFeedbackButtonClick() {
		assertTrue(YesDeleteFeedbackButtonClick);
	}

	

@Test(priority=5)
	  public void WhatTopicsWereDiscussedTest() throws Exception {
			driver.navigate().to(Constant.URL);
			Actions action = new Actions(driver);
			SoftAssert softAssert = new SoftAssert();
			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
			
			try{

				try {
					if(isUSerLogin!=true){
						existingUserSignIn(driver,UserName, Password);
					}

					wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
					action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

					wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
					driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
					isUSerLogin=true;
				} catch (NoSuchElementException e) {
					printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
					driver.navigate().to(Constant.URL+"#topic/feedback");
					result=false;
				}
				
				TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
				selectOptionWithIndex(0, driver,Constant.UL_ID_1);
				selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());
								
				TypeInField(driver,FeedbackAndInsightElements.txtbox_TopicDiscussed(driver),feedbackFormData[0][13]);
				List<WebElement> options2= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
				
				if(0<=options2.size()) {
					options2.get(0).click();
				}
				else{
					driver.findElement(FeedbackAndInsightElements.txtbox_TopicDiscussed(driver)).sendKeys(Keys.TAB);
					
				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()));
				Thread.sleep(1000);
				
				
				if(   	   driver.findElement(FeedbackAndInsightElements.Section_fieldInsights()).isDisplayed() &&
						   driver.findElements(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).size()>0 ){
					System.out.println("text"+ driver.findElement(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).getText());		
					FeedbackInsightsection=true;
							System.out.println("FeedbackAndInsight section test: PASS");
						}
						else{
							FeedbackInsightsection=false;
							System.out.println("FeedbackAndInsight section test: FAIL");
						}
				
				List<WebElement> selectedItems= driver.findElement(By.id("s2id_topics")).findElements(By.tagName("ul"));
				System.out.println(selectedItems.size());
				for (WebElement opt : selectedItems) {
					//System.out.println(opt.getText());
					opt.findElement(By.tagName("a")).click();

				}
				
				List<WebElement> selectedItems1= driver.findElement(By.id("s2id_topics")).findElements(By.tagName("ul"));
				if(selectedItems1.size()>0){
					removeTopic=true;
					System.out.println("Contact attribute removed ");
				}
				else{
					removeTopic=false;
					System.out.println("Contact attribute Not removed ");
				}
				
				
				System.out.println("Section_questionsFeedback"+driver.findElement(FeedbackAndInsightElements.Section_questionsFeedback()).isDisplayed());
				System.out.println( "Section_fieldInsights"+driver.findElement(FeedbackAndInsightElements.Section_fieldInsights()).isDisplayed());
				System.out.println( "Section_ToicAndQuestionInfo"+  driver.findElement(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).isDisplayed());
				System.out.println("text"+ driver.findElement(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).getText());	
				
				TypeInField(driver,FeedbackAndInsightElements.txtbox_TopicDiscussed(driver),feedbackFormData[0][13]);
				List<WebElement> options1= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
				if(0<=options1.size()) {
					options2.get(0).click();
				}
				else{
					driver.findElement(FeedbackAndInsightElements.txtbox_TopicDiscussed(driver)).sendKeys(Keys.TAB);
					
				}
				
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
				driver.findElement(FeedbackAndInsightElements.btn_Post()).click();
				Thread.sleep(2000);
				driver.navigate().refresh();
				wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
				if(driver.getCurrentUrl().contains("#home")){
					userIsAbletosubmitFormWithQuestion=true;
				}
				else{
					userIsAbletosubmitFormWithQuestion=false;
				}
				
			}
			catch (Exception e) {
				softAssert.assertTrue(false);;
			}
			softAssert.assertTrue(result);
			softAssert.assertAll();
			
	  }
	*/
	@Test(priority=6)
	  public void SelectingMultipleTopic() throws Exception {
			driver.navigate().to(Constant.URL);
			Actions action = new Actions(driver);
			SoftAssert softAssert = new SoftAssert();
			Boolean result=true;
			WebDriverWait wait = new WebDriverWait(driver, 100);
			String[][] feedbackFormData ;
			feedbackFormData=(String[][]) ExcelUtils.ReadExcelData(3);
			String[] topics=feedbackFormData[0][12].split(",");
			for(int i=0;i<topics.length;i++){
					topics[i]=topics[i].substring(1, topics[i].length()-1);
					//System.out.println(topics[i]);
			}
			
			try{

				try {
					if(isUSerLogin!=true){
						existingUserSignIn(driver,UserName, Password);
					}

					wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.btn_CreateButton()));
					action.moveToElement(driver.findElement(LogedInhomepageElement.btn_CreateButton())).perform();

					wait.until(ExpectedConditions.elementToBeClickable(LogedInhomepageElement.link_AddFeedback()));
					driver.findElement(LogedInhomepageElement.link_AddFeedback()).click();
					isUSerLogin=true;
				} catch (NoSuchElementException e) {
					printer("LogedInhomepageElement.btn_CreateButton Not found on feedback page");
					driver.navigate().to(Constant.URL+"#topic/feedback");
					result=false;
				}
				
				TypeInField(driver,FeedbackAndInsightElements.txtbox_contactname(),feedbackFormData[0][1]);
				selectOptionWithIndex(0, driver,Constant.UL_ID_1);
				
				selectOption(driver,feedbackFormData[0][11],FeedbackAndInsightElements.dropdown_timespent(),FeedbackAndInsightElements.dropdown_timeSpentMenu());
								
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.dropdown_timespent()));
				
				/*System.out.println("Section_questionsFeedback"+driver.findElement(FeedbackAndInsightElements.Section_questionsFeedback()).isDisplayed());
				System.out.println( "Section_fieldInsights"+driver.findElement(FeedbackAndInsightElements.Section_fieldInsights()).isDisplayed());
				System.out.println( "Section_ToicAndQuestionInfo"+  driver.findElement(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).isDisplayed());
				System.out.println("text"+ driver.findElement(FeedbackAndInsightElements.Section_ToicAndQuestionInfo()).getText());	*/
				//Thread.sleep(5000);
				for(int i=0;i<topics.length;i++){
					TypeInField(driver,FeedbackAndInsightElements.txtbox_TopicDiscussed(driver),topics[i]);
					List<WebElement> options1= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
					if(0<=options1.size()) {
						options1.get(0).click();
					}
					else{
						driver.findElement(FeedbackAndInsightElements.txtbox_TopicDiscussed(driver)).sendKeys(Keys.TAB);
						
					}
					Thread.sleep(100);
				}
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(FeedbackAndInsightElements.Section_AnswerChoice()));
				
	
				List<WebElement> answerList=driver.findElements(FeedbackAndInsightElements.Section_AnswerChoice());
				// Click on Replly
				try {
					List<WebElement> answerList1=answerList.get(0).findElements(By.tagName("a"));
					answerList1.get(0).click();
					Thread.sleep(5000);
					Answer_ReplyClick=true;
					UserisabletoReplywithoutenteringMoreDeatils =  answerList.get(0).findElement(By.className("pollResponse-body")).isDisplayed();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					Answer_ReplyClick=false;
					UserisabletoReplywithoutenteringMoreDeatils=false;
				}
				
				try {
					List<WebElement> answerList2=answerList.get(1).findElements(By.tagName("a"));
					answerList2.get(1).click();
					Thread.sleep(5000);
					Not_discussedClick=true;
					Thread.sleep(5000);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					Not_discussedClick=false;
				}
				
				try {
					List<WebElement> answerList3=answerList.get(2).findElements(By.tagName("a"));
					/*System.out.println(answerList3.size()+"Size");
					System.out.println(answerList3.get(0).getText() +"Text 1");
					System.out.println(answerList3.get(1).getText() +"Text 2");*/
					wait.until(ExpectedConditions.elementToBeClickable(answerList3.get(0)));
					answerList3.get(0).click();
					answerList3=answerList.get(2).findElements(By.tagName("a"));
					wait.until(ExpectedConditions.elementToBeClickable(answerList3.get(0)));
					answerList3.get(0).click();
					UserClicksonReplyandthenClicksonNotDiscussed=true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					UserClicksonReplyandthenClicksonNotDiscussed=false;
				}
				
				
				Thread.sleep(1000);
				List<WebElement> InsightsElements=driver.findElement(FeedbackAndInsightElements.Section_fieldInsights()).findElements(By.tagName("li"));
				
				if(InsightsElements.size()>0){
					Thread.sleep(1000);
					List<WebElement> InsightsRow1Topic=InsightsElements.get(0).findElements(By.tagName("input"));
					List<WebElement> InsightsRow1Comment=InsightsElements.get(0).findElements(By.tagName("textarea"));
					Thread.sleep(1000);
					InsightsRow1Topic.get(0).sendKeys(topics[0]);
					List<WebElement> options1= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
					if(0<=options1.size()) {
						options1.get(0).click();
					}
					else{
						driver.findElement(FeedbackAndInsightElements.txtbox_TopicDiscussed(driver)).sendKeys(Keys.TAB);
						
					}
					InsightsRow1Topic.get(0).sendKeys(topics[1]);
					 options1= driver.findElement(By.id("select2-drop")).findElements(By.tagName("ul"));
					if(0<=options1.size()) {
						options1.get(0).click();
						Thread.sleep(2000);
						List<WebElement> selectedItems= driver.findElement(By.id("s2id_insight")).findElements(By.tagName("ul"));
						System.out.println(selectedItems.size());
						for (WebElement opt : selectedItems) {
							//System.out.println(opt.getText());
							opt.findElement(By.tagName("a")).click();

						}
					}
					else{
						driver.findElement(FeedbackAndInsightElements.txtbox_TopicDiscussed(driver)).sendKeys(Keys.TAB);
						
					}
					Thread.sleep(1000);
					InsightsRow1Comment.get(0).sendKeys(feedbackFormData[0][1]);
					
				}
				
				
				wait.until(ExpectedConditions.elementToBeClickable(FeedbackAndInsightElements.btn_Post()));
				driver.findElement(FeedbackAndInsightElements.btn_Post()).click();
				Thread.sleep(2000);
				
				wait.until(ExpectedConditions.urlContains("#home"));
				//driver.navigate().refresh();
				if(driver.getCurrentUrl().contains("#home")){
					userIsAbletosubmitFormWithMultipletopic=true;
				}
				else{
					userIsAbletosubmitFormWithMultipletopic=false;
				}
				
			}
			catch (Exception e) {
				softAssert.assertTrue(false);;
			}
			softAssert.assertTrue(result);
			softAssert.assertAll();
			
	  }
	
@Test(priority=7)
	public void UserClicksonReplyandthenClicksonNotDiscussed() {
		assertTrue(UserClicksonReplyandthenClicksonNotDiscussed);
	}
@Test(priority=8)
public void UserisabletoReplywithoutenteringMoreDeatils() {
	assertTrue(UserisabletoReplywithoutenteringMoreDeatils);
}
@Test(priority=8)
		public void Answer_ReplyClick() {
			assertTrue(Answer_ReplyClick);
		}
@Test(priority=10)
		public void Not_discussedClick() {
			assertTrue(Not_discussedClick);
		}
@Test(priority=11)
	public void removeTopic() {
		assertTrue(removeTopic);
	}
@Test(priority=12)
public void userIsAbletosubmitFormWithQuestion() {
	assertTrue(userIsAbletosubmitFormWithQuestion);
}
@Test(priority=13)
public void FeedbackInsightsectionHideSowOnSelection() {
	assertTrue(FeedbackInsightsection);
}


}
