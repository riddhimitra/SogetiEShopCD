package com.sogeti.autotest.helper;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.page.containers.CartContainer;
import com.sogeti.autotest.page.containers.Footer;
import com.sogeti.autotest.page.containers.Header;
import com.sogeti.autotest.page.containers.HomePageContainer;
import com.sogeti.autotest.page.containers.LoginPageContainer;
import com.sogeti.autotest.page.containers.MyAccountContainer;
import com.sogeti.autotest.page.containers.OrderConfirmationContainer;
import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;
import com.sogeti.autotest.utils.Navigation;


public class Helper
{
	public static WebDriver driverW;
	public static HomePageContainer homeContainer = new HomePageContainer();
	public static LoginPageContainer loginContainer = new LoginPageContainer();
	public static MyAccountContainer myAccountContainer = new MyAccountContainer();
	//public static EshopHomePage eshopHomePage = new EshopHomePage();
	public static CartContainer cartContainer = new CartContainer();
	public static OrderConfirmationContainer orderConfirmationContainer = new OrderConfirmationContainer();
	public static Header header = new Header();
	public static Footer footer = new Footer();
	ArrayList<String> skuList = new ArrayList<>();
	public static JavascriptExecutor js;


	
	public Helper()
	{
		initPageFactories();
	}

	public static void initPageFactories()
	{
		loginContainer = PageFactory
				.initElements(driverW, LoginPageContainer.class);
		homeContainer = PageFactory
				.initElements(driverW, HomePageContainer.class);
		myAccountContainer = PageFactory
				.initElements(driverW, MyAccountContainer.class);
		cartContainer = PageFactory
				.initElements(driverW, CartContainer.class);
		orderConfirmationContainer = PageFactory
				.initElements(driverW, OrderConfirmationContainer.class);
		header = PageFactory
				.initElements(driverW, Header.class);
		footer = PageFactory
				.initElements(driverW, Footer.class);
		//eshopHomePage = PageFactory
		//		.initElements(driverW, EshopHomePage.class);
				
		

	}

	
	public static void initPageFactories(BROWSER_TYPES browserType)
	{
		driverW = BrowserDriver.getCurrentDriver(browserType);
		js = ActionHandler.setJavaScriptExecutor(browserType);
		initPageFactories();
	}

	
	public void setDefaultBrowserType()
	{
		BrowserDriver.BROWSER_TYPE = BROWSER_TYPES.ONE;
	}


	public void goToHomePage(String region) throws InterruptedException, IOException
	{
		setDefaultBrowserType();
		Navigation.given_I_navigate_to(region, "home page");
	}
	
	  public static String getCurrentUrl() 
	  {
	
		return driverW.getCurrentUrl();
		
		}

}
