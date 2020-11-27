package com.sogeti.autotest.utils.browsers.remote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserFactory;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.browserstack.BrowserStackResults;


public abstract class AbstractRemoteBrowserDriver
{
	private static final String SELENIUM_HUB_URL = "SELENIUM_HUB_URL";

	private static final String SELENIUM_DEVICE_ORIENTATION = "SELENIUM_DEVICE_ORIENTATION";

	private static final String SELENIUM_VERSION = "SELENIUM_VERSION";

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRemoteBrowserDriver.class.getName());

	private static final String SELENIUM_BROWSER = "SELENIUM_BROWSER";
	protected static final String SELENIUM_PLATFORM = "SELENIUM_PLATFORM";
	protected static final String SELENIUM_DEVICE = "SELENIUM_DEVICE";
	protected static final String SELENIUM_RESOLUTION = "SELENIUM_RESOLUTION";

	private boolean runningRemotely = false;

	public WebDriver createDriver() throws InterruptedException, MalformedURLException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		String orientation = (System.getenv(SELENIUM_DEVICE_ORIENTATION));
		if (orientation == null)
		{
			capabilities.setCapability("deviceOrientation", orientation);
			LOGGER.info("Orientation: {}", orientation);
		}
		String idleTimeout = null;
		idleTimeout = Config.getCommonOptionalPropValue("config.idleTimeout");

		if (StringUtils.isNotEmpty(idleTimeout))
		{

			capabilities.setCapability("idleTimeout", idleTimeout);
			LOGGER.info("idleTimeout: {}", idleTimeout);

		}

		capabilities.setBrowserName(System.getenv(SELENIUM_BROWSER));

		capabilities.setCapability("acceptSslCert", true);
		capabilities.setCapability("public", "share");

		String hubUrl = (System.getenv(SELENIUM_HUB_URL));
		if (StringUtils.isEmpty(hubUrl))
		{
			hubUrl = createRemoteDriver(capabilities);
			runningRemotely = true;
		}

		LOGGER.info("Setting Grid Hub: {}", hubUrl);

		LOGGER.info("Browser: {}", System.getenv(SELENIUM_BROWSER));
		LOGGER.info("Platform: {}", System.getenv(SELENIUM_PLATFORM));
		LOGGER.info("Version: {}", System.getenv(SELENIUM_VERSION));
		LOGGER.info("Device: {}", System.getenv(SELENIUM_DEVICE));

		RemoteWebDriver driver = null;

		//check the queue before creating the driver which may just fail
		int queueSize = new BrowserStackResults().getQueue();
		int maxQueueSize = Integer.parseInt(Config.getCommonMandatoryPropValue("browser.queue.size"));
		int i = 0;
		while (queueSize == maxQueueSize && i < 10)
		{
			LOGGER.error("Remote Queue is full, waiting 30 seconds before trying again");
			Thread.sleep(30000);
			queueSize = new BrowserStackResults().getQueue();
			i++;

		}

		try
		{

			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
		}
		catch (IOException e)
		{
			LOGGER.error("Couldn't create Remote WebDriver:", e);
			return null;
		}
		catch (WebDriverException e)
		{
			LOGGER.info(
					"webDriverException, will sleep for 30 seconds and try again." + e.toString());
			Thread.sleep(30000);
			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
		}

		if (!BrowserFactory.isDimensionBrowserSupported())
		{
			String browserDimensions = BrowserFactory.getBrowserDimensions();
			int browserWidth = Integer.parseInt(browserDimensions.split(",")[0]);
			int browserHeight = Integer.parseInt(browserDimensions.split(",")[1]);
			Dimension dim = new Dimension(browserWidth, browserHeight);
			driver.manage().window().setSize(dim);
		}

		if (runningRemotely)
		{
			logSessionDetails(driver);
		}

		return driver;

	}

	protected void setDimensionCapabilities(DesiredCapabilities capabilities)
	{
		String screenSize = null;

		String browserDimensions = BrowserFactory.getBrowserDimensions();
		if (StringUtils.isNotBlank(browserDimensions))
		{

			if (BrowserFactory.isDimensionBrowserSupported())
			{
				String browserWidth = browserDimensions.split(",")[0];
				String browserHeight = browserDimensions.split(",")[1];
				screenSize = browserWidth + "x" + browserHeight;
			}
			else
			{
				LOGGER.info("dimension not supported but will try the manual values anyway");
				String browserWidth = browserDimensions.split(",")[0];
				String browserHeight = browserDimensions.split(",")[1];
				screenSize = browserWidth + "x" + browserHeight;
			}
		}
		else if ((System.getenv(SELENIUM_PLATFORM) != null)
				&& (System.getenv(SELENIUM_PLATFORM).contains("OS") || System.getenv(SELENIUM_PLATFORM).contains("Mac")))

		{
			screenSize = Config.getCommonOptionalPropValue("browser.mac.resolution");
		}
		else
		{
			screenSize = Config.getCommonOptionalPropValue("browser.windows.resolution");
		}

		if (StringUtils.isNotEmpty(screenSize))
		{
			LOGGER.info("Setting Desktop Size: {}", screenSize);
			capabilities.setCapability("screenResolution", screenSize);
			capabilities.setCapability("resolution", screenSize);
		}
	}


	protected void setPerformanceLogs(DesiredCapabilities capabilities)
	{

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

		Object lognetwork = CacheService.getInstance().getDataMap().get("networklogs");
		if (lognetwork != null && lognetwork.toString().contains("true"))
		{
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		}

	}

	protected void logSessionDetails(RemoteWebDriver driver)
	{
	}

	protected abstract String createRemoteDriver(DesiredCapabilities capabilities);
}
