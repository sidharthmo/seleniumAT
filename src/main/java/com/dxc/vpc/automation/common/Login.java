package com.dxc.vpc.automation.common;

import java.util.Properties;

import com.dxc.vpc.automation.constant.DashboardConstant;
import com.dxc.vpc.automation.constant.LoginPageConstant;
import com.dxc.vpc.automation.util.WebConnector;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class Login {
	
	WebConnector connector = WebConnector.getWebConnectorInstance();
	Properties property = connector.getLocalProperties();
	
/*	@Before()
	public void beforeTest()throws Throwable {
		System.out.println("In before");
	}*/
	
	@Given("^I navigate to \"([^\"]*)\" on Browser$")
	public void navigateToURL(String NGPURL)throws Throwable {
/*		String xlspath = property.getProperty("storageXLS");
		String workSheet =property.getProperty("storageWorkSheet");
		int i =selenium.getDatatable(xlspath).getRowCount(workSheet);
		System.out.println(i);*/
		String webBrowser =property.getProperty("webBrowser");
		//String webUrl =property.getProperty("webUrl");
		String webUrl =NGPURL;
		connector.log.info("loggin in browser " +webBrowser+ " to URL "+webUrl);
		connector.openBrowser(webBrowser);
		connector.navigateToURL(webUrl);
		
	}
	
	@And("^I enter \"([^\"]*)\" and \"([^\"]*)\"$")
	public void setUserNameAndPassword(String username,String password1)throws Throwable {
		//String userName =property.getProperty("vpcUserName");
		String userName =username;
		String userNameId = LoginPageConstant.USERNAMEID;
		connector.log.info("I enter usename as " +userName+ " with usename Id "+userNameId);
		connector.waitUntilElementIsPresentById(userNameId);
		connector.enterTextById(userNameId, userName);
		//String password =property.getProperty("vpcPassword");
		String password =password1;
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
		//String expectedUser = connector.getValueByXpath(loggedInUserXpath);
		//String actualUser = property.getProperty("vpcUserName");
		//connector.log.info("successfully logged with expected user" + expectedUser +" and actual user is "+actualUser);
		//assertEquals(actualUser, expectedUser);
		connector.log.info("successfully logged in");
	}


}
