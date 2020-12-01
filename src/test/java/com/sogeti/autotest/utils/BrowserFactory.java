package com.sogeti.autotest.utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.constants.BrowserResolutions;
import com.sogeti.autotest.constants.Browsers;
import com.sogeti.autotest.utils.browsers.local.AbstractLocalBrowserDriver;
import com.sogeti.autotest.utils.browsers.local.LocalChromeDriver;
import com.sogeti.autotest.utils.browsers.local.LocalEdgeDriver;
import com.sogeti.autotest.utils.browsers.local.LocalFirefoxDriver;
import com.sogeti.autotest.utils.browsers.local.LocalInternetExplorerDriver;
import com.sogeti.autotest.utils.browsers.local.LocalSafariDriver;
import com.sogeti.autotest.utils.browsers.remote.AbstractRemoteBrowserDriver;
import com.sogeti.autotest.utils.browsers.remote.BrowserStackRemoteDriver;
import com.sogeti.autotest.utils.browsers.remote.SauceLabsRemoteDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.BlacklistEntry;
import net.lightbody.bmp.proxy.CaptureType;


public class BrowserFactory
{
	private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class.getName());

	private static final String BROWSER_STACK = "BrowserStack";
	private static final String REMOTE_BROWSER_DRIVER_PROVIDER = "RemoteBrowserDriverProvider";
	private static final String SELENIUM_MODE = "SELENIUM_MODE";

	public enum BROWSER_TYPES
	{
		ADMIN, ONE, TWO
	}

	private static BrowserMobProxy bmProxy;
	private static String zapProxy;
	private static Proxy seleniumProxy;

	public static Proxy getSeleniumProxy()
	{
		return seleniumProxy;
	}

	public static void setSeleniumProxy(Proxy seleniumProxy)
	{
		BrowserFactory.seleniumProxy = seleniumProxy;
	}

	public static BrowserMobProxy getProxy()
	{
		return bmProxy;
	}

	public static void setProxy(BrowserMobProxy proxy)
	{
		BrowserFactory.bmProxy = proxy;
	}

	public static void startBmProxy(String proxySettings)
	{
		bmProxy = new BrowserMobProxyServer();

		ArrayList<BlacklistEntry> arraylist = new ArrayList<>();

		switch (proxySettings)
		{
			case "ALLOW_ALL":
				break;
			case "NO_MAXYMISER":
				arraylist.add(new BlacklistEntry(".*service\\.maxymiser\\.net.*", 204));
				break;
			case "NO_TRACKING":
				arraylist.add(new BlacklistEntry(".*googletagmanager\\.com.*", 204));
				break;
			default:
				arraylist.add(new BlacklistEntry(".*googletagmanager\\.com.*", 204));
				arraylist.add(new BlacklistEntry(".*service\\.maxymiser\\.net.*", 204));
		}
		bmProxy.setBlacklist(arraylist);
		bmProxy.start(0);
	}

	/**
	 * creates the browser driver specified in the system property "browser" if
	 * no property is set then a firefox browser driver is created. The allow
	 * properties are firefox, safari and chrome e.g to run with safari, pass in
	 * the option -Dbrowser=safari at runtime
	 * @return WebDriver
	 * @throws MalformedURLException
	 */
	public static WebDriver getBrowser()
	{
		WebDriver driver = null;

		if (StringUtils.isNoneBlank(System.getProperty("PROXY")))
		{
			String proxyType = System.getProperty("PROXY");
			if (StringUtils.containsIgnoreCase(proxyType, "ZAP"))
			{
				zapProxy = Config.getCommonMandatoryPropValue("proxy.zap");
				seleniumProxy = new Proxy();
				seleniumProxy.setHttpProxy(zapProxy).setSslProxy(zapProxy);
			}
			else
			{
				startBmProxy(System.getProperty("PROXY"));
				seleniumProxy = ClientUtil.createSeleniumProxy(bmProxy);
			}
		}

		// Setting Selenium Mode locally in Eclipse lets you use your local
		// Browser rather than a selenium grid browser
		if (System.getenv(SELENIUM_MODE) == null)
		{
			AbstractRemoteBrowserDriver remoteBrowserDriver;
			String remoteBrowserDriverProvider = System.getenv(REMOTE_BROWSER_DRIVER_PROVIDER);
			logger.debug("remote Driver Provider from env is "+remoteBrowserDriverProvider);
			if (remoteBrowserDriverProvider == null) {
				remoteBrowserDriverProvider = Config.getCommonOptionalPropValue(REMOTE_BROWSER_DRIVER_PROVIDER);
			}
			if (remoteBrowserDriverProvider != null && remoteBrowserDriverProvider.equalsIgnoreCase(BROWSER_STACK))
			{
				remoteBrowserDriver = new BrowserStackRemoteDriver();
			}
			else
			{
				remoteBrowserDriver = new SauceLabsRemoteDriver();
			}
			try
			{
				driver = remoteBrowserDriver.createDriver();
			}
			catch (MalformedURLException e)
			{
				logger.info("malformed" + e);
			}
			catch (InterruptedException e)
			{
				logger.info("INterruption" + e);
			}
		}
		else
		{
			AbstractLocalBrowserDriver localBrowserDriver = getLocalBrowserDriver(SELENIUM_MODE);
			driver = localBrowserDriver.createDriver();
		}
		if (driver != null)
			addAllBrowserSetup(driver);

		return driver;
	}

	public static AbstractLocalBrowserDriver getLocalBrowserDriver(String browserProperty)
	{
		Browsers browser = Browsers.browserForName(System.getenv(browserProperty));
		AbstractLocalBrowserDriver localBrowser;
		switch (browser)
		{
			case CHROME:
				LocalChromeDriver chromeBrowser = new LocalChromeDriver();
				chromeBrowser.setHeadless(false);
				localBrowser = chromeBrowser;
				break;
			case HEADLESSCHROME:
				LocalChromeDriver chromeBrowserHeadless = new LocalChromeDriver();
				chromeBrowserHeadless.setHeadless(true);
				localBrowser = chromeBrowserHeadless;
				break;
			case SAFARI:
				localBrowser = new LocalSafariDriver();
				break;
			case EDGE:
				localBrowser = new LocalEdgeDriver();
				break;
			case IE:
				localBrowser = new LocalInternetExplorerDriver();
				break;
			case FIREFOX:
			default:
				logger.info("I'm creating a firefox because browser is: {}", browser);
				localBrowser = new LocalFirefoxDriver();
				break;
		}
		return localBrowser;
	}

	private static void addAllBrowserSetup(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		String browserDimensions = BrowserFactory.getBrowserDimensions();
		/*
		 * if (StringUtils.isBlank(browserDimensions)) { try {
		 * logger.info("setting window fullscreen");
		 * driver.manage().window().fullscreen();
		 * 
		 * 
		 * } catch (WebDriverException e) {
		 * logger.info("couldn't full screen trying to make full screen:" + e); try {
		 * driver.manage().window().maximize(); } catch (Exception e1) { // TODO
		 * Auto-generated catch block logger.info("couldn't maximise trying resize:" +
		 * e); try { Thread.sleep(10000); driver.manage().window().setPosition(new
		 * Point(0, 0)); driver.manage().window().setSize(new Dimension(1920,1200)); }
		 * catch(Exception ee) { logger.info("couldn't manually resize:" + e); } } } }
		 */
		
		if (seleniumProxy != null && bmProxy != null)
		{
			bmProxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		}
	}

	public static String getBrowserDimensions()
	{
		String browserProfile = System.getenv("BROWSER_PROFILE");
		String browserDimensions = "";

		if (StringUtils.isNotEmpty(browserProfile))
		{
			browserDimensions = BrowserResolutions.getDimensionByName(browserProfile);
		}
		else
		{
			String browserHeight = System.getenv("BROWSER_HEIGHT");
			String browserWidth = System.getenv("BROWSER_WIDTH");

			if (StringUtils.isNotEmpty(browserHeight) && StringUtils.isNotEmpty(browserWidth))
			{
				browserDimensions = browserWidth + "," + browserHeight;
			}
		}
		return browserDimensions;
	}

	public static Boolean isDimensionBrowserSupported()
	{
		String browserProfile = System.getenv("BROWSER_PROFILE");
		Boolean dimensionsSupported = true;

		if (StringUtils.isNotEmpty(browserProfile))
		{
			dimensionsSupported = BrowserResolutions.getBrowserSupportedByName(browserProfile);
		}

		String browserHeight = System.getenv("BROWSER_HEIGHT");
		String browserWidth = System.getenv("BROWSER_WIDTH");

		if (StringUtils.isNotEmpty(browserHeight) && StringUtils.isNotEmpty(browserWidth))
		{
			dimensionsSupported = false;
		}

		return dimensionsSupported;
	}
}
