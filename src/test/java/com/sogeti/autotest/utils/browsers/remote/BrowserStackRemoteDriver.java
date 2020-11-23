package com.sogeti.autotest.utils.browsers.remote;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.browserstack.BrowserStackJobDetails;


public class BrowserStackRemoteDriver extends AbstractRemoteBrowserDriver
{
	private static final String SELENIUM_DEVICE_VERSION = "SELENIUM_DEVICE_VERSION";
	private static final String SELENIUM_DEVICE_MODEL = "SELENIUM_DEVICE_MODEL";
	private static final Logger logger = LoggerFactory.getLogger(BrowserStackRemoteDriver.class.getName());

	@Override
	protected String createRemoteDriver(DesiredCapabilities capabilities)
	{
		// use browserstack not a local GRID
		logger.info("using browserstack as local hubURL is empty");

		capabilities.setCapability("browserstack.local", System.getenv("BROWSERSTACK_LOCAL"));
		capabilities.setCapability("browserstack.localIdentifier", System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER"));

		BrowserStackJobDetails jobDetails = new BrowserStackJobDetails();
		capabilities.setCapability("build", jobDetails.getBuild());
		capabilities.setCapability("project", jobDetails.getProject());

		capabilities.setCapability("browserstack.debug", Boolean.parseBoolean(System.getenv("REMOTE_DEBUG")));
		capabilities.setCapability("browserstack.networkLogs", Boolean.parseBoolean(System.getenv("REMOTE_NETWORK_DEBUG")));

		capabilities.setCapability("acceptSslCerts", "true");

		String device = System.getenv(SELENIUM_DEVICE);
		if (device.equalsIgnoreCase("android") || device.equalsIgnoreCase("iphone"))
		{
			setMobileCapabilities(device, capabilities);
		}
		else
		{
			setComputerCapabilities(device, capabilities);
		}

		String username = System.getenv("BROWSERSTACK_USERNAME");
		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		String url = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
		logger.info("BrowserStack URL: {}", url);
		return url;
	}

	private void setComputerCapabilities(String device, DesiredCapabilities capabilities)
	{
		capabilities.setCapability("browser", device);
		String browserVersion = System.getenv(SELENIUM_DEVICE_VERSION);
		if (StringUtils.isNotEmpty(browserVersion))
		{
			capabilities.setCapability("browser_version", browserVersion);
		}
		capabilities.setCapability("os", System.getenv(SELENIUM_PLATFORM));
		capabilities.setCapability("os_version", System.getenv("SELENIUM_PLATFORM_VERSION"));
		setDimensionCapabilities(capabilities);
		setPerformanceLogs(capabilities);

		if (device.toLowerCase().contains("chrome"))
		{
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--start-fullscreen");
			chromeOptions.addArguments("ignore-certificate-errors");
			chromeOptions.addArguments("disable-infobars"); //This disables the "Chrome is being controlled by automated test software" infobar		
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		}

	}

	private void setMobileCapabilities(String device, DesiredCapabilities capabilities)
	{
		capabilities.setCapability("browserName", device);
		capabilities.setCapability("device", System.getenv(SELENIUM_DEVICE_MODEL));
		capabilities.setCapability("realMobile", "true");
		capabilities.setCapability("os_version", System.getenv("SELENIUM_VERSION"));
	}
}
