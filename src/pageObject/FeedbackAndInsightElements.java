package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class FeedbackAndInsightElements  {
	
	static  String  ContactAttributeId;

	public static By txtbox_contactname()
	{
		return By.id("contacts");
	}

	public static By Link_handleContactDetails()
	{
		return By.id("handleContactDetails");
	}

	public static By Dropdown_primaryrole()
	{
		return By.id("primaryRoleButton");
	}
	public static By Dropdown_recognitionlevel()
	{
		return By.id("recognitionLevelButton");
	}

	public static By txtbox_ContactAttributes(WebDriver driver)
	{
		ContactAttributeId= driver.findElement(By.id("s2id_attributes")).findElement(By.tagName("input")).getAttribute("id");
		return By.id(ContactAttributeId);
	}
	/*public static void txtbox_ContactAttributes(WebDriver driver)
	{
		ContactAttributeId= driver.findElement(By.id("s2id_attributes")).findElement(By.tagName("input")).getAttribute("id");
		//System.out.println(ContactAttributeId);
	}*/

	public static By Dropdown_InstitutionTypeButton()
	{
		return By.id("institutionTypeButton");
	}
	public static By Dropdown_InstitutionTypeMenu()
	{
		return By.id("institutionTypeMenu");
	}
	
	public static By chkbox_payer()
	{
		return By.id("payer");
	}

	public static By chkbox_IDS()
	{
		return By.id("ids");
	}

	public static By txtbox_IDSInput()
	{
		return By.id("idsInput");
	}

	public static By datePicker_IDSInput()
	{
		return By.id("dueDate");
	}

	public static By txt_TopicDiscussed()
	{
		return By.id("s2id_autogen2");
	}

	public static By btn_Post()
	{
		return By.id("postAction");
	}

	public static By link_Cancel()
	{
		return By.linkText("cancel");
	}
	public static By popUp_RequiredFieldValidationMsg()
	{
		return By.xpath("//div[@class='k-dialog-body']");
	}
	public static By popUp_RequiredFieldValidation()
	{
		return By.className("k-dialog");
	}

	public static By btn_PopUpOK()
	{
		return By.id("cancel");
	}

	public static By error_RequiredField()
	{
		return By.className("error");
	}


	public static By btn_PopUpStartOver()
	{
		return By.id("startOver");
	}

	public static By btn_PopUpcreateNew()
	{
		return By.id("createNew");
	}

	public static By btn_PopUpContinueEditing()
	{
		return By.id("continueEditing");
	}

	public static By dropdown_primaryRoleMenu()
	{
		return By.id("primaryRoleMenu");
	}
	public static By dropdown_recognitionLevelMenu()
	{
		return By.id("recognitionLevelMenu");
	}
	
	public static By institutionList()
	{
		return By.id("ui-id-2");
	}
	
	public static By txtbox_institutionList()
	{
		return By.id("institutions");
	}
	
	public static By dropdown_timespent()
	{
		return By.id("timeSpentButton");
	}
	public static By dropdown_timeSpentMenu()
	{
		return By.id("timeSpentMenu");
	}
	
	public static By Div_contact_details()
	{
		return By.id("contact-details");
	}	
	
	public static By Cancellink_Click(){

		return By.xpath("//a[contains(.,'cancel')]");

	}
	
	public static By CancelPopup_Close(){

		return By.xpath("//button[@class='k-button k-modal-close']");

	}

	public static By CancelPopup_NokeepMeHereButton(){

		return By.id("cancel");

	}

	public static By CancelPopup_YesDeleteFeedbackButton(){

		return By.id("deleteFedback");

	}

	public static By txtbox_TopicDiscussed(WebDriver driver)
	{
	String id= driver.findElement(By.id("s2id_topics")).findElement(By.tagName("input")).getAttribute("id");
		return By.id(id);
	}
	public static By TopicDiscussed_Menu()
	{
		return By.id("s2id_topics");
	}
	
	public static By Section_questionsFeedback()
	{
		return By.id("questionsFeedback");
	}
	
	public static By Section_fieldInsights()
	{
		return By.id("fieldIntelligence");
	}
	
	public static By Section_ToicAndQuestionInfo()
	{
		return By.xpath("//div[@class='feedbackInfo']");
	}
	
	public static By Section_AnswerChoice()
	{
		return By.xpath("//div[@class='choiceContent']");
	}
	
	public static By txt_insightTxtArea()
	{
		return By.id("insightTxtArea");
	}
	
	
}
