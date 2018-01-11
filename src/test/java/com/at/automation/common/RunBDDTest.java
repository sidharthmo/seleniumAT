package com.at.automation.common;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

// runner class

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"html:target/cucumberHtmlReport"},
		glue = {"com.at.automation.common"}
)

public class RunBDDTest  {

}