package utility;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.google.common.collect.ImmutableMap;

import pageObject.homePageElements;
import pageObject.loginPageElements;



public class Commons extends Constant {



	public static void printer(String message){
		System.out.println(message);
	}

	public static void selectOptionWithIndex(int indexToSelect,WebDriver driver,String UL_ID) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement autoOptions = driver.findElement(By.id(UL_ID));
			wait.until(ExpectedConditions.visibilityOf(autoOptions));

			List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
			if(indexToSelect<=optionsToSelect.size()) {
				optionsToSelect.get(indexToSelect).click();
			}
		} 		
		catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

	}


	public static int selectOptionWithIndex(int indexToSelect,WebDriver driver,By UL_ID) {

		int result=0;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement autoOptions = driver.findElement(UL_ID);
			wait.until(ExpectedConditions.visibilityOf(autoOptions));

			List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
			if(indexToSelect<=optionsToSelect.size()) {
				optionsToSelect.get(indexToSelect).click();
				result= 1;
			}
			else
			{
				result= 0;
			}
		} 		
		catch (NoSuchElementException e) {
			System.out.println(e.getStackTrace());
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return result;
	}

	public void existingUserSignIn(WebDriver driver, String Email,String Passward){
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


	@BeforeSuite
	public void setsysproperties() throws IOException{
		
		System.out.println(System.getProperty("os.name"));
		String OS= System.getProperty("os.name");
		if(OS.contains("Windows")){
			System.out.println("Windows");
			System.setProperty("webdriver.chrome.driver", "D:\\Installables\\Drivers\\Newfolder\\chromedriver.exe");
			System.setProperty("webdriver.ie.driver", "D:\\Installables\\Drivers\\Newfolder\\IEDriverServer.exe");
		}
		else{
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File("/usr/bin/chromedriver"))	
					.usingAnyFreePort()
					.withEnvironment(ImmutableMap.of("DISPLAY",":2"))
					.build();
					service.start();
		}
	}


}
