package com.sogeti.autotest.helper;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.utils.BrowserDriver;

public class CartHelper extends Helper {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartHelper.class.getName());

	public static void emptyCart() {
		int cartSize = cartContainer.removeProduct.size();
		while (cartSize > 0) {
			WebElement element = cartContainer.removeProduct.get(cartSize - 1);
			ActionHandler.scrollElementIntoView(element);
			ActionHandler.click(element);
			ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
			cartSize = cartContainer.removeProduct.size(); //Need to let webdriver get the real count not just decrement the while count
		}
		LOGGER.info("{} now in your basket after emptying", cartContainer.cartitems.size());
		Assert.assertTrue("All products are not removed!!", cartContainer.cartitems.isEmpty());
	}

	}
