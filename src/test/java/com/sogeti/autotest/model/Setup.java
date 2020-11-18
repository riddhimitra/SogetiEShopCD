package com.sogeti.autotest.model;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.browserstack.BrowserStackResults;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Setup
{
	private static final String BROWSERSTACK_RESULTS_CLOSE_SECTION = "</section>";

	private static final Logger LOGGER = LoggerFactory.getLogger(Setup.class.getName());

	@After
	public void addBrowserStackResults(Scenario scenario) throws IOException
	{
		BrowserStackResults results = new BrowserStackResults();
		Optional<String> resultUrl = results.getLatestSessionResults();

		if (resultUrl.isPresent())
		{
			String url = resultUrl.get();
			LOGGER.info("Session URL: {}", url);

			String featureName = scenario.getId().split(";")[0];
			featureName = featureName.replaceAll("/", " ");
			Path outputFile = Paths.get("target", "browserstack", featureName + ".xml");
			List<String> content;
			if (outputFile.toFile().exists())
			{
				content = Files.readAllLines(outputFile);
				content.remove(BROWSERSTACK_RESULTS_CLOSE_SECTION);
			}
			else
			{
				Files.createDirectories(outputFile.getParent());
				content = new ArrayList<>();
				content.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				content.add("<section name=\"BrowserStack Results\">");
			}
			content.add(String.format(
					"  <field name=\"Test\" value=\"%s\"> <![CDATA[<a target=\"_blank\" href=\"%s\">View Results</a>]]> </field>",
					scenario.getName(), url));
			content.add(BROWSERSTACK_RESULTS_CLOSE_SECTION);
			Files.write(outputFile, content);
		}
	}

	@Before(order = 3001)
	public static void setFromEnvVariables(Scenario scenario)
	{
		String env = System.getProperty("ENV");
		String locale = System.getProperty("LOCALE");
		if (env != null)
		{
			switch (env)
			{
				case "@test":
					setCommonTestProperties(scenario);
					break;
				case "@local":
					setCommonLocalProperties(scenario);
					break;
				default:
					fail("unknown Environment:" + env);
					break;
			}
		}

	}

	@Before(order = 3001, value = "@local")
	public static void setCommonLocalProperties(Scenario scenario)
	{
		Config.props.setProperty("product", "sogetitest");
		Config.props.setProperty("environment", "LOCAL");
		Config.props.setProperty("commonPropertiesFile", "LOCAL.properties");
	}

	@Before(order = 3001, value = "@test")
	public static void setCommonTestProperties(Scenario scenario)
	{
		Config.props.setProperty("product", "eu-sogetitest");
		Config.props.setProperty("environment", "TEST");
		Config.props.setProperty("commonPropertiesFile", "TEST.properties");
	}


	@Before(order = 5002, value = "@uk")
	public static void setLocalisedUKProperties()
	{
		setEnvironment("UK");
	}

	@Before(order = 5003, value = "@us")
	public static void setLocalisedUSProperties()
	{
		setEnvironment("US");
	}

	public static void setEnvironment(String region)
	{
		setEnvironment(region, false);

	}

	public static void setEnvironment(String region, boolean temp)
	{
		Config.props.setProperty("region", region);
		String environment = Config.props.getProperty("environment");
		Config.props.setProperty("regionalPropertiesFile",
				"GLOBAL/" + region + ".properties");
		Config.props.setProperty("localisedPropertiesFile",
				environment + "/" + region + "-" + environment + ".properties");

	}

	public static void setLocale(String locale)
	{
		switch (locale)
		{
			case "UK":
				setLocalisedUKProperties();
				break;
			case "US":
				setLocalisedUSProperties();
				break;
			default:
				fail("unknown Region:" + locale);

		}
	}

}
