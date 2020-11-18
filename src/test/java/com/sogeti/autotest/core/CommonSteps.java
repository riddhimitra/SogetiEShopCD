package com.sogeti.autotest.core;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.json.JsonException;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.VerifyHandler;
import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.ScreenshotUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;


public class CommonSteps
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonSteps.class.getName());

	public static BROWSER_TYPES BROWSER_TYPE;

	static String jobUrl = null, jiraUrl = null, jiraUserName = null, jiraPwd = null;
	static ArrayList<String> jiraIssue;
	static ArrayList<String> resultList;
	static HashMap<String, ArrayList<String>> issueStatus = new HashMap<>();
	boolean isJenkins = false;
	private static boolean initialized = false;

	@Then("^I refresh the page$")
	public void refreshPage() throws Throwable
	{
		BrowserDriver.refreshPage();
	}

	@Then("^the page title should contain (.*)$")
	public void confirmPageTitle(String title)
	{
		VerifyHandler.verifyTitleContainsText(title);
	}

	@When("^I wait for (\\d+) seconds$")
	public void I_wait_for_seconds(int waitTime)
	{
		BrowserDriver.wait(waitTime * 1000);
	}

	@After(order = 50000, value = "not @noui")
	public static void embedScreenshot(Scenario scenario)
	{

		if (scenario.isFailed() && (BrowserDriver.getBROWSER_TYPE() != null))
		{
			ScreenshotUtils.embedScreenshot(scenario);
		}
	}
	
	@After(order = 50000)
	public static void dumpConsole(Scenario scenario)
	{
		if (scenario.isFailed() && (BrowserDriver.getBROWSER_TYPE() != null))
		{
			try
			{
				ActionHandler.outputLogs("ALL");
			}
			catch (JsonException j)
			{

			}
		}
	}


	@After(order = 49000, value = "not (@noui or @keepbrowser)")
	public void closeAllBrowsers()
	{
		try
		{			
			BrowserDriver.quit();
		}
		catch (Exception e)
		{
			LOGGER.info("issue quitting the browsers:" + e);
		}
	}

	@Before(order = 50001, value = "@keepbrowser")
	public void keepBrowserOpen()
	{
		BrowserDriver.setCloseOnComplete(false);
	}


	@Before(order = 50000, value = "@networklogs")
	public void logNetworkTraffic()
	{
		CacheService.getInstance().setDataMap("networklogs", true);
	}

	@After(order = 50000, value = "@networklogs")
	public void logallNetworkTraffic()
	{

	}

	//	In Eclipse :
	//		Under Window > Preferences , go to the Run/Debug > Console section, then you should see 
	//	   an option "Limit console output." 
	// 	You can uncheck this or change the number in the "Console buffer size (characters)" text box below.
	//
	//		Add a couple of 0's to the end or uncheck.
	//
	public HashMap<String, Boolean> getLogsFilteredBy(String filter)
	{
		List<LogEntry> entries = BrowserDriver.getCurrentDriver().manage().logs().get(LogType.PERFORMANCE).toJson();

		System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");

		HashMap<String, Boolean> filteredLogsMap = new HashMap<>();
		for (LogEntry entry : entries)
		{
			//LOGGER.info(entry.getMessage().toString());
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(entry.getMessage());
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String messageString = jsonObject.get("message").toString();
			JsonElement message = jsonObject.get("message");
			JsonObject messageObject = message.getAsJsonObject();
			JsonElement method = messageObject.get("method");

			if (method.toString().contains("Network.requestWillBeSent"))
			{
				JsonElement params = messageObject.get("params");
				JsonElement request = params.getAsJsonObject().get("request");
				JsonElement url = request.getAsJsonObject().get("url");

				if (url.toString().contains(filter))
				{
					filteredLogsMap.put(url.toString(), false);
					LOGGER.info("Found log entry request: " + url.toString());
				}
			}

			if (method.toString().contains("Network.responseReceived"))
			{
				JsonElement params = messageObject.get("params");
				JsonElement request = params.getAsJsonObject().get("response");
				JsonElement url = request.getAsJsonObject().get("url");

				if (url.toString().contains(filter))
				{
					filteredLogsMap.put(url.toString(), false);
					LOGGER.info("Found log entry response: " + url.toString());
				}
			}
		}

		System.out.println(filteredLogsMap.size() + " filtered log entries found");
		return filteredLogsMap;
	}

	@Then("^the following coremetrics tags should be fired$")
	
	public void clearCacheService()
	{
		CacheService.clearCacheInstance();
	}

	
	@After(order = 4000)
	public void toRunAfterScenario(Scenario scenario) throws Exception
	{
		clearCacheService();
	}

}
