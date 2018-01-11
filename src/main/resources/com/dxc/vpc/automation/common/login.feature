Feature: verifying service detail for given user

In order to migrate VPC to NPG portal 
As a QA engineer
I want to login and verify the service detail of migrated user

@login
Scenario Outline: Validate Service detail for respective customer 
	Given I navigate to "<NGPURL>" on Browser
	And I enter "<username>" and "<password>"
	And I click on loginButton
	When User gets loggedin successfully
	Then User clicks on ServiceIcon
	Then Select the "<DataCenter>" 
	Then Sort the customer list  by GroupByOwnerAndCatalogItem
	Then Select customer "<Customer>"
	And  expend the "<Server>"
	And Click the service detail from available list 
	Then Validate service detail page detail with "<xls>"

Examples:
 |NGPURL   															| username   	| password 		| DataCenter	| Customer	| Server								| xls										|
 |																	|  				|  				| 				| 			| 										| 											|