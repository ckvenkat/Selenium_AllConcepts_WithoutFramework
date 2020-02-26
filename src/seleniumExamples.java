import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class seleniumExamples {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver;
		String radOption = "radio3";
		int checkBoxCount = 3;
		int dropDownValue = 2;
		int row = 3;
		int col = 2;
		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\Drivers_Automation\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		driver.manage().window().maximize();
		//WebDriverWait wait = new WebDriverWait(driver, 120);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String parentHandle = driver.getWindowHandle();
		System.out.println("parentWindow:" + parentHandle);

		/* Radio Button */
		radioButton(radOption, driver);

		/* TextBox */
		driver.findElement(By.id("autocomplete")).sendKeys("Mithraa");

		/* DropDown */
		driver.findElement(By.xpath("//select[@id=\"dropdown-class-example\"]")).click();
		driver.findElement(By.xpath("//option[@value=\"option" + dropDownValue + "\"]")).click();

		/* CheckBox */
		checkBox(checkBoxCount, driver);

		/* SwitchWindow */
		driver.findElement(By.id("openwindow")).click();
		Set<String> allHandles = driver.getWindowHandles();

		int handleSize = allHandles.size();
		System.out.println("All Handles:" + handleSize);
		allHandles.remove(allHandles.iterator().next());

		String lastHandle = allHandles.iterator().next();
		System.out.println("last window handle" + lastHandle);

		driver.switchTo().window(lastHandle);
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		Assert.assertEquals(driver.getTitle(),
				"QA Click Academy | Selenium,Jmeter,SoapUI,Appium,Database testing,QA Training Academy");
		Thread.sleep(10000);
		if (driver.findElement(By.xpath("(//div[@class=\"sumome-react-wysiwyg-move-handle\"]//button)[2]"))
				.isDisplayed()) {
			Actions actions = new Actions(driver);
			actions.moveToElement(
					driver.findElement(By.xpath("(//div[@class=\"sumome-react-wysiwyg-move-handle\"]//button)[2]")))
					.click().perform();
		}
		driver.close();
		driver.switchTo().window(parentHandle);

		/* Switch Tab */

		driver.findElement(By.id("opentab")).click();
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class=\"col-md-6 text-left\"]//span)[2]")).getText(),
				"World class Tutorials on Selenium, Rest Assured, Protractor, SoapUI, Appium, Cypress, Jmeter, Cucumber, Jira and many more!");
		driver.close();
		driver.switchTo().window(tabs2.get(0));

		/* Alert */

		driver.findElement(By.id("name")).sendKeys("Mithraa");
		driver.findElement(By.id("alertbtn")).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();

		/* Confirm */

		driver.findElement(By.id("name")).sendKeys("Mithraa");
		driver.findElement(By.id("confirmbtn")).click();
		System.out.println(driver.switchTo().alert().getText());
		driver.switchTo().alert().accept();

		/* Web Table */

		System.out.println(
				driver.findElement(By.xpath("//table[@id=\"product\"]//tr[" + row + "]/td[" + col + "]")).getText());

		/* Hide/Show */

		driver.findElement(By.id("hide-textbox")).click();
		Assert.assertFalse(driver.findElement(By.id("displayed-text")).isDisplayed());
		driver.findElement(By.id("show-textbox")).click();
		Assert.assertTrue(driver.findElement(By.id("displayed-text")).isDisplayed());

		/* Mouse Hover */

		Actions action = new Actions(driver);
		WebElement mh = driver.findElement(By.id("mousehover"));
		action.moveToElement(mh)
				.moveToElement(driver.findElement(By.xpath("//div[@class=\"mouse-hover-content\"]/a[@href=\"#top\"]")))
				.click().build().perform();
		Assert.assertEquals(driver.getCurrentUrl(), "https://rahulshettyacademy.com/AutomationPractice/#top");

		/* iFrame */
		System.out.println(driver.findElements(By.tagName("iframe")).size());
		try {
			driver.switchTo().frame("courses-iframe");
		} catch (Exception e) {
		}

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("//div[@class=\"owl-item active\"]//a[contains(text(),\"Consulting\")]")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class=\"owl-item active\"]//a[contains(text(),\"Consulting\")]")).click();
		Assert.assertEquals((driver.findElement(By.xpath("(//p[@class=\"text-center\"])[1]")).getText()),
				"This should be chosen if you or your team is blocked or need support in solving one specific Automation challenge in your project");
		driver.switchTo().parentFrame();
		radOption = "radio2";

		/* Radio Button after returning to parent frame */
		radioButton(radOption, driver);

		driver.close();
	}

	public static void radioButton(String radioOption, WebDriver driver) {
		if (!driver.findElement(By.xpath("//input[@value='" + radioOption + "']")).isSelected()) {
			driver.findElement(By.xpath("//input[@value='" + radioOption + "']")).click();
		}
	}

	public static void checkBox(int checkBox, WebDriver driver) {
		for (int i = 1; i <= checkBox; i++) {
			driver.findElement(By.xpath("//input[@id=\"checkBoxOption" + i + "\"]")).click();
		}

	}

}
