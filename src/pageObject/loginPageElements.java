package pageObject;

import org.openqa.selenium.By;

public class loginPageElements {

	public static By txtbox_Email()
	{
		return By.id("emailInput");
	}

	public static By txtbox_Password()
	{
		return By.id("passInput");
	}

	public static By button_Enter()
	{
		return By.id("signin-enter");
	}

	public static By link_SignUp()
	{
		return By.xpath("//a[@href='#register']");
	}

	public static By link_ForgotMyPassword()
	{
		return By.xpath("//a[@href='#passwordreset']");
	}

	public static By login_Element()
	{
		return By.id("createLink");
	}

	public static By login_EmailValiMsg()
	{
		return By.xpath("//li[contains(.,'This value should be a valid email.')]");
	}

	public static By AccountDoesNotExistMsg()
	{
		return By.xpath("//li[contains(.,'This combination of email and password is not in our records. Please try again.')]");
	}

	public static By incorrectPasswordMsg()
	{
		return By.xpath("//li[contains(.,'This combination of email and password is not in our records. Please try again.')]");
	}

	public static By PasswordRequiredMsg()
	{
		return By.xpath("//li[contains(.,'please enter your password')]");
	}

	public static By EmailRequiredMsg()
	{
		return By.xpath("//li[contains(.,'please enter your email')]");
	}

	public static By LoginPage_SignUpText()
	{
		return By.xpath("//h3[contains(.,'Sign Up')]");
	}

	public static By Link_ForgotMyPassword()
	{
		return By.xpath("//a[@href='#passwordreset']");
	}
	
	public static By Link_Logout()
	{
		return By.xpath("//a[@href='#logout']");
	}
	
	public static By Link_profileInitials()
	{
		return By.id("profileInitials");
	}
	
	

}
