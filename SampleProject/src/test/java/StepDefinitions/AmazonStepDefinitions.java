package StepDefinitions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonStepDefinitions {

	private WebDriver driver;
	private List<String> productPrices = new ArrayList<>();


	@Given("I am on the Amazon homepage")
	public void i_am_on_the_homepage() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/?ref_=icp_country_from_us");

	}

	@When("I search for {string}")
	public void i_search_for(String searchTerm) {
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchTerm + "\n");

	}

	@When("I add two products to the cart")
	public void i_add_two_products_to_the_cart()
	{
		List<WebElement> products = driver.findElements(By.xpath("//div[@class='a-section a-spacing-base']"));
		int productsAdded = 0;


		for(int i=0;i<products.size() && productsAdded<2; i++)
		{
			WebElement product = products.get(i);

			String price = product.findElement(By.xpath("//div[contains(@class,'a-section a-spacing-small puis-padding-left')]//span[@class='a-price-whole']")).getText();

			productPrices.add(price);

			//product.findElement(By.xpath(".//h2/a")).click();


			WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement addToCartButton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add to')]")));

			addToCartButton.click();
			productsAdded++;

		}	

	}

	@Then("I should see the prices in the cart")
	public void i_should_see_the_prices_in_the_cart()
	{
		driver.findElement(By.id("nav-cart")).click();
		String pageSource = driver.getPageSource();

		for(String price : productPrices)
		{
			Assert.assertTrue(pageSource.contains(price), "Price not found in cart: " + price);

		}
		driver.quit();
	}	
}
