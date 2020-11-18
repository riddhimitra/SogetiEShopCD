package com.sogeti.autotest.utils.browsers.local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public abstract class AbstractLocalBrowserDriver
{
	public abstract WebDriver createDriver();

	public void addCapabilities(DesiredCapabilities capabilities)
	{

	}
}
