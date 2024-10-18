Feature: Add new device

 Scenario: Successfully a new device
 	When I add a new device with name "Apple Max Pro 1TB", year 2023, price 7999.99, CPU model "Apple ARM A7", and hard disk size "1 TB" 
 	Then the response should contain a valid ID and creation date
 	And the added device details should match the provided information
 	
 	
