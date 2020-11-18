package com.sogeti.autotest.utils.browsers.local;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.sogeti.autotest.utils.BrowserFactory;

import io.github.bonigarcia.wdm.WebDriverManager;


public class LocalFirefoxDriver extends AbstractLocalBrowserDriver
{
	@Override
	public WebDriver createDriver()
	{
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		Proxy seleniumProxy = BrowserFactory.getSeleniumProxy();
		if (seleniumProxy != null)
		{
			firefoxOptions.setCapability(CapabilityType.PROXY, seleniumProxy);
		}

		FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().fullscreen();
		return driver;
	}
}
