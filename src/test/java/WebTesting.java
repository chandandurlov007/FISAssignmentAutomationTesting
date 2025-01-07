import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTesting {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void browserSetUp() {
		driver = WebDriverManager.chromedriver().create();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.ebay.com/");
		driver.manage().window().maximize();
	}

	@Test
	public void testCase1() throws InterruptedException {
		
		WebElement searchBox = driver.findElement(By.id("gh-ac"));
		searchBox.clear();
		searchBox.sendKeys("book");
		WebElement searchButton = driver.findElement(By.id("gh-btn"));
		searchButton.click();
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='s-item__image-section']/div/a/div/img)[3]")));
		WebElement firstBook = driver.findElement(By.xpath("(//div[@class='s-item__image-section']/div/a/div/img)[3]"));
		firstBook.click();

		String currentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		Iterator<String> i = allWindows.iterator();
		while (i.hasNext()) {
			String childwindow = i.next();
			if (!childwindow.equalsIgnoreCase(currentWindow)) {
				driver.switchTo().window(childwindow);
				System.out.println("The child window is " + childwindow);
			}
		}
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Add to cart']")));
		
		WebElement addToCartButton = driver.findElement(By.xpath("//span[text()='Add to cart']"));
		addToCartButton.click();
		

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@id='gh-cart-n']")));
		
		WebElement cartItemNo = driver.findElement(By.xpath("//i[@id='gh-cart-n']"));
		String itemNo = cartItemNo.getText();
		Assert.assertEquals(1,Integer.parseInt(itemNo),"Expected count is "+1+"But Actual count displays as "+itemNo);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
