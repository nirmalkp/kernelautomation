package pageObject;

import org.openqa.selenium.By;

public class registerPageElements {


	public static By txtbox_Email()
	{
		return By.id("email");
	}

	public static By txtbox_LastName()
	{
		return By.id("nameField");
	}

	public static By txtbox_newPassword()
	{
		return By.id("newPasswordField");
	}

	public static By txtbox_ConfirmPassword()
	{
		return By.id("confirmPasswordField");
	}
	public static By btn_Next()
	{
		return By.id("btn_enter");
	}

	public static By validationMsg_Email()
	{
		return By.xpath("//li[contains(.,'You should enter your email')]");
	}

	public static By validationMsg_LastName()
	{
		return By.xpath("//li[contains(.,'You should enter your name')]");
	}
	public static By validationMsg_NewPassword()
	{
		return By.xpath("//li[contains(.,'Enter your new password')]");
	}
	public static By validationMsg_ConfirmPassword()
	{
		return By.xpath("//li[contains(.,'Confirm password')]");
	}
	
	public static By InvalidPasswordMsg()
	{
		return By.xpath("//li[contains(.,'Please create a strong password that is at least 8 characters long, and includes at least one upper and one lowercase letter.')]");
	}
	public static By InvalidEmailMsg()
	{
		return By.xpath("//li[contains(.,'This value should be a valid email.')]");
	}
	public static By passwordNotMatchMsg()
	{
		return By.xpath("//li[@class='parsley-equalto']");
	}
	
	
}
