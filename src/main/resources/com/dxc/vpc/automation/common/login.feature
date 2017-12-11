Feature: verifying service detail for given user

In order to migrate VPC to NPG portal 
As a QA engineer
I want to login and verify the service detail of migrated user

@login
Scenario: Service detail for respective customer 
Given I navigate to "NGPURL" on "Browser"
And I enter "loginUserName" and "loginPassword"
And I click on loginButton
When User gets loggedin successfully
Then User clicks on ServiceIcon
Then Select the customer 
Then Sort the customer list  by "GROUP BY OWNER AND CATALOG ITEM"
Then Select customer "lukas.vrab@hpe.com"
And expend the "Backups" Server
And Click the service detail from available list 
Then Service detail page should get loaded
