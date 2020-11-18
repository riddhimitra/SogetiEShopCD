package com.sogeti.autotest.utils.browsers.remote;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.utils.Config;


public class SauceLabsRemoteDriver extends AbstractRemoteBrowserDriver
{
	private static final Logger logger = LoggerFactory.getLogger(SauceLabsRemoteDriver.class.getName());

	@Override
	protected String createRemoteDriver(DesiredCapabilities capabilities)
	{
		// use sauceLabs not a local GRID
		logger.info("using sauceLabs as hubURL is empty");
		capabilities.setCapability("deviceName", System.getenv(SELENIUM_DEVICE));
		capabilities.setCapability(CapabilityType.PLATFORM, System.getenv(SELENIUM_PLATFORM));

		String hubUrl = Config.getHubUrl();

		String sauceTunnel = null;

		sauceTunnel = Config.getTunnelIdentifier();
		if (StringUtils.isNotEmpty(sauceTunnel))
		{
			logger.info("Setting Sauce Tunnel identifier: {}", sauceTunnel);
			capabilities.setCapability("tunnelIdentifier", sauceTunnel);
		}

		setDimensionCapabilities(capabilities);

		String prerun = (System.getenv("SAUCE_PRERUN_URL"));
		if (StringUtils.isNotBlank(prerun))
		{
			logger.info("setting a prerun script: {}", prerun);
			capabilities.setCapability("prerun", prerun);
		}

		// set job visibility to public:restricted
		capabilities.setCapability("public", "public restricted");
		return hubUrl;
	}

	@Override
	protected void logSessionDetails(RemoteWebDriver driver)
	{
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", sessionId,
				"sogetiAutomation");
		logger.info(message);

		logger.info("View Job: http://saucelabs.com/tests/{}", sessionId);
	}
}
