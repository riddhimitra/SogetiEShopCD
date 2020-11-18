package com.sogeti.autotest.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;


public class ActionHandler extends BaseHandler
{

	private static final Logger logger = LoggerFactory.getLogger(ActionHandler.class.getName());
	public static Map<String, String> map = new HashMap<>();


	public static WebElement locateElement(String locator, LOCATOR_TYPES locateby)
	{
		return locateElement(locator, locateby, BrowserDriver.BROWSER_TYPE);
	}

	public static WebElement locateElement(String locator, LOCATOR_TYPES locateby, BROWSER_TYPES driverType)
	{
		WebElement webElement = null;
		switch (locateby)
		{
			case CSS:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
				break;
			case ID:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
				break;
			case LINKTEXT:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locator)));
				break;

			case PARTIAL_LINK_TEXT:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locator)));
				break;

			case CLASS_NAME:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.className(locator)));
				break;

			case NAME:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.name(locator)));
				break;

			case TAG_NAME:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locator)));
				break;

			case XPATH:
				webElement = BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
				break;
		}

		return webElement;
	}

	public static void scrollElementToTopOfView()
	{
		js.executeScript("javascript:window.scrollTo(document.body.scrollHeight,0)");
	}

	public static void scrollToBottomOfPage()
	{
		js.executeScript("javascript:window.scrollTo(0,document.body.scrollHeight)");
	}

	public static String runJsScript(String script)
	{
		return (String) js.executeScript(script);
	}

	public static void removeReadOnly(WebElement element)
	{
		js.executeScript("arguments[0].removeAttribute('readonly','readonly');", element);
	}

	/**
	 * Need to get value via this method as:
	 * 1) Selenium's getLocation() always returns the same position even if scrolled
	 * 2) isDisplayed() does not take the viewport in to account
	 * 3) jquery offset and position don't change with scrolling
	 * @param element - The WebElement to get y Position of
	 * @return return a String value of the y position
	 */
	public static String getVerticalPositiononPage(WebElement element)
	{
		Map getRectangle = (Map) js.executeScript("return arguments[0].getBoundingClientRect();", element);
		return getRectangle.get("y").toString().split("\\.")[0];
	}

	public static Long getVerticalScreenHeight()
	{
		return (Long) js.executeScript("return document.documentElement.clientHeight;");

	}

	public static void click(String locator, LOCATOR_TYPES locateby)
	{
		click(locator, locateby, BrowserDriver.BROWSER_TYPE);
	}


	public static void click(String locator, LOCATOR_TYPES locateby, BROWSER_TYPES driverType)
	{
		logger.info("Trying to locate the weblement by: " + locateby + "with locator:" + locator);
		WebElement webElement = locateElement(locator, locateby, driverType);
		if (webElement != null)
		{
			click(webElement, driverType);
		}
		else
		{
			Assert.fail("Unable to locate the weblement by: " + locateby + "with locator:" + locator);
		}
	}

	public static void clickAll(String locator, LOCATOR_TYPES locateby)
	{
		logger.info("Trying to locate the all the weblements for :" + locator);
		List<WebElement> webElements = getElementsList(locator, locateby);
		logger.info("Success");
		logger.info("Total Webelements located :::::" + webElements.size());
		webElements.forEach(webElement ->
		{
			if (webElement.isDisplayed())
				webElement.click();
		});

	}

	public static void browserBackButton()
	{
		js.executeScript("window.history.go(-1)");

	}

	// A DoubleClick Event with a custom wait time set
	public static void doubleClick(WebElement element)
	{
		BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.elementToBeClickable(element));
		if (element.isDisplayed() && element.isEnabled())
		{
			logger.info("Trying to double click the weblement :" + element);
			Actions actions = new Actions(BrowserDriver.getCurrentDriver());
			actions.doubleClick(element).build().perform();
		}

	}

	public static void clickWaitForAjax(WebElement element)
	{
		waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
		click(element);
	}

	public static void click(WebElement element)
	{
		click(element, BrowserDriver.BROWSER_TYPE);
	}


	public static void click(WebElement element, BROWSER_TYPES driverType)
	{
		click(element, BrowserDriver.WAIT_IN_SEC, driverType);
	}

	public static void click(WebElement element, int waitTimeInSeconds)
	{
		click(element, waitTimeInSeconds, BrowserDriver.BROWSER_TYPE);
	}


	public static void click(WebElement element, int waitTimeInSeconds, BROWSER_TYPES driverType)
	{
		boolean elementFound = isElementPresent(element, driverType);
		if (elementFound)
		{
			BrowserDriver.setWait(waitTimeInSeconds);
			BrowserDriver.getWait(driverType).until(ExpectedConditions.elementToBeClickable(element));
			try
			{
				element.click();
			}
			catch (WebDriverException e)
			{
				BrowserDriver.wait(1000);
				logger.info("Was Foresee Dialog present? " + PopupHandler.closeForeseePopup());
				scrollElementIntoView(element);
				element.click();
			}
		}
		else
		{
			Assert.fail("Unable to locate the weblement: " + element);
		}
	}

	public static void scrollElementIntoView(String locator, LOCATOR_TYPES locateBy)
	{
		WebElement element = locateElement(locator, locateBy, BrowserDriver.BROWSER_TYPE);
		scrollElementIntoView(element);
	}

	public static void scrollElementIntoView(WebElement element)
	{
		try
		{
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
		}
		catch (Exception e)
		{
			logger.info("Error while scrolling JS");
		}
	}


	/**
	 * SetText for an Element with default timeout without clicking in input
	 * first and splitting each chracter ensuring you append each time
	 * @param element
	 *           - the WebElement input field
	 * @param text
	 *           - the text to input
	 */
	public static void setTextSafely(WebElement element, String text)
	{
		setText(element, text, false, false, true);
	}

	/**
	 * SetText for an Element with default timeout without clicking in input
	 * first
	 * @param element
	 *           - the WebElement input field
	 * @param text
	 *           - the text to input
	 */
	public static void setText(WebElement element, String text)
	{
		setText(element, text, false);
	}

	/**
	 * SetText for an Element with default timeout
	 * @param element
	 *           - the WebElement input field
	 * @param text
	 *           - the text to input
	 * @param giveFocus
	 *           - should the input be clicked in before entering text, useful
	 *           to disable parsley checking
	 * @param waitForJs
	 *           - should we wait for JS& JQuery to complete before moving on
	 */
	public static void setText(WebElement element, String text, Boolean giveFocus)
	{
		setText(element, text, BrowserDriver.WAIT_IN_SEC, giveFocus, true);
	}

	public static void setTextNoWait(WebElement element, String text, Boolean giveFocus)
	{
		setText(element, text, BrowserDriver.WAIT_IN_SEC, giveFocus, false);
	}

	public static void setText(WebElement element, String text, Boolean giveFocus, Boolean waitForJs)
	{
		setText(element, text, BrowserDriver.WAIT_IN_SEC, giveFocus, waitForJs);
	}

	public static void setText(WebElement element, String text, Boolean giveFocus, Boolean waitForJs, Boolean splitString)
	{
		setText(element, text, BrowserDriver.WAIT_IN_SEC, BrowserDriver.BROWSER_TYPE, giveFocus, waitForJs, splitString);
	}

	public static void setText(WebElement element, String text, int waitTimeInSeconds, Boolean giveFocus)
	{
		setText(element, text, waitTimeInSeconds, BrowserDriver.BROWSER_TYPE, giveFocus, true, false);
	}

	public static void setText(WebElement element, String text, int waitTimeInSeconds, Boolean giveFocus, Boolean waitForJs)
	{
		setText(element, text, waitTimeInSeconds, BrowserDriver.BROWSER_TYPE, giveFocus, waitForJs, false);
	}

	public static void setText(WebElement element, String text, int waitTimeInSeconds, BROWSER_TYPES driverType, Boolean giveFocus)
	{
		setText(element, text, waitTimeInSeconds, driverType, giveFocus, true, false);
	}

	// SetText with a custom wait Time
	public static void setText(WebElement element, String text, int waitTimeInSeconds, BROWSER_TYPES driverType,
			Boolean giveFocus, Boolean waitForJs, Boolean splitString)
	{
		BrowserDriver.setWait(waitTimeInSeconds);
		if (isElementPresent(element, driverType))
		{
			BrowserDriver.getWait(driverType).until(ExpectedConditions.visibilityOf(element));
			if (giveFocus)
			{
				click(element);
				BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, "readonly", "readonly")));

			}
			try
			{
				BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, "readonly", "readonly")));
				element.clear();
				logger.info("Trying to set text [{}] for the weblement :{}", text, element);
			}
			catch (InvalidElementStateException ee)
			{
				logger.info("Exception thrown Trying to set text again [{}] for the weblement :{}", text, element);
				BrowserDriver.getWait(driverType)
						.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(element, "readonly", "readonly")));
				element.clear();

			}

			if (splitString)
			{
				String[] chars = text.split("(?!^)");
				for (String character : chars)
				{
					BrowserDriver.wait(300);
					element.click();
					element.sendKeys(Keys.END + character);
					waitForJSandJQueryToLoad(driverType);
				}
			}
			else
			{
				element.sendKeys(text);
			}
			if (waitForJs)
			{
				waitForJSandJQueryToLoad(driverType);
			}
			logger.info("Success");
			BrowserDriver.resetWait();
		}
		else
		{
			Assert.fail("Cannot find element:" + element);
		}

	}

	/**
	 * setText of an input dynamically finding the element by Locator rather using a WebElement
	 * defaults to the standard driver rather than the adminDriver
	 * @param locator - locator string
	 * @param locateby - locate method, one of LOCATOR_TYPES
	 * @param text - The text to input
	 * @throws IOException
	 */
	public static void setText(String locator, LOCATOR_TYPES locateby, String text) throws IOException
	{
		setText(locator, locateby, text, null);
	}

	/**
	 * @param locator - locator string
	 * @param locateby- locate method, one of LOCATOR_TYPES
	 * @param text- The text to input
	 * @param driverType - "admin" uses the AdminBrowser Factory, all other values use the default Browser
	 * @throws IOException
	 */
	public static void setText(String locator, LOCATOR_TYPES locateby, String text, BROWSER_TYPES driverType)
	{
		logger.info("Trying to locate the weblement by: {} with locator: {}", locateby, locator);
		WebElement webElement = locateElement(locator, locateby, driverType);
		if (webElement != null)
		{
			webElement.clear();
			webElement.sendKeys(text);

		}
	}

	// GetText for an Element with default timeout
	public static String getText(WebElement element)
	{
		return getText(element, BrowserDriver.WAIT_IN_SEC);
	}

	// Keypress actions
	public static void keyActions()
	{
		try
		{
			logger.info("In keyActions Method");
			Actions act = new Actions(BrowserDriver.getCurrentDriver());
			act.sendKeys(Keys.ENTER).build().perform();
		}
		catch (Exception e)
		{
			logger.info("Global Timeout not specified" + e);
			e.printStackTrace();
		}
	}

	// switch to frame

	public static void switchToframe(WebElement element)
	{
		try
		{
			logger.info("In switchtoframe method");

			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		}
		catch (WebDriverException e)
		{
			logger.error("WebDriverException :" + e);
			try
			{
				logger.info("In retry of switchtoframe method");

				BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
			}
			catch (WebDriverException ee)
			{
				logger.error("WebDriverException :" + ee);
				Assert.fail("Cannot switch to Frame after 2 attempts");
			}
		}
	}

	// Switch back from frame
	public static void switchbackFromframe()
	{
		try
		{

			BrowserDriver.getCurrentDriver().switchTo().defaultContent();
		}
		catch (WebDriverException e)
		{
			logger.error("WebDriverException :" + e);

			if (e.getMessage().contains("Other element would receive the click"))
			{

				logger.info("Trying to click the forsee pop up if any");
				String xpath = "//a[@class='fsrCloseBtn']";
				click(xpath, LOCATOR_TYPES.XPATH);
				logger.info("Re trying to switch the frame");
			}
		}

	}

	// GetText with a custom wait Time
	public static String getText(WebElement element, int waitTimeInSeconds)
	{
		String text = null;
		// Wait for the element to be visible
		BrowserDriver.setWait(waitTimeInSeconds);
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		try
		{
			if (elementFound)
			{
				logger.info("Trying to get text for the weblement : {}", element);
				text = getElementText(element);
				logger.info("Success : text captured >>{}", text);
			}
			else
			{
				scrollToBottomOfPage();
				text = getElementText(element);
			}
		}
		catch (WebDriverException e)
		{
			Assert.fail("Unable to locate the weblement: " + element);
		}
		return text;
	}

	public static String getElementText(WebElement element)
	{
		String text = element.getText();
		if (text.equalsIgnoreCase(""))
		{
			text = element.getAttribute("value");
		}
		return text;
	}

	public static ArrayList<String> getAllTextByAttribute(List<WebElement> elements, String attribute)
	{
		ArrayList<String> textList = new ArrayList<>();
		for (WebElement element : elements)
		{
			if (element.isDisplayed())
			{
				textList.add(element.getAttribute(attribute));
			}
		}
		return textList;
	}

	public static void appendText(WebElement element, String text)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		if (elementFound)
		{
			BrowserDriver.setWait(BrowserDriver.WAIT_IN_SEC);
			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.elementToBeClickable(element));
			try
			{
				element.sendKeys(text);
			}
			catch (WebDriverException e)
			{
				logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
				element.sendKeys(text);
			}
		}
	}

	public static void selectByValue(WebElement element, String option)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		Select dropdown = null;
		if (elementFound)
		{
			dropdown = new Select(element);
		}
		try
		{
			dropdown.selectByValue(option);
		}
		catch (WebDriverException ee)
		{
			logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
			dropdown.selectByValue(option);
		}
		catch (NullPointerException ee)
		{
			logger.error("Element not available to select ", ee.getMessage());
			Assert.fail();
		}

	}

	public static String getDropDownValue(WebElement element)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		Select dropdown = null;
		String value = null;
		if (elementFound)
		{
			dropdown = new Select(element);
		}
		try
		{
			value = dropdown.getFirstSelectedOption().getAttribute("value");
		}
		catch (WebDriverException ee)
		{
			logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
			value = dropdown.getFirstSelectedOption().getAttribute("value");
		}
		catch (NullPointerException ee)
		{
			logger.error("Element not available", ee.getMessage());
			Assert.fail();
		}
		return value;

	}

	public static String getDropDownText(WebElement element)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		Select dropdown = null;
		String value = null;
		if (elementFound)
		{
			dropdown = new Select(element);
		}
		try
		{
			value = dropdown.getFirstSelectedOption().getText();
		}
		catch (WebDriverException ee)
		{
			logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
			value = dropdown.getFirstSelectedOption().getText();
		}
		catch (NullPointerException ee)
		{
			logger.error("Element not available", ee.getMessage());
			Assert.fail();
		}
		return value;

	}

	public static void selectByVisibleText(WebElement element, String text)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		Select dropdown = null;
		if (elementFound)
		{
			dropdown = new Select(element);
		}
		try
		{
			dropdown.selectByVisibleText(text);
		}
		catch (WebDriverException ee)
		{
			logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
			dropdown.selectByVisibleText(text);
		}
		catch (NullPointerException ee)
		{
			logger.error("Element not available to select ", ee.getMessage());
			Assert.fail();
		}

	}

	public static void selectByIndex(WebElement element, int index)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		Select dropdown = null;
		if (elementFound)
		{
			dropdown = new Select(element);
		}
		try
		{
			dropdown.selectByIndex(index);
		}
		catch (WebDriverException ee)
		{
			logger.info("Last Try..  Is Foresee Dialog present? " + PopupHandler.closeForeseePopup());
			dropdown.selectByIndex(index);
		}
		catch (NullPointerException ee)
		{
			logger.error("Element not available to select ", ee.getMessage());
			Assert.fail();
		}
	}

	public static void hideElementByLocator(By byLocator)
	{
		WebElement element = BrowserDriver.getCurrentDriver().findElement(byLocator);
		((JavascriptExecutor) BrowserDriver.getCurrentDriver()).executeScript("arguments[0].style.visibility='hidden'",
				element);
	}

	public static void hideElementByWebElement(WebElement element)
	{
		((JavascriptExecutor) BrowserDriver.getCurrentDriver(BrowserDriver.BROWSER_TYPE)).executeScript(
				"arguments[0].style.visibility='hidden'",
				element);
	}

	public static void noDisplayElement(WebElement element)
	{
		js.executeScript("arguments[0].style.display='none'", element);
	}

	public static void noDisplayElementByLocator(By byLocator)
	{
		WebElement element = BrowserDriver.getCurrentDriver().findElement(byLocator);
		noDisplayElement(element);
	}

	public static void captureAndStore(WebElement element, String key)
	{
		BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.visibilityOf(element));
		logger.info("Trying to capture the text on the weblement :" + element);
		map.put(key, element.getText());
	}

	public static Boolean isElementPresent(String locator, LOCATOR_TYPES locateby)
	{
		return getElementsList(locator, locateby).size() > 0;
	}

	public static Boolean isElementVisible(String locator, LOCATOR_TYPES locateby)
	{
		return getElementIfVisible(locator, locateby) != null;

	}


	public static Boolean isElementVisibleinDomViaJS(WebElement element)
	{
		String script = "return arguments[0].offsetHeight || arguments[0].offsetWidth || arguments[0].getClientRects().length;";
		Long visibilitySize = (Long) js.executeScript(script, element);
		logger.info("element visibility value is {}", visibilitySize.toString());
		if (visibilitySize > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static List<WebElement> getElementsList(String locator, LOCATOR_TYPES locateby)
	{
		List<WebElement> elements = null;
		switch (locateby)
		{
			case XPATH:
				elements = BrowserDriver.getCurrentDriver().findElements(By.xpath(locator));
				break;
			case CSS:
				elements = BrowserDriver.getCurrentDriver().findElements(By.cssSelector(locator));
				break;
			case ID:
				elements = BrowserDriver.getCurrentDriver().findElements(By.id(locator));
				break;
			case LINKTEXT:
				elements = BrowserDriver.getCurrentDriver().findElements(By.linkText(locator));
				break;
			case PARTIAL_LINK_TEXT:
				elements = BrowserDriver.getCurrentDriver().findElements(By.partialLinkText(locator));
				break;
			case CLASS_NAME:
				elements = BrowserDriver.getCurrentDriver().findElements(By.className(locator));
				break;
			case NAME:
				elements = BrowserDriver.getCurrentDriver().findElements(By.name(locator));
				break;
			case TAG_NAME:
				elements = BrowserDriver.getCurrentDriver().findElements(By.tagName(locator));
				break;

		}
		return elements;
	}

	public static List<WebElement> getElementsList(WebElement baseElement, String locator, LOCATOR_TYPES locateby)
	{
		List<WebElement> elements = null;
		switch (locateby)
		{
			case XPATH:
				elements = baseElement.findElements(By.xpath(locator));
				break;
			case CSS:
				elements = baseElement.findElements(By.cssSelector(locator));
				break;
			case ID:
				elements = baseElement.findElements(By.id(locator));
				break;
			case LINKTEXT:
				elements = baseElement.findElements(By.linkText(locator));
				break;
			case PARTIAL_LINK_TEXT:
				elements = baseElement.findElements(By.partialLinkText(locator));
				break;
			case CLASS_NAME:
				elements = baseElement.findElements(By.className(locator));
				break;
			case NAME:
				elements = baseElement.findElements(By.name(locator));
				break;
			case TAG_NAME:
				elements = baseElement.findElements(By.tagName(locator));
				break;

		}
		return elements;
	}

	public static WebElement getElementIfVisible(String locator, LOCATOR_TYPES locateby)
	{
		WebElement element = null;
		List<WebElement> elements = getElementsList(locator, locateby);
		for (WebElement element1 : elements)
		{
			if (element1.isDisplayed())
			{
				element = element1;
				break;
			}
		}
		return element;
	}

	public static WebElement getElementIfClickable(String locator, LOCATOR_TYPES locateby)
	{
		WebElement element = null;
		List<WebElement> elements = getElementsList(locator, locateby);
		for (WebElement element1 : elements)
		{
			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.elementToBeClickable(element1));
			element = element1;
			break;
		}
		return element;
	}


	public static void moveToElement(String locator, LOCATOR_TYPES locateBy)
	{
		WebElement element = locateElement(locator, locateBy, BrowserDriver.BROWSER_TYPE);
		moveToElement(element);
	}

	public static void moveToElement(WebElement element)
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		actions.moveToElement(element);
		actions.perform();
	}

	public static HashMap<String, String> getProductCatAndId(String target)
	{
		HashMap<String, String> prodCategoryMap = new HashMap<>();
		if (target == null)
		{
			target = BrowserDriver.getCurrentDriver().getCurrentUrl();
		}
		prodCategoryMap.put("category", target.split("/c/")[1].split("/")[0]);

		prodCategoryMap.put("productId", target.split("/p/")[1].split("/")[0]);

		CacheService.getInstance().setDataMap("prodCategoryMap", prodCategoryMap);

		return prodCategoryMap;
	}

	public static String getAttributeOfVisibleElement(String locator, LOCATOR_TYPES locateBy, String attribute)
	{
		return getElementIfVisible(locator, locateBy).getAttribute(attribute);
	}

	public static String getAttributeOfElement(String locator, LOCATOR_TYPES locateBy, String attribute)
	{
		return getElementsList(locator, locateBy).get(0).getAttribute(attribute);
	}

	public static Boolean outputLogs(String severity)
	{
		Boolean hasEntries = false;
		for (LogEntry log : BrowserDriver.getBrowserLogs())
		{

			if (log.toString().contains(severity) || severity.equalsIgnoreCase("ALL"))
			{
				logger.info("Console Log Entry: {}", log.toString());
				hasEntries = true;
			}

		}
		return hasEntries;
	}

	public static Object getDomVariable(String script)
	{
		return js.executeScript(script);
	}

}
