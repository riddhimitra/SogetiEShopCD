package com.sogeti.autotest.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;


public class AssertHandler extends BaseHandler
{

	private static final Logger logger = LoggerFactory.getLogger(AssertHandler.class.getName());

	public static void assertElementPresent(WebElement element)
	{
		assertElementPresent(element, BrowserDriver.BROWSER_TYPE);
	}

	public static void assertElementPresent(WebElement element, BROWSER_TYPES driverType)
	{
		assertTrue("Element " + element + "is not present", isElementPresent(element, driverType));
	}

	public static void assertTextEqualsOnElement(WebElement element, String expectedText)
	{
		assertTextEqualsOnElement(element, expectedText, BrowserDriver.BROWSER_TYPE);
	}

	public static void assertTextEqualsOnElement(WebElement element, String expectedText, BROWSER_TYPES driverType)
	{
		isElementPresent(element, driverType);
		assertTrue("Expected Text: " + expectedText + "\nActual Text: " + element.getText(),
				expectedText.equalsIgnoreCase(element.getText()));

	}
	
	public static void assertTextEqualsInInput(WebElement element, String expectedText, BROWSER_TYPES driverType)
	{
		isElementPresent(element, driverType);
		assertTrue("Expected Text: " + expectedText + "\nActual Text: " + element.getAttribute("value"),
				expectedText.equalsIgnoreCase(element.getAttribute("value")));

	}

	public static void assertString(String expected, String actual)
	{
		assertEquals(expected.toLowerCase(), actual.toLowerCase());
	}

	public static void assertTextContainsOnElement(WebElement element, String expectedText)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		assertTrue("Element not found", elementFound);
		String actualText = ActionHandler.getElementText(element).toLowerCase().trim();
		assertTrue("Expected Text: " + expectedText.trim().toLowerCase() + "\n Actual Text : " + actualText,
				actualText.contains(expectedText.trim().toLowerCase()));
	}
	
	public static void assertTextNotContainsOnElement(WebElement element, String expectedText)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		assertTrue("Element not found", elementFound);
		String actualText = ActionHandler.getElementText(element).toLowerCase().trim();
		assertFalse("Expected Text: " + expectedText.trim().toLowerCase() + "\n Actual Text : " + actualText,
				actualText.contains(expectedText.trim().toLowerCase()));
	}

	public static void assertElementAttribute(WebElement element, String attribute, String expectedAttribute, Boolean status)
	{
		boolean elementFound = isElementPresent(element, BrowserDriver.BROWSER_TYPE);
		assertTrue("Element not found", elementFound);
		String actualAttribute = element.getAttribute(attribute).toLowerCase();
		if (status) {
		assertTrue("Expected Attribute: " + expectedAttribute + "\n Actual Attribute : " + actualAttribute,
				actualAttribute.equalsIgnoreCase(expectedAttribute));
		} else {
			assertFalse("Expected Attribute: " + expectedAttribute + "\n Actual Attribute : " + actualAttribute,
					actualAttribute.equalsIgnoreCase(expectedAttribute));
		}
	}

	public static void assertElementAttribute(WebElement element, String attribute, String expectedAttribute)
	{
		assertElementAttribute(element, attribute, expectedAttribute, true);
	}

	public static void assertElementNotAttribute(WebElement element, String attribute, String expectedAttribute)
	{
		assertElementAttribute(element, attribute, expectedAttribute, false);
	}

	public static void assertElementNotPresent(WebElement element, BROWSER_TYPES driverType)
	{
		BrowserDriver.setWait(1);
		assertFalse("Element " + element + " should not be present", isElementNotPresent(element, driverType));
		BrowserDriver.resetWait();
	}

	public static void assertElementNotPresent(WebElement element)
	{
		assertElementNotPresent(element, BrowserDriver.BROWSER_TYPE);

	}

	public static void assertResultTrue(boolean result, String message)
	{
		assertTrue(message, result);
	}

	public static void assertValues(List<String> values)
	{
		for (int index = 0; index < values.size() - 1; index++)
		{
			logger.info(values.get(index) + " and " + values.get(index + 1));
			Assert.assertTrue("Selected values are not equal " + values.get(index) + " and " + values.get(index + 1),
					values.get(index).equals(values.get(index + 1)));
		}
	}

	public static void assertDropdownNotContainValue(WebElement element, String value)
	{
		Boolean found = false;
		List<WebElement> allOptions = getDropDownOptions(element);

		for (WebElement dropdownValue : allOptions)
		{
			//This gets the text of the value attribute
			String actualValue = dropdownValue.getAttribute("value").toString();
			logger.info("Select Value is: " + actualValue);
			if (actualValue.equals(value))
			{
				found = true;
				break;
			}
		}
		assertFalse(value + " IS in the list of options", found);
	}

	public static void assertDropdownContainsValue(WebElement element, String value)
	{
		Boolean found = false;
		List<WebElement> allOptions = getDropDownOptions(element);

		for (WebElement dropdownValue : allOptions)
		{
			//This gets the text of the value attribute
			String actualValue = dropdownValue.getAttribute("value").toString();
			logger.info("Select Value is: " + actualValue);
			if (actualValue.equals(value))
			{
				found = true;
				break;
			}
		}
		assertTrue(value + " is NOT in the list of options", found);
	}
	
	
	public static void assertDropdownNotContainsText(WebElement element, String value)
	{
		Boolean found = false;
		List<WebElement> allOptions = getDropDownOptions(element);

		for (WebElement dropdownText : allOptions)
		{
			//This gets the text
			String actualText = ActionHandler.getElementText(dropdownText).trim();
			logger.info("Select Text is: " + actualText);
			
			if (actualText.equals(value))
			{
				found = true;
				break;
			}
		}
		assertFalse(value + " is NOT in the list of options", found);
	}
	
	
	public static void assertDropdownContainsText(WebElement element, String value)
	{
		Boolean found = false;
		List<WebElement> allOptions = getDropDownOptions(element);

		for (WebElement dropdownText : allOptions)
		{
			//This gets the text
			String actualText = ActionHandler.getElementText(dropdownText).trim();
			logger.info("Select Text is: " + actualText);
			
			if (actualText.equals(value))
			{
				found = true;
				break;
			}
		}
		assertTrue(value + " is NOT in the list of options", found);
	}
	

	public static void assertDropdownSelectedValue(WebElement element, String value)
	{
		Boolean found = false;
		Select select = new Select(element);
		WebElement option = select.getFirstSelectedOption();
		String selectedVal = option.getAttribute("value");

		if (selectedVal.trim().equalsIgnoreCase(value.trim()))
		{
			found = true;
		}

		assertTrue(value + " is NOT the selected option. "+selectedVal + " is", found);

	}

}
