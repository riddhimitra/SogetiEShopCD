package com.sogeti.autotest.steps;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.AssertHandler;
import com.sogeti.autotest.handlers.VerifyHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.Utils;

import io.cucumber.java.en.When;
import io.cucumber.java.en.And;



public class LogoutSteps extends Helper{
	public static final Utils utils = new Utils();
	public static BrowserDriver browserdriver = new BrowserDriver();

	

	@When("I click logout link from dropdown")
	public void i_click_logout_link_from_dropdown() {
		 ActionHandler.scrollElementIntoView(loginContainer.accountArea);
		 VerifyHandler.verifyElementPresent(loginContainer.accountArea);
		 ActionHandler.scrollElementIntoView(loginContainer.logoutDropdownLink);
		 ActionHandler.click(loginContainer.logoutDropdownLink);
	}

	@And("I successfully log out of Eshop")
	public void i_successfully_log_out_of_Eshop() {
		
		AssertHandler.assertTextContainsOnElement(loginContainer.loginPageLink, "login");
		 
	}
}

