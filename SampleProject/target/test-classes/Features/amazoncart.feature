Feature: Amazon Cart Functionality
	Scenario: User adds toys to the cart and validates prices
		Given I am on the Amazon homepage
		When I search for "toys"
		And I add two products to the cart
		Then I should see the prices in the cart
 