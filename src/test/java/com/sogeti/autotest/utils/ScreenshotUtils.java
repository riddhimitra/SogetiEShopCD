package com.sogeti.autotest.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.core.api.Scenario;


public class ScreenshotUtils
{

	private static final Logger LOGGER = LoggerFactory.getLogger(BrowserDriver.class.getName());


	private ScreenshotUtils()
	{
		throw new IllegalStateException("Utility class");
	}

	public static void zoomOut()
	{
		JavascriptExecutor js = ((JavascriptExecutor) BrowserDriver.getCurrentDriver(BrowserDriver.getBROWSER_TYPE()));
		js.executeScript("document.body.style.zoom = '50%';");
	}

	public static void embedScreenshot(Scenario scenario)
	{	
		
		zoomOut();
			{
				try
				{
					LOGGER.info("taking a screenshot");
					byte[] screenshot = ((TakesScreenshot) BrowserDriver.getCurrentDriver())
							.getScreenshotAs(OutputType.BYTES);
					scenario.embed(screenshot, "image/png");
					LOGGER.info("embedding screenshot as scenario is failed");
				}
				catch (WebDriverException wde)
				{
					LOGGER.error(wde.getMessage());
				}
				catch (ClassCastException cce)
				{
					LOGGER.error(cce.getMessage());
				}
				catch (Exception e)
				{
					LOGGER.error("something unexpected happenned");
					LOGGER.error(e.getMessage());
				}
			}
		
	}

}
