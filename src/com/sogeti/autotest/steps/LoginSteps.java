package com.sogeti.autotest.steps;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.constants.CredentialsType;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.AssertHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.helper.Users;
import com.sogeti.autotest.model.User;
import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.CacheService;
import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.Navigation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends Helper {

	private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class.getName());

	@When("^I login to '(UK|US)' site with '(VALID|INVALID|UNREGISTERED|EXISTING|CORRECT)' credentials$")
	public void given_I_login_to(String region, CredentialsType credentialsType) throws Exception {
		setDefaultBrowserType();
		Navigation.given_user_navigate_to(region, "home page", BrowserDriver.BROWSER_TYPE);
		when_I_try_to_login(credentialsType);
	}

	@When("^I try to login with '(VALID|INVALID|UNREGISTERED|EXISTING|CORRECT|GIVEN)' credentials$")
	public void when_I_try_to_login(CredentialsType credentialsType) {
		User user = new User();
		logger.info("Entering: I try to login with " + credentialsType + " credentials");
		switch (credentialsType) {
		case VALID:
		case CORRECT:
			if (CacheService.getInstance().getDataMap().get("user") == null) {
				user = Users.createExistingValidUser(1);
			} else {
				user = (User) CacheService.getInstance().getDataMap().get("user");
			}
			break;
		case INVALID:
			user = Users.createExistingInvalidUser();
			break;
		case UNREGISTERED:
			user = Users.createNewValidUniqueUser();
			break;
		case EXISTING:
			user = Users.createExistingValidUser(2);
			break;
		case GIVEN:
			user = Users.createGivenValidUser(CacheService.getInstance().getDataMap().get("orderEmail").toString(),
					CacheService.getInstance().getDataMap().get("password").toString());
		}
		ActionHandler.setText(loginContainer.usernameInput, user.getUsername());
		loginContainer.passwordInput.sendKeys("");
		ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
		ActionHandler.setText(loginContainer.passwordInput, user.getPassword(), true);
		WebElement signInElement = loginContainer.signinbutton;
		ActionHandler.scrollElementIntoView(signInElement);
		ActionHandler.click(signInElement);
		CacheService.getInstance().setDataMap("user", user);

	}

	@Then("^I should see that I logged in '(successfully|unsuccessfully)'$")
	public static void then_I_login(String result) {
		switch (result) {
		case "successfully":
			AssertHandler.assertElementPresent(header.logoutLink);
			break;
		case "unsuccessfully":
			AssertHandler.assertElementPresent(loginContainer.loginError);
			break;
		default:
			Assert.fail("unknown result: " + result);
		}

	}


	@And("^I (?:should |)log out successfully$")
	public void I_logout_successfully() throws IOException {
		logger.info("Logging Out");
		ActionHandler.scrollElementIntoView(loginContainer.logoutPageLink);
		ActionHandler.click(loginContainer.logoutPageLink);
	}


	@When("^I login using '(VALID)' credentials$")
	public void i_login_using_using_VALID_credentials(CredentialsType credentialsType) {
		when_I_try_to_login(credentialsType);
	}

	@When("^I enter '(.+)' user name$")
	public void i_enter_user_name(String type) {
		User user = (User) CacheService.getInstance().getDataMap().get("user");
		if (user == null) {
			switch (type) {
			case "valid":
				user = Users.createExistingValidUser(2);
				break;
			case "invalid":
				user = Users.createExistingInvalidUser();
				break;
			case "blank":
				user = Users.createExistingInvalidUser();
				user = user.withUserName("");
				break;
			default:
				Assert.fail("unknown user type: " + type);
				user = Users.createExistingInvalidUser();
				user = user.withUserName("unknown");
			}
			CacheService.getInstance().setDataMap("user", user);
		}
		ActionHandler.setText(loginContainer.usernameInput, user.getUsername());

	}


	@When("^I select the password field$")
	public void i_select_the_password_field() throws Throwable {
		ActionHandler.scrollElementIntoView(loginContainer.passwordInput);
		ActionHandler.click(loginContainer.passwordInput);
		ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
	}

	@When("^I Select Remember Me$")
	public void i_Select_Remember_Me() throws Throwable {

		WebElement rememberMe = loginContainer.rememberMeCheckBox;
		ActionHandler.click(rememberMe);
	}

	@Then("^I should be on the 'login' Page$")
	public void i_should_be_Login_Page() throws Throwable {

		BrowserDriver.waitForElement(loginContainer.loginPageHeader);
		AssertHandler.assertElementPresent(loginContainer.loginPageHeader);
	}

}