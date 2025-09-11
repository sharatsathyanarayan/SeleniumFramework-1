package frameworkone.base.test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import frameworkone.base.classes.BaseTest;
import frameworkone.base.classes.PageDriver;

public class TestLogin_negative extends BaseTest {

	WebDriver driver = null;

	@Test
	public void loginNegativeTest() {

		driver = PageDriver.getDriver();

		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("wrong_password");
		driver.findElement(By.id("login-button")).click();
		WebElement productsLogo = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[data-test='title']")));

		assertEquals(productsLogo.getText().toString(), "Products");

	}

}
