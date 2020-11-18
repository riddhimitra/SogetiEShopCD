package com.sogeti.autotest.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Assert;


public class Utils
{
	private static final Logger logger = LoggerFactory.getLogger(Utils.class.getName());

	public static String getCurrentTimeStamp()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static ArrayList<String> getKeysList(HashMap<String, ?> hashMapType)
	{
		ArrayList<String> keysList = new ArrayList<>();
		for (String key : hashMapType.keySet())
		{
			keysList.add(key);
		}
		return keysList;
	}

	public static String removePrecedingCharacters(String strVal, int noOfchars)
	{
		return strVal.substring(noOfchars);
	}
	
	public static boolean isNumeric(String strNum) {
	   
		if (strNum == null || strNum.isEmpty()) {
			return false;
		}
		
		try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	public static boolean isProperDiscountOrTaxCalculated(String discPercent, String subTotalStr, String actualDiscontStr)
	{

		double percent = 0.0;
		boolean result = false;

		percent = Double.parseDouble(Config.getLocalisedMandatoryPropValue(discPercent));
		double subtotalVal = Double.parseDouble(subTotalStr);
		double actualDiscountPrice = Double.parseDouble(actualDiscontStr);
		double expectedDiscountprice = subtotalVal * percent / 100;

		if (actualDiscountPrice == expectedDiscountprice)
		{
			result = true;
		}


		return result;
	}

	public static Double roundingOffDecimal(Double value)
	{
		DecimalFormat formattedValue = new DecimalFormat(".##");
		return Double.parseDouble(formattedValue.format(value));

	}
	
	public static String getLocatorFromWebElement(WebElement element) {

		String[] elementDetails = element.toString().split("->");
	   return elementDetails[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
	}
	
	public static By rebuildElement(WebElement element) {

		String locatorString = getLocatorFromWebElement(element);
		
		String[] elementDetails = locatorString.toString().split(":");
		logger.info(elementDetails[0]+":"+elementDetails[1]);
		By bySelector = null;
		if (elementDetails[0].contains("css")) {
			bySelector = By.cssSelector(elementDetails[1].trim());
		} else if (elementDetails[0].contains("xpath")) {
			bySelector = By.xpath(elementDetails[1].trim());
		} else if (elementDetails[0].contains("id")) {
			bySelector = By.id(elementDetails[1].trim());
		} else {
			Assert.fail("unknown selector");
		}
	   return bySelector;
	}

}
