package com.dxc.vpc.automation.common;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import com.dxc.vpc.automation.util.WebConnector;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginNGPTest {

	WebConnector connector = WebConnector.getWebConnectorInstance();
	Properties property = connector.getLocalProperties();

	@Given("^I navigate to \"([^\"]*)\" on \"([^\"]*)\"$")
	public void navigateToURL(String NGPURL,String Browser) {
		
/*		String xlspath = property.getProperty("storageXLS");
		String workSheet =property.getProperty("storageWorkSheet");
		int i =selenium.getDatatable(xlspath).getRowCount(workSheet);
		System.out.println(i);*/
		String webBrowser =property.getProperty("webBrowser");
		String webUrl =property.getProperty("webUrl");
		connector.log.info("loggin in browser " +webBrowser+ " to URL "+webUrl);
		connector.openBrowser(webBrowser);
		connector.navigateToURL(webUrl);
	}
	
	@And("^I enter \"([^\"]*)\" and \"([^\"]*)\"$")
	public void setUserNameAndPassword(String Object,String text) {
		String userName =property.getProperty("vpcUserName");
		String userNameId = "username";
		connector.log.info("I enter usename as " +userName+ " with usename Id "+userNameId);
		connector.waitUntilElementIsPresentById(userNameId);
		connector.enterTextById(userNameId, userName);
		String password =property.getProperty("vpcPassword");
		String passwordId = "password";
		connector.log.info("I enter password as " +password+ " with password Id "+passwordId);
		connector.waitUntilElementIsPresentById(passwordId);
		connector.enterTextById(passwordId, password);
	}
	
	@And("^I click on loginButton$")
	public void clickOn() {
		String loginId ="submit";
		connector.log.info("Clicking on submit button with loginId " + loginId);
		connector.clickcById(loginId);
		
	}

	@When("^User gets loggedin successfully$")
	public void login() {
		String loggedInUserXpath ="//propel-header/nav/div/div[2]/div";
		connector.waitUntilElementIsPresentByXpath(loggedInUserXpath);
		String expectedUser = connector.getValueByXpath(loggedInUserXpath);
		String actualUser = property.getProperty("vpcUserName");
		connector.log.info("successfully logged with expected user" + expectedUser +" and actual user is "+actualUser);
		assertEquals(actualUser, expectedUser);
		connector.log.info("successfully logged in");
	}

	
	@Then("^User clicks on ServiceIcon$")
	public void clickServiceIcon() {
		String serviceId ="//*[@id='dashboard-tile_services']/div/div/div/div/div/div";
		connector.waitUntilElementIsPresentByXpath(serviceId);
		connector.selectByXpath(serviceId);
		connector.log.info("clicked ServiceIcon");
	}
	
	@Then("^Select the customer$")
	public void selectCustomer() {
		connector.log.info("Selecting Customer");
		String dataCenterXpath ="//*[@id='default_dc_link']";
		connector.waitUntilElementIsPresentByXpath(dataCenterXpath);
		connector.selectByXpath(dataCenterXpath);
		//connector.selectCustomerByName("Markham - PB1DEVCAN");
		connector.selectItemFromDropDown("//subheader/div/div/div/dc-selector/div/dl/ul/li","Markham - PB1DEVCAN");

		
		
	}
	@Then("^Sort the customer list  by \"([^\"]*)\"$")
	public void selectDataCenter(String Sorting) {
		connector.log.info("going to dataCenter");
		String groupingDropDownXpath ="//div[1]/div/sort-selector[2]/a";
		String selectDropDownxpath="//div/sort-selector[2]/ul/li";
		//NO GROUPING--- GROUP BY CATALOG ITEM AND OWNER-----GROUP BY OWNER AND CATALOG ITEM
		String dropDownName= "Group By Owner And Catalog Item";
		connector.waitUntilElementIsPresentByXpath(groupingDropDownXpath);
		connector.selectDropDownElementByXpath(groupingDropDownXpath);
		connector.sleep(1000);
		connector.selectItemFromDropDown(selectDropDownxpath,dropDownName);

/*		String groupingDropDownXpath ="//div[1]/div/sort-selector[2]/a";
		String groupingDropDown3rdElementXpath ="//div/sort-selector[2]/ul/li[3]/a";
		String gridDataXpath="//statistics-list/div/div[1]/div";
		String customerName = "lukas.vrab@hpe.com";
		String serverName = "Backups";
		connector.waitUntilElementIsPresentByXpath(groupingDropDownXpath);
		connector.selectDropDownElementByXpath(groupingDropDownXpath);
		connector.sleep(1000);
		connector.waitUntilElementIsPresentByXpath(groupingDropDown3rdElementXpath);
		connector.selectDropDownElementByXpath(groupingDropDown3rdElementXpath);
		connector.waitUntilElementIsPresentByXpath(gridDataXpath);
		connector.getRespectiveCustomerFromGrid(gridDataXpath,customerName,serverName);*/
		
	}
	
	@Then("^Select customer \"([^\"]*)\"$")
	public void selectCustomerFromGrid(String CustomerName) {
		String customerName = "lukas.vrab@hpe.com";
		String gridDataXpath="//statistics-list/div/div[1]/div";
		String serverName = "Backups";
		connector.waitUntilElementIsPresentByXpath(gridDataXpath);
		connector.getRespectiveCustomerFromGrid(gridDataXpath,customerName,serverName);
	}
	
	@Then("^expend the \"([^\"]*)\" Server$")
	public void getServerList(String CustomerDetail) {
		
		
	}

	@Then("^Click the service detail from available list$")
	public void expendCustomerFromGrid() {

		
	}
	
	@Then("^Service detail page should get loaded$")
	public void validateServiceDetailPage() {
		//connector.closeBrowser();
	}
}
