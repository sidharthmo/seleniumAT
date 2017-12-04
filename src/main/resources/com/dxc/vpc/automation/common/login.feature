Feature: Logging into NGP

In order to migrate VPC to NPG portel 
As a migration users
I want to login and verify the dashboard

@login
Scenario: Successful Login with Valid Credentials
Given I navigate to "NGPURL" on "Browser"
And I enter "loginUserName" and "loginPassword"
And I click on loginButton
When User gets loggedin successfully
Then User clicks on ServiceIcon
Then Select the customer
Then Go to Datacenter and sort by groupByUserCI