package com.sogeti.autotest.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.handlers.BaseHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.model.Setup;
import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.BrowserFactory;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.NavigatorFactory;
import com.sogeti.autotest.utils.Utils;

public class Navigation extends Helper{

	private static final Logger logger = LoggerFactory.getLogger(Navigation.class.getName());

	public void given_I_navigate_to(String page) throws IOException
	{
		setDefaultBrowserType();
		given_I_navigate_to(page,BrowserDriver.BROWSER_TYPE);
	}

	public void given_I_navigate_to(String page, BROWSER_TYPES driverType) throws IOException
	{
		Helper.initPageFactories(driverType);
		StringBuilder url = new StringBuilder();
		String pageToLoad = Config.getLocalisedMandatoryPropValue("url.homepage");
		String frontendurl = Config.getLocalisedMandatoryPropValue("url.frontend");
		logger.info("FrontendUrl is : " + frontendurl);
		if (page.contains("home"))
		{
			url.append(pageToLoad);
		} else if (page.contains("cart")) {
			url.append(frontendurl + "/cart");
		}
		
		else if (page.contains("my-account"))
		{
			url.append(frontendurl);
			url.append("/my-account");
		}
		else
		{
			url.append(frontendurl + page);
		}

		if (BrowserFactory.getProxy() != null)
		{
			String harName = Utils.getCurrentTimeStamp();
			BrowserFactory.getProxy().newHar(harName);
			CacheService.getInstance().setDataMap("harName", harName);
		}

		BrowserDriver.loadPage(url.toString(), driverType);

	}


	public static void given_I_navigate_to(String environment, String page) throws IOException
	{
		given_user_navigate_to(environment, page, BrowserDriver.BROWSER_TYPE);
	}

	public static void given_user_navigate_to(String environment, String page, BrowserFactory.BROWSER_TYPES browser)
			throws IOException

	{
		logger.info("Entering: I navigate to " + environment + " " + page);
		try {
			Setup.setLocale(environment.toUpperCase());
		} catch (Exception e) {
			logger.error("Unknown environment: \n"+e);
			assert (false);
		}

		CacheService.getInstance().setDataMap("userEnv", Config.getLocalisedMandatoryPropValue("user.env"));

		NavigatorFactory.getNavigator(browser).given_I_navigate_to(page, browser);

	}

}
