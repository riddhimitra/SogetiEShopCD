package com.sogeti.autotest.utils.browsers.local;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.sogeti.autotest.utils.BrowserFactory;

public class LocalSafariDriver extends AbstractLocalBrowserDriver
{
	@Override
	public WebDriver createDriver()
	{
		SafariOptions safariOptions = new SafariOptions();

		Proxy seleniumProxy = BrowserFactory.getSeleniumProxy();
		if (seleniumProxy != null)
		{
			safariOptions.setProxy(seleniumProxy);
			safariOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		}
		safariOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		return new SafariDriver(safariOptions);
	}
}
