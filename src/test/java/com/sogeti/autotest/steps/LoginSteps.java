<<<<<<< HEAD
package com.sogeti.autotest.steps;

import java.io.IOException;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.Navigation;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.VerifyHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.Utils;

import cucumber.api.java.en.Given;
import gherkin.ast.DataTable;
import java.util.List;

import io.cucumber.java.en.And;



public class LoginSteps extends Helper{
	public static final Utils utils = new Utils();
	public static BrowserDriver browserdriver = new BrowserDriver();


	
//	@And("^I try to login to eshop from checkout page with following credentials$")
//	public void i_try_to_login_to_eshop_from_checkout_page_with_following_credentials(DataTable uc) 
//
//	{
//	  
//	     VerifyHandler.verifyElementPresent(loginContainer.loginText);
//	     List<String> data = uc.asList(String.class);
//		 loginContainer.inputUsername.sendKeys(data.get(0).get(0));
//		 loginContainer.inputPassword.sendKeys(data.get(0).get(1));
//		 BrowserDriver.wait(50);
//		 ActionHandler.click(loginContainer.loginButton);
//	}
	

	@And("I try to login to eshop from checkout page with {string} and {string}")
	public void i_try_to_login_to_eshop_from_checkout_page_with (String username, String password)  

	{
	     VerifyHandler.verifyElementPresent(loginContainer.loginText);
	     VerifyHandler.verifyElementPresent(loginContainer.inputUsername);
	     ActionHandler.setText(loginContainer.inputUsername, username, true);
	     ActionHandler.setText(loginContainer.inputPassword, password, true);
		 ActionHandler.click(loginContainer.loginButton);
	}

}

=======
package com.sogeti.autotest.steps;

import java.io.IOException;

import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.Navigation;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.VerifyHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.Utils;

import cucumber.api.java.en.Given;
import gherkin.ast.DataTable;
import java.util.List;

import io.cucumber.java.en.And;



public class LoginSteps extends Helper{
	public static final Utils utils = new Utils();
	public static BrowserDriver browserdriver = new BrowserDriver();


	
//	@And("^I try to login to eshop from checkout page with following credentials$")
//	public void i_try_to_login_to_eshop_from_checkout_page_with_following_credentials(DataTable uc) 
//
//	{
//	  
//	     VerifyHandler.verifyElementPresent(loginContainer.loginText);
//	     List<String> data = uc.asList(String.class);
//		 loginContainer.inputUsername.sendKeys(data.get(0).get(0));
//		 loginContainer.inputPassword.sendKeys(data.get(0).get(1));
//		 BrowserDriver.wait(50);
//		 ActionHandler.click(loginContainer.loginButton);
//	}
	

	@And("I try to login to eshop from checkout page with {string} and {string}")
	public void i_try_to_login_to_eshop_from_checkout_page_with (String username, String password)  

	{
	     VerifyHandler.verifyElementPresent(loginContainer.loginText);
	     VerifyHandler.verifyElementPresent(loginContainer.inputUsername);
	     ActionHandler.setText(loginContainer.inputUsername, username, true);
	     ActionHandler.setText(loginContainer.inputPassword, password, true);
		 ActionHandler.click(loginContainer.loginButton);
	}

}

>>>>>>> ab4263a3c2f29ffcc2977e1ab30ac397532ecfbe
