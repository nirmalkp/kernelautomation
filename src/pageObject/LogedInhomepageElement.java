package pageObject;

import org.openqa.selenium.By;

public class LogedInhomepageElement {

	public static By btn_CreateButton()
	{
		return By.id("createLink");
	}
	
	public static By link_AddFeedback()
	{
		return By.xpath("//a[contains(.,'Add Feedback')]");
	}
	
	
}
