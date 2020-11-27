package com.sogeti.autotest.steps;

import java.io.IOException;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.Navigation;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;



public class BaseSteps extends Helper{
	public static final Utils utils = new Utils();
	public static BrowserDriver browserdriver = new BrowserDriver();

	public void openShoppingCart(){
		ActionHandler.scrollElementIntoView(header.shoppingBagLink);
		ActionHandler.click(header.shoppingBagLink);
	}
	
	@Given("^I navigate to '(UK|US)' (.+)$")
	public void navigateUserDefaultBrowser(String environment, String page) throws InterruptedException, IOException
	{
		setDefaultBrowserType();
		Navigation.given_user_navigate_to(environment, page, BrowserDriver.BROWSER_TYPE);
	}
	
	


	 

}
