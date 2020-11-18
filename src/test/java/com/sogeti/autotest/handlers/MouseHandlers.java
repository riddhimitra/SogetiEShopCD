package com.sogeti.autotest.handlers;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;

public class MouseHandlers {

	private static final Logger logger = LoggerFactory.getLogger(MouseHandlers.class.getName());

	public static void hover(WebElement element) {

		try {
			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.visibilityOf(element));
			logger.info("Trying to hover on the weblement :" + element);
			Actions actions = new Actions(BrowserDriver.getCurrentDriver());
			actions.moveToElement(element).build().perform();
			logger.info("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void moveAndClick(WebElement element) {

		try {

			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.visibilityOf(element));
			logger.info("Trying to move and click on the weblement :" + element);
			Actions actions = new Actions(BrowserDriver.getCurrentDriver());
			actions.moveToElement(element).click().perform();
			logger.info("Success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void clickAndHold(WebElement element) {

		try {
			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE).until(ExpectedConditions.visibilityOf(element));
			logger.info("Trying to hover on the weblement :" + element);
			Actions actions = new Actions(BrowserDriver.getCurrentDriver());
			actions.moveToElement(element).clickAndHold().build().perform();
			logger.info("Success");
		} catch (WebDriverException e) {
			if(PopupHandler.closeForeseePopup()) {
				return;
			}
			logger.error("WebDriverException :" + e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
