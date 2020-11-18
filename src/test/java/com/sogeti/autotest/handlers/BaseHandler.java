package com.sogeti.autotest.handlers;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;


public class BaseHandler
{

	private static final Logger logger = LoggerFactory.getLogger(BaseHandler.class.getName());

	public static JavascriptExecutor js;
	public static final int numberOfTries = 2;

	public static JavascriptExecutor setJavaScriptExecutor(BROWSER_TYPES driverType)
	{
		js = ((JavascriptExecutor) BrowserDriver.getCurrentDriver(driverType));
		return js;
	}

	public static boolean isElementPresent(WebElement element, BROWSER_TYPES driverType)
	{
		return isElementPresent(element, driverType, numberOfTries);
	}

	public static boolean isElementPresent(WebElement element, BROWSER_TYPES driverType, int customNumberOfTries)
	{
		boolean elementFound = false;
		while (customNumberOfTries > 0 && !elementFound)
		{
			try
			{
				BrowserDriver.waitForElement(element, BrowserDriver.WAIT_IN_SEC, driverType);
				elementFound = element.isDisplayed();
				break;
			}
			catch (Exception e)
			{
				customNumberOfTries--;
				PopupHandler.closeForeseePopup();
				ActionHandler.scrollElementIntoView(element);
			}
		}
		return elementFound;
	}

	public static boolean isElementNotPresent(WebElement element, BROWSER_TYPES driverType)
	{
		boolean elementFound = false;
		try
		{
			BrowserDriver.waitForElement(element, BrowserDriver.WAIT_IN_SEC, driverType);
			elementFound = element.isDisplayed();
		}
		catch (Exception e)
		{
			logger.info("element is not present as expected: {}", element);

		}
		return elementFound;
	}


	public static boolean isAlertPresent()
	{
		Alert alert = ExpectedConditions.alertIsPresent().apply(BrowserDriver.getCurrentDriver());
		return (alert != null);
	}

	public static void acceptAlert()
	{

		BrowserDriver.getCurrentDriver().switchTo().alert().accept();
	}

	public static void rejectAlert()
	{

		BrowserDriver.getCurrentDriver().switchTo().alert().dismiss();
	}

	public static boolean waitForJSandJQueryToLoad(BROWSER_TYPES driverType)
	{
		BrowserDriver.setWait(10);
		WebDriverWait wait = BrowserDriver.getWait(driverType);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				try
				{
					return ((Long) (setJavaScriptExecutor(driverType)).executeScript("return jQuery.active")) == 0;
				}
				catch (Exception e)
				{
					// no jQuery present
					return true;
				}
			}

		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return (setJavaScriptExecutor(driverType)).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public enum LOCATOR_TYPES
	{
		XPATH, CSS, ID, LINKTEXT, PARTIAL_LINK_TEXT, CLASS_NAME, NAME, TAG_NAME
	}

	public static List<WebElement> getDropDownOptions(WebElement element)
	{
		Select dropdown = new Select(element);
		List<WebElement> allOptions = dropdown.getOptions();
		return allOptions;
	}

	}
