package com.sogeti.autotest.handlers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;

public class VerifyHandler extends BaseHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(VerifyHandler.class.getName());
	
	public static boolean verifyElementPresent(WebElement element) {
		return verifyElementPresent(element, BROWSER_TYPES.ONE);
	}
	
	public static boolean verifyElementPresent(WebElement element, BROWSER_TYPES driverType) {
		boolean status = false;
		try {
			BrowserDriver.getWait(driverType).until(ExpectedConditions.visibilityOf(element));
			status = element.isDisplayed();
		} catch (NoSuchElementException e) {
			logger.info("Verifying the presence of element: "+e);
		}

		catch (Exception e) {
			logger.info("Verifying the presence of element: "+e);
		}
		return status;
	}
	
	public static boolean verifyTitleContainsText(String text) {
		return verifyTitleContainsText(text, BrowserDriver.BROWSER_TYPE);
	}
	
	public static boolean verifyTitleContainsText(String text, BROWSER_TYPES driverType) {
		logger.info("Verifying the title contains the text ["+text+"]");
		boolean status = false;
		try {
			BrowserDriver.getWait(driverType).until(ExpectedConditions.titleContains(text));
			status = true;
			logger.info("Success");
		} catch (NoSuchElementException e) {
			status = false;
		}

		catch (Exception e) {
			status = false;
		}
		return status;
	}

}
