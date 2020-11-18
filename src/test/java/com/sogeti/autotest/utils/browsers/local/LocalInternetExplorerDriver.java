package com.sogeti.autotest.utils.browsers.local;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.sogeti.autotest.utils.BrowserFactory;

import io.github.bonigarcia.wdm.WebDriverManager;


public class LocalInternetExplorerDriver extends AbstractLocalBrowserDriver
{
	@Override
	public WebDriver createDriver()
	{
		WebDriverManager.iedriver().setup();
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();

		Proxy seleniumProxy = BrowserFactory.getSeleniumProxy();
		if (seleniumProxy != null)
		{
			ieOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		}
		ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return new InternetExplorerDriver(ieOptions);
	}
}
