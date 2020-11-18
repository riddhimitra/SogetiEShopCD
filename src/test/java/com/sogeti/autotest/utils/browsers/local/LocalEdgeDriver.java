package com.sogeti.autotest.utils.browsers.local;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.sogeti.autotest.utils.BrowserFactory;

import io.github.bonigarcia.wdm.WebDriverManager;


public class LocalEdgeDriver extends AbstractLocalBrowserDriver
{
	@Override
	public WebDriver createDriver()
	{
		WebDriverManager.edgedriver().setup();

		EdgeOptions edgeOptions = new EdgeOptions();

		Proxy seleniumProxy = BrowserFactory.getSeleniumProxy();
		if (seleniumProxy != null)
		{
			edgeOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		}
		edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return new EdgeDriver(edgeOptions);
	}
}
