package ApiTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.cucumber.java.en.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

public class DeviceApiTest {

	private static final String BASE_URL = "https://api.restful-api.dev/objects";
	private String responseBody;
	private String id;
	private String createdAt;
	private String name;
	private int year;
	private double price;

	@When("I add a new device with name {string}, year {int}, price {double}, CPU model {string}, and hard disk size {string}")
	public void i_add_a_new_device(String name, int year, double price, String cpuModel, String hardDiskSize) {



		// Sample Request Payload
		String requestBody = String.format(
				"{ \"name\": \"%s\", \"data\": { \"year\": %d, \"price\": %.2f, \"CPU model\": \"%s\", \"Hard disk size\": \"%s\" } }",
				name, year, price, cpuModel, hardDiskSize
				);

		// Send POST request 
		Response response = given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post(BASE_URL)
				.then()
				.statusCode(200) 
				.extract().response();

		// Store response data for validation
		responseBody = response.asString();
		id = response.jsonPath().getString("id");
		createdAt = response.jsonPath().getString("createdAt");
		this.name = response.jsonPath().getString("name");
		this.year = response.jsonPath().getInt("data.year");
		this.price = response.jsonPath().getDouble("data.price");
	}

	@Then("the response should contain a valid ID and creation date")
	public void the_response_should_contain_a_valid_id_and_creation_date() {
		assertNotNull(id, "ID should not be null");
		assertNotNull(createdAt, "CreatedAt should not be null");
	}

	@Then("the added device details should match the provided information")
	public void the_added_device_details_should_match_the_provided_information() {
		assertEquals(name, "Apple Max Pro 1TB", "Device name does not match");
		assertEquals(year, 2023, "Year does not match");
		assertEquals(price, 7999.99, 0.01, "Price does not match");
	}
}