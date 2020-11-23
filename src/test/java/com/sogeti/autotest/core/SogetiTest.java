package com.sogeti.autotest.core;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",//path to the features	
		plugin = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json" },
		glue = { "com.sogeti.autotest.steps", "com.sogeti.autotest.model",  "com.sogeti.autotest.core" }
		)
public class SogetiTest {
	public void startTest(){
		System.out.println("Starting Tests....");
	}
		
}
