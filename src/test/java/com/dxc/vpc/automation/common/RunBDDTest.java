package com.dxc.vpc.automation.common;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

// runner class

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"html:target/cucumberHtmlReport"},
		//pretty:target/cucumber-json-report.json
		//features = "login.feature",
		glue = {"com.dxc.vpc.automation.common"}
)

public class RunBDDTest  {

}