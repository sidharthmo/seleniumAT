package com.dxc.vpc.automation.common;

import java.util.Properties;

import com.dxc.vpc.automation.constant.DashboardConstant;
import com.dxc.vpc.automation.constant.ServicesPageConstant;
import com.dxc.vpc.automation.util.WebConnector;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

public class ServicePage {

	WebConnector connector = WebConnector.getWebConnectorInstance();
	Properties property = connector.getLocalProperties();


	
	@Then("^User clicks on ServiceIcon$")
	public void clickServiceIcon()throws Throwable {
		String serviceId =DashboardConstant.serviceIdXpath;
		connector.waitUntilElementIsPresentByXpath(serviceId);
		connector.selectByXpath(serviceId);
		connector.log.info("clicked ServiceIcon");
	}
	
	@Then("^Select the \"([^\"]*)\"$")
	public void selectCustomer(String DataCenter)throws Throwable {
		connector.log.info("Selecting Customer");
		String dataCenterDropDownXpath =ServicesPageConstant.dataCenterDropDownXpath;
		connector.waitUntilElementIsPresentByXpath(dataCenterDropDownXpath);
		connector.selectByXpath(dataCenterDropDownXpath);
		//connector.selectItemFromDropDown(ServicesPageConstant.dataCenterDropDownListXpath,property.getProperty("dataCenterName"));
		connector.selectItemFromDropDown(ServicesPageConstant.dataCenterDropDownListXpath,DataCenter);

		
		
	}
	@Then("^Sort the customer list  by GroupByOwnerAndCatalogItem$")
	public void selectDataCenter()throws Throwable {
		connector.log.info("going to dataCenter");
		String groupingDropDownXpath =ServicesPageConstant.groupingDropDownXpath;
		String groupingDropDownExpendedListXpath=ServicesPageConstant.groupingDropDownExpendedListXpath;
		String groupingDropDownName= property.getProperty("groupingDropDownName");
		connector.waitUntilElementIsPresentByXpath(groupingDropDownXpath);
		connector.selectDropDownElementByXpath(groupingDropDownXpath);
		connector.waitUntilElementIsPresentByXpath(groupingDropDownExpendedListXpath);
		//connector.sleep(1000);//waitUntilElementIsPresentByXpath is not working for this
		connector.selectItemFromDropDown(groupingDropDownExpendedListXpath,groupingDropDownName);
	}
	
	@Then("^Select customer \"([^\"]*)\"$")
	public void selectCustomerFromGrid(String customerName)throws Throwable {
		//String customerName = property.getProperty("customerName");
		String gridDataXpath=ServicesPageConstant.gridDataXpath;
		String serverName = property.getProperty("serverName");
		connector.waitUntilElementIsPresentByXpath(gridDataXpath);
		connector.getRespectiveCustomerFromGrid(gridDataXpath,customerName,serverName);
	}
	
	@Then("^expend the \"([^\"]*)\"$")
	public void getServerList(String serverName) throws Throwable{
		
		
	}

	@Then("^Click the service detail from available list$")
	public void expendCustomerFromGrid()throws Throwable {

		
	}
	
	@Then("^Validate service detail page detail with \"([^\"]*)\"$")
	public void validateServiceDetailPage(String XLS)throws Throwable {
		try {
			//connector.closeBrowser();
		} catch (PendingException e) {
			e.printStackTrace();
			throw new PendingException();
		}
		
		
	}
}
