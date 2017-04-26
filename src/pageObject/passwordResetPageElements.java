package pageObject;

import org.openqa.selenium.By;

public class passwordResetPageElements {
	
	
	public static By link_Login()
	{
		return By.xpath("//a[@href='#login']");
	}
	public static By text_PasswordReset()
	{
		return By.xpath("//h3[contains(.,'Password Reset')]");
	}
	public static By txtbox_emailInput()
	{
		return By.id("emailInput");
	}
	public static By btn_SendResetRequeset()
	{
		return By.id("passreset-btn");
	}
	
	public static By text_SendResetRequesetSuccess()
	{
		return By.xpath("//label[contains(.,'A message with the steps to reset the password has been sent to: ')]");
	}
	
	
	public static By EmailRequiredMsg()
	{
		return By.xpath("//li[contains(.,'please enter your email')]");
	}
	
	public static By Reset_EmailValiMsg()
	{
		return By.xpath("//li[contains(.,'Please, enter a valid email.')]");
	}
	
	
}
