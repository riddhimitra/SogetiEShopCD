package com.sogeti.autotest.utils;

import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.Config;

public class Utility {

	public static String getProductSKU(String product) throws IOException {
		String productSku = null;
		if (product.contains("sku.")) {
			if (product.contains("sku.random")) {
				String[] productList = Config.getLocalisedMandatoryPropValue(product).split(",");
				Random rand = new Random();
				productSku = productList[rand.nextInt(productList.length)];

			} else {
				productSku = Config.getLocalisedMandatoryPropValue(product);
			}
		} else {
			productSku = product;
		}
		CacheService.getInstance().setDataMap("product", product);
		return productSku;
	}

	public static void pageDown()
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.DOWN).perform();
		actions.keyDown(Keys.CONTROL).release().perform();
		BrowserDriver.wait(2000);

	}

	public static void pageUp()
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		actions.sendKeys(Keys.PAGE_UP).perform();
		BrowserDriver.wait(2000);
	}

	public static void pageTop()
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.UP).perform();
		actions.keyDown(Keys.CONTROL).release().perform();
		BrowserDriver.wait(2000);
	}

	public static void pressTab()
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		actions.sendKeys(Keys.TAB).perform();
		BrowserDriver.wait(2000);
	}

	public static void scrollDown()
	{
		Actions actions = new Actions(BrowserDriver.getCurrentDriver());
		for (int j = 0; j < 4; j++)
		{
			actions.sendKeys(Keys.ARROW_DOWN);
		}
		BrowserDriver.wait(2000);
	}
}