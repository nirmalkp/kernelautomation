package utility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



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

}
