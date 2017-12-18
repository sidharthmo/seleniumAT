package com.dxc.vpc.automation.common;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import com.dxc.vpc.automation.constant.DashboardConstant;
import com.dxc.vpc.automation.constant.LoginPageConstant;
import com.dxc.vpc.automation.constant.ServicesPageConstant;
import com.dxc.vpc.automation.util.WebConnector;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginNGP {

	WebConnector connector = WebConnector.getWebConnectorInstance();
	Properties property = connector.getLocalProperties();

	@Given("^I navigate to NGPURL on Browser$")
	public void navigateToURL()throws Throwable {
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
	public void setUserNameAndPassword(String Object,String text)throws Throwable {
		String userName =property.getProperty("vpcUserName");
		String userNameId = LoginPageConstant.USERNAMEID;
		connector.log.info("I enter usename as " +userName+ " with usename Id "+userNameId);
		connector.waitUntilElementIsPresentById(userNameId);
		connector.enterTextById(userNameId, userName);
		String password =property.getProperty("vpcPassword");
		String passwordId = LoginPageConstant.PASSWORDID;
		connector.log.info("I enter password as " +password+ " with password Id "+passwordId);
		connector.waitUntilElementIsPresentById(passwordId);
		connector.enterTextById(passwordId, password);
	}
	
	@And("^I click on loginButton$")
	public void clickOn()throws Throwable {
		String loginId =LoginPageConstant.SUBMITBUTTONID;
		connector.log.info("Clicking on submit button with loginId " + loginId);
		connector.clickcById(loginId);
		
	}

	@When("^User gets loggedin successfully$")
	public void login()throws Throwable {
		String loggedInUserXpath =DashboardConstant.loggedInUserNameXpath;
		connector.waitUntilElementIsPresentByXpath(loggedInUserXpath);
		String expectedUser = connector.getValueByXpath(loggedInUserXpath);
		String actualUser = property.getProperty("vpcUserName");
		connector.log.info("successfully logged with expected user" + expectedUser +" and actual user is "+actualUser);
		assertEquals(actualUser, expectedUser);
		connector.log.info("successfully logged in");
	}

	
	@Then("^User clicks on ServiceIcon$")
	public void clickServiceIcon()throws Throwable {
		String serviceId =DashboardConstant.serviceIdXpath;
		connector.waitUntilElementIsPresentByXpath(serviceId);
		connector.selectByXpath(serviceId);
		connector.log.info("clicked ServiceIcon");
	}
	
	@Then("^Select the customer$")
	public void selectCustomer()throws Throwable {
		connector.log.info("Selecting Customer");
		String dataCenterDropDownXpath =ServicesPageConstant.dataCenterDropDownXpath;
		connector.waitUntilElementIsPresentByXpath(dataCenterDropDownXpath);
		connector.selectByXpath(dataCenterDropDownXpath);
		connector.selectItemFromDropDown(ServicesPageConstant.dataCenterDropDownListXpath,property.getProperty("dataCenterName"));

		
		
	}
	@Then("^Sort the customer list  by \"([^\"]*)\"$")
	public void selectDataCenter(String Sorting)throws Throwable {
		connector.log.info("going to dataCenter");
		String groupingDropDownXpath =ServicesPageConstant.groupingDropDownXpath;
		String groupingDropDownExpendedListXpath=ServicesPageConstant.groupingDropDownExpendedListXpath;
		//String groupingDropDownName= property.getProperty("groupingDropDownName");
		String groupingDropDownName= property.getProperty("groupingDropDownName");
		connector.waitUntilElementIsPresentByXpath(groupingDropDownXpath);
		connector.selectDropDownElementByXpath(groupingDropDownXpath);
		connector.waitUntilElementIsPresentByXpath(groupingDropDownExpendedListXpath);
		//connector.sleep(1000);//waitUntilElementIsPresentByXpath is not working for this
		connector.selectItemFromDropDown(groupingDropDownExpendedListXpath,groupingDropDownName);
	}
	
	@Then("^Select customer \"([^\"]*)\"$")
	public void selectCustomerFromGrid(String CustomerName)throws Throwable {
		String customerName = property.getProperty("customerName");
		String gridDataXpath=ServicesPageConstant.gridDataXpath;
		String serverName = property.getProperty("serverName");
		connector.waitUntilElementIsPresentByXpath(gridDataXpath);
		connector.getRespectiveCustomerFromGrid(gridDataXpath,customerName,serverName);
	}
	
	@Then("^expend the \"([^\"]*)\" Server$")
	public void getServerList(String CustomerDetail) throws Throwable{
		
		
	}

	@Then("^Click the service detail from available list$")
	public void expendCustomerFromGrid()throws Throwable {

		
	}
	
	@Then("^Service detail page should get loaded$")
	public void validateServiceDetailPage()throws Throwable {
		try {
			//connector.closeBrowser();
		} catch (PendingException e) {
			e.printStackTrace();
			throw new PendingException();
		}
		
		
	}
}
