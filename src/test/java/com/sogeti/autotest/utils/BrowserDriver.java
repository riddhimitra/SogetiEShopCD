package com.sogeti.autotest.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.json.JsonException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;


public class BrowserDriver
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BrowserDriver.class.getName());
	private static WebDriver mDriver;
	private static WebDriver aDriver;
	private static WebDriver sDriver;
	public static int WAIT_IN_SEC = 5;
	private static WebDriverWait wait = null;
	public static BROWSER_TYPES BROWSER_TYPE;

	public static BROWSER_TYPES getBROWSER_TYPE()
	{
		return BROWSER_TYPE;
	}

	public static void setBROWSER_TYPE(BROWSER_TYPES browserType)
	{
		BROWSER_TYPE = browserType;
	}

	private static Boolean closeOnComplete = true;

	public static Boolean getCloseOnComplete()
	{
		return closeOnComplete;
	}

	public static void setCloseOnComplete(final Boolean closeOnComplete)
	{
		BrowserDriver.closeOnComplete = closeOnComplete;
	}

	public static WebDriverWait getWait(final BROWSER_TYPES browserType)
	{
		if (browserType == null)
		{
			BROWSER_TYPE = BROWSER_TYPES.ONE;
		}
		if (wait == null || WAIT_IN_SEC == 5)
		{
			wait = new WebDriverWait(getCurrentDriver(browserType), WAIT_IN_SEC);
		}
		return wait;
	}

	public static void setWait(final int waitTimeInSec)
	{
		WAIT_IN_SEC = waitTimeInSec;
		wait = new WebDriverWait(getCurrentDriver(BROWSER_TYPE), WAIT_IN_SEC);

	}

	public static LogEntries getBrowserLogs()
	{
		final WebDriver driver = getCurrentDriver();
		LogEntries logs = null;
		if (null != driver)
		{
			try
			{
				logs = driver.manage().logs().get("browser");
			}
			catch (JsonException j)
			{
				LOGGER.error("unable to parse remote console logs: " + j);
			}
		}
		return logs;
	}

	public static void resetWait()
	{
		WAIT_IN_SEC = Integer.parseInt(Config.getCommonMandatoryPropValue("config.globalTimeoutInSeconds"));
	}

	public static WebDriver setBrowser(WebDriver driver)
	{
		try
		{
			driver = BrowserFactory.getBrowser();
		}
		catch (final UnreachableBrowserException ee)
		{
			driver = BrowserFactory.getBrowser();
		}
		catch (final WebDriverException ee)
		{
			driver = BrowserFactory.getBrowser();
		}
		finally
		{
			Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
		}
		return driver;
	}

	public static synchronized WebDriver getCurrentDriver()
	{

		final int GLOBAL_WAIT_IN_SEC = Integer.parseInt(Config.getCommonMandatoryPropValue("config.globalTimeoutInSeconds"));
		switch (BROWSER_TYPE)
		{
			case ONE:
				if (mDriver == null)
				{
					mDriver = setBrowser(mDriver);
				}
				wait = new WebDriverWait(mDriver, GLOBAL_WAIT_IN_SEC);
				return mDriver;
			case TWO:
				if (sDriver == null)
				{
					sDriver = setBrowser(sDriver);
				}
				wait = new WebDriverWait(sDriver, GLOBAL_WAIT_IN_SEC);
				return sDriver;
			case ADMIN:
				if (aDriver == null)
				{
					aDriver = setBrowser(aDriver);
				}
				wait = new WebDriverWait(aDriver, GLOBAL_WAIT_IN_SEC);
				return aDriver;

		}
		return null;

	}



	public static synchronized WebDriver getCurrentDriver(final BROWSER_TYPES driverType)
	{
		WebDriver driver = null;
		switch (driverType)
		{
			case ADMIN:
				BROWSER_TYPE = BROWSER_TYPES.ADMIN;
				break;
			case ONE:
				BROWSER_TYPE = BROWSER_TYPES.ONE;
				break;
			case TWO:
				BROWSER_TYPE = BROWSER_TYPES.TWO;
				break;
		}
		driver = getCurrentDriver();

		wait = new WebDriverWait(driver, WAIT_IN_SEC);
		return driver;
	}

	public static void quit()
	{

		for (final BROWSER_TYPES browser_types : BROWSER_TYPES.values())
		{
			quit(browser_types);
		}
	}

	/**
	 * This method Disposes of the specified WebDriver driver. This also disposes of the Browser associated with the driver.
	 * <p>
	 * This is different to a Selenium WebDriver driver.close() which will just close the Browser but leave the driver running
	 * and cause Java session errors on program / test end.
	 * <p>
	 * <p>
	 * A good reference is at:
	 * https://stackoverflow.com/questions/15067107/difference-between-webdriver-dispose-close-and-quit
	 * <p>
	 * @param driver BROWSER_TYPES value of the WebDriver driver to Dispose of.
	 * @return void.
	 */
	public static void quit(final BROWSER_TYPES driver)
	{
		try
		{
			switch (driver)
			{
				case ONE:
					if (mDriver != null)
					{
						LOGGER.info("quitting browser ONE");
						mDriver.quit();
					}
					mDriver = null;
					break;
				case TWO:
					if (sDriver != null)
					{
						LOGGER.info("quitting browser TWO");
						sDriver.quit();
					}
					sDriver = null;
					break;
				case ADMIN:
					if (aDriver != null)
					{
						LOGGER.info("quitting browser ADMIN");
						aDriver.quit();
					}
					aDriver = null;
					break;
			}
		}
		catch (final UnreachableBrowserException e)
		{
			LOGGER.info("cannot quit browser: unreachable browser:" + e);
		}
	}

	public static void clearCookies()
	{
		if (getCurrentDriver(BROWSER_TYPE).manage().getCookieNamed("JSESSIONID") != null)
		{
			getCurrentDriver(BROWSER_TYPE).manage().deleteCookieNamed("JSESSIONID");
		}
		getCurrentDriver(BROWSER_TYPE).manage().deleteAllCookies();
	}


	private static class BrowserCleanup implements Runnable
	{
		@Override
		public void run()
		{

			if (getCloseOnComplete())
			{
				if (mDriver != null)
				{
					quit(BROWSER_TYPES.ONE);

				}
				if (sDriver != null)
				{
					quit(BROWSER_TYPES.TWO);

				}
				if (aDriver != null)
				{
					quit(BROWSER_TYPES.ADMIN);

				}
			}

			if (BrowserFactory.getProxy() != null)
			{
				BrowserFactory.getProxy().stop();
			}
		}
	}


	public static void loadPage(final String url)
	{
		loadPage(url, BROWSER_TYPES.ONE);
	}

	public static void loadPage(final String url, final BROWSER_TYPES driverType)
	{
		BROWSER_TYPE = driverType;
		getCurrentDriver(driverType).get(url);
		LOGGER.info("Directing browser to:" + url);

	}

	public static void loadrelativePage(final String path, final BROWSER_TYPES driverType)
	{

		BROWSER_TYPE = driverType;
		URL url = null;
		try
		{
			url = new URL(getCurrentPage(driverType));
		}
		catch (final MalformedURLException e)
		{
			Assert.fail("cannot get current page url");
		}

		int port;
		port = url.getPort();
		String fullUrl = null;

		if (port < 0)
		{
			fullUrl = url.getProtocol() + "://" + url.getHost() + "/" + path;
		}
		else
		{
			fullUrl = url.getProtocol() + "://" + url.getHost() + ":" + port + "/" + path;
		}

		LOGGER.info("Directing browser to:" + fullUrl);
		getCurrentDriver(driverType).get(fullUrl);
	}

	public static void refreshPage()
	{
		getCurrentDriver(BROWSER_TYPE).navigate().refresh();
	}

	public static void wait(final int milliseconds)
	{
		try
		{
			LOGGER.info("sleeping for  [" + milliseconds + "] milliseconds");
			Thread.sleep(milliseconds);
		}
		catch (final InterruptedException e)
		{
			LOGGER.error(e.toString());
		}

	}

	public static String getCurrentPage()
	{
		return getCurrentPage(BROWSER_TYPE);
	}

	public static String getCurrentPage(final BROWSER_TYPES driverType)
	{
		return getCurrentDriver(driverType).getCurrentUrl();
	}

	public static WebElement waitForElementToBeClickable(final WebElement elementToWaitFor, final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		 
		WebElement status = null;
		try
		{
			status = getWait(driverType).until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
		}
		catch (StaleElementReferenceException e)
		{
			LOGGER.info("StaleElement Exception thrown; get elment again");
			By locator = Utils.rebuildElement(elementToWaitFor);
			BrowserDriver.getCurrentDriver().findElement(locator);
			
			status = getWait(driverType).until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
		}
							
		return status;

	}

	// VISIBILITY WAITS
	public static WebElement waitForElement(final By locator)
	{
		return waitForElementToBeVisible(locator, BROWSER_TYPE);
	}

	public static WebElement waitForElement(final By locator, final Integer waitTimeInMilliSeconds)
	{
		return waitForElementToBeVisible(locator, BROWSER_TYPE, waitTimeInMilliSeconds);
	}


	public static WebElement waitForElementToBeVisible(final By locator)
	{

		return waitForElementToBeVisible(locator, BROWSER_TYPE);

	}

	public static WebElement waitForElementToBeVisible(final By locator, final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		return getWait(driverType).until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	public static WebElement waitForElementToBeVisible(final By locator, final BROWSER_TYPES driverType,
			final Integer waitTimeInSeconds)
	{

		if (waitTimeInSeconds != null)
		{
			setWait(waitTimeInSeconds);

		}
		getCurrentDriver(driverType);
		return getWait(driverType).until(ExpectedConditions.visibilityOfElementLocated(locator));

	}


	public static Boolean waitForElementToBeInvisible(final WebElement element)
	{
		return waitForElementToBeInvisible(element, BROWSER_TYPE);
	}

	//This also covers ElementNotPresent
	public static Boolean waitForElementToBeInvisible(final WebElement element, final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		
		Boolean status = null;
		try
		{
			status = getWait(driverType).until(ExpectedConditions.invisibilityOf(element));
		}
		catch (StaleElementReferenceException e)
		{
			LOGGER.info("StaleElement Exception thrown; get elment again");
			By locator = Utils.rebuildElement(element);
			BrowserDriver.getCurrentDriver().findElement(locator);
			
			status = getWait(driverType).until(ExpectedConditions.invisibilityOf(element));
		}
		
		
		return status;
	}

	//This also covers ElementNotPresent
	public static void waitForElementToBeInvisible(final String locatorString, final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		getWait(driverType).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(locatorString), 0));
	}

	// legacy
	public static WebElement waitForElement(final WebElement elementToWaitFor)
	{
		return waitForElementToBeVisible(elementToWaitFor, BrowserDriver.WAIT_IN_SEC, BROWSER_TYPE);
	}

	public static WebElement waitForElement(final WebElement elementToWaitFor, final Integer waitTimeInSeconds)
	{
		return waitForElementToBeVisible(elementToWaitFor, waitTimeInSeconds, BROWSER_TYPE);
	}

	public static WebElement waitForElementWithScroll(final WebElement elementToWaitFor, final Integer waitTimeInMilliSeconds)
	{
		WebElement elementToRet = null;
		try
		{
			elementToRet = waitForElementToBeVisible(elementToWaitFor, waitTimeInMilliSeconds, BROWSER_TYPE);
		}
		catch (final Exception ee)
		{
			ActionHandler.scrollElementIntoView(elementToWaitFor);
			elementToRet = waitForElementToBeVisible(elementToWaitFor, waitTimeInMilliSeconds, BROWSER_TYPE);

		}
		return elementToRet;
	}

	public static WebElement waitForElement(final WebElement elementToWaitFor, final Integer waitTimeInSeconds,
			final BROWSER_TYPES browser_types)
	{
		return waitForElementToBeVisible(elementToWaitFor, waitTimeInSeconds, browser_types);
	}

	public static WebElement waitForElementToBeVisible(final WebElement elementToWaitFor, final BROWSER_TYPES driverType)
	{
		return waitForElementToBeVisible(elementToWaitFor, WAIT_IN_SEC, driverType);
	}

	public static WebElement waitForElementToBeVisible(final WebElement elementToWaitFor)
	{
		return waitForElementToBeVisible(elementToWaitFor, BrowserDriver.WAIT_IN_SEC, BROWSER_TYPE);
	}

	public static WebElement waitForElementToBeVisible(final WebElement elementToWaitFor, final Integer waitTimeInSeconds,
			final BROWSER_TYPES driverType)
	{
		if (waitTimeInSeconds != null)
		{
			setWait(waitTimeInSeconds);

		}
		
		WebElement status = null;
		try
		{
			status = getWait(driverType).until(ExpectedConditions.visibilityOf(elementToWaitFor));
		}
		catch (StaleElementReferenceException e)
		{
			LOGGER.info("StaleElement Exception thrown; get elment again");
			By locator = Utils.rebuildElement(elementToWaitFor);
			BrowserDriver.getCurrentDriver().findElement(locator);
			
			status = getWait(driverType).until(ExpectedConditions.visibilityOf(elementToWaitFor));
		}
		
		return status;
	}

	public static Boolean waitForElementToContainAttribute(final WebElement elementToWaitFor, final String attribute,
			final String attributeValue)
	{

		return waitForElementToContainAttribute(elementToWaitFor, attribute, attributeValue, WAIT_IN_SEC, BROWSER_TYPE);
	}

	public static Boolean waitForElementToContainAttribute(final WebElement elementToWaitFor, final String attribute,
			final String attributeValue, final Integer waitTimeInSeconds, final BROWSER_TYPES driverType)
	{
		if (waitTimeInSeconds != null)
		{
			setWait(waitTimeInSeconds);
		}
		return getWait(driverType).until(ExpectedConditions.attributeContains(elementToWaitFor, attribute, attributeValue));
	}

	public static Boolean waitForElementToContainText(final WebElement elementToWaitFor, final String textToContain,
			final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		return getWait(driverType).until(ExpectedConditions.textToBePresentInElement(elementToWaitFor, textToContain));
	}

	public static Boolean waitForElementToContainText(final WebElement elementToWaitFor, final String textToContain)
	{
		return waitForElementToContainText(elementToWaitFor, textToContain, BROWSER_TYPE);
	}

	public static void waitForElementToNotContainText(final WebElement elementToWaitFor, final String textNotToContain,
			final BROWSER_TYPES driverType)
	{
		getCurrentDriver(driverType);
		int counter = 2;
		
		try
		{
			while (elementToWaitFor.getText().contains(textNotToContain) && counter > 0)
			{
				BrowserDriver.wait(WAIT_IN_SEC);
				counter--;
			}
		}
		catch (StaleElementReferenceException e)
		{
			LOGGER.info("StaleElement Exception thrown; get elment again");
			By locator = Utils.rebuildElement(elementToWaitFor);
			BrowserDriver.getCurrentDriver().findElement(locator);
			
			while (elementToWaitFor.getText().contains(textNotToContain) && counter > 0)
			{
				BrowserDriver.wait(WAIT_IN_SEC);
				counter--;
			}
		}
		
		
		
	}

	public static void waitForElementToNotContainText(final WebElement elementToWaitFor, final String textToContain)
	{
		waitForElementToNotContainText(elementToWaitFor, textToContain, BROWSER_TYPE);
	}

	public static WebElement switchToActiveElement()
	{
		return switchToActiveElement(BROWSER_TYPE);
	}

	public static WebElement switchToActiveElement(final BROWSER_TYPES driverType)
	{
		return getCurrentDriver(driverType).switchTo().activeElement();
	}

	public static void closeAllTabsExceptCurrent(final String windowHandle)
	{

		LOGGER.info(BrowserDriver.getCurrentDriver().getWindowHandles().toString());
		for (final String handle : BrowserDriver.getCurrentDriver().getWindowHandles())
		{
			if (!handle.equals(windowHandle))
			{
				BrowserDriver.getCurrentDriver().switchTo().window(handle);
				LOGGER.info("Closing Window with Title:" + BrowserDriver.getCurrentDriver().getTitle());
				BrowserDriver.getCurrentDriver().close();
			}
		}
		BrowserDriver.getCurrentDriver().switchTo().window(windowHandle);
	}

	public static void createNewTab()
	{
		((JavascriptExecutor) BrowserDriver.getCurrentDriver()).executeScript("window.open()");
		ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
	}

	public static void switchtoNewTab(final String windowHandle)
	{

		for (final String handle : BrowserDriver.getCurrentDriver().getWindowHandles())
		{
			if (!handle.equals(windowHandle))
			{
				BrowserDriver.getCurrentDriver().switchTo().window(handle);
			}
		}
	}

	public static boolean waitForNewWindow(BROWSER_TYPES driverType, int timeout)
	{

		boolean flag = false;
		int counter = 0;
		while (!flag)
		{
			try
			{
				Set<String> winId = getCurrentDriver(driverType).getWindowHandles();
				if (winId.size() > 1)
				{
					flag = true;
					return flag;
				}
				Thread.sleep(1000);
				counter++;
				if (counter > timeout)
				{
					return flag;
				}
			}
			catch (Exception e)
			{
				LOGGER.error(e.getMessage());
				return false;
			}
		}
		return flag;
	}




}
