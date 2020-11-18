package com.sogeti.autotest.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserDriver;



public class PopupHandler extends BaseHandler
{

	private static final Logger LOGGER = LoggerFactory.getLogger(PopupHandler.class.getName());
	private static final String BX_CSS = "button[data-click='close']";
	private static final String BXBOX_CSS = ".bxc.bx-base.bx-impress";

	public static boolean closeForeseePopup()
	{

		Boolean popupHere = false;

		try
		{
			WebElement webElement = ActionHandler.locateElement(BX_CSS, LOCATOR_TYPES.CSS, BrowserDriver.BROWSER_TYPE);
			ActionHandler.hideElementByLocator(By.cssSelector(BXBOX_CSS));
			webElement.click();
			BrowserDriver.getWait(BrowserDriver.BROWSER_TYPE)
					.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(BX_CSS)));
			popupHere = true;
			LOGGER.info("BX popup was here");
		}
		catch (TimeoutException e)
		{
			if (!popupHere)
				popupHere = false;
			LOGGER.info("BX popup was NOT here");
		}

		return popupHere;


	}


}
