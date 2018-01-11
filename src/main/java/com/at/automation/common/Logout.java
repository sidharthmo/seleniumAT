package com.at.automation.common;

import java.io.IOException;
import java.util.Properties;

import com.at.automation.util.WebConnector;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;

public class Logout {
	
	WebConnector connector = WebConnector.getWebConnectorInstance();
	Properties property = connector.getLocalProperties();
	
	@Then("^User logout from the application $")
	public void logout()throws Throwable {
		try {
			connector.closeBrowser();
		} catch (PendingException e) {
			e.printStackTrace();
			throw new PendingException();
		}
}
	@After
	public final void takeScreenShot(Scenario scenario) throws IOException {
		System.out.println(scenario.getName());
		System.out.println(scenario.getStatus());
		if (scenario.isFailed()) {
			connector.takeScreenShot();
			connector.closeBrowser();
		}
	}
}
