package com.sogeti.autotest.utils.browsers.local;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.BrowserFactory;
import com.sogeti.autotest.utils.CacheService;

import io.github.bonigarcia.wdm.WebDriverManager;


public class LocalChromeDriver extends AbstractLocalBrowserDriver
{
	private static final Logger logger = LoggerFactory.getLogger(LocalChromeDriver.class.getName());

	private boolean headless;

	@Override
	public WebDriver createDriver()
	{

		String chromedriverbin = System.getProperty("CHROMEDRIVER_BIN");
		if (StringUtils.isNotBlank(chromedriverbin))
		{
			logger.info("CHROMEDRIVER_BIN is " + System.getProperty("CHROMEDRIVER_BIN"));
			System.setProperty("webdriver.chrome.driver", chromedriverbin);
		}
		else if (StringUtils.isNoneBlank(System.getProperty("USE_LOCALDRIVER")))
		{
			WebDriverManager.chromedriver().forceCache().setup();
		}
		else if (StringUtils.isNoneBlank(System.getProperty("CHROMEDRIVER_VERSION")))
		{
			WebDriverManager.chromedriver().version(System.getProperty("CHROMEDRIVER_VERSION")).setup();
		}
		else
		{
			WebDriverManager.chromedriver().setup();
		}

		ChromeOptions chromeOptions = disableChromeForms();
		String browserDimensions = BrowserFactory.getBrowserDimensions();
		if (StringUtils.isNotEmpty(browserDimensions))
		{
			chromeOptions.addArguments("--window-size=" + browserDimensions);
			logger.info("BrowserDimensions are {}", browserDimensions);
		}
		else
		{
			//chromeOptions.addArguments("--start-maximized");
			//chromeOptions.addArguments("--start-fullscreen");
		}
		if (headless)
		{
			chromeOptions.addArguments("headless");
			chromeOptions.addArguments("--disable-gpu");

		}

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

		Object lognetwork = CacheService.getInstance().getDataMap().get("networklogs");
		if (lognetwork != null && lognetwork.toString().contains("true"))
		{
			chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		}

		Proxy seleniumProxy = BrowserFactory.getSeleniumProxy();
		if (seleniumProxy != null)
		{
			chromeOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		}

		/*
		 * Headless will not work with bad certificate sites (dev envs) at
		 * present https://bugs.chromium.org/p/chromium/issues/detail?id=721739
		 * so can only be used for preprod and prod
		 * debug using chromeOptions.addArguments("remote-debugging-port=9223")
		 */

		chromeOptions.addArguments("ignore-certificate-errors");
		chromeOptions.addArguments("disable-infobars"); //This disables the "Chrome is being controlled by automated test software" infobar
		chromeOptions.setAcceptInsecureCerts(true);
		return new ChromeDriver(chromeOptions);
	}

	@Override
	public void addCapabilities(DesiredCapabilities capabilities)
	{
		ChromeOptions chromeOptions = disableChromeForms();
		if (BrowserFactory.isDimensionBrowserSupported())
		{
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--start-fullscreen");
		}

		chromeOptions.addArguments("--ignore-certificate-errors");
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	}

	private ChromeOptions disableChromeForms()
	{
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	public void setHeadless(boolean headless)
	{
		this.headless = headless;
	}
}
