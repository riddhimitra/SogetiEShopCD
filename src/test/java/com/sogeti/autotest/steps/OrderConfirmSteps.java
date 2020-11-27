package com.sogeti.autotest.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.VerifyHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.BrowserDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



public class OrderConfirmSteps extends Helper {
	private static final Logger logger = LoggerFactory.getLogger(OrderConfirmSteps.class.getName());
	public String checkorderconfirmation() {
		String customerEmailText = orderConfirmationContainer.orderconfirmPageDiv.getText();
		logger.info("Email Text is :" + customerEmailText);

		return customerEmailText;
	}
	
	@And("I click the checkout button")
	public void i_click_the_checkout_button() {
		 ActionHandler.scrollElementIntoView(homeContainer.checkOutButton);
		 BrowserDriver.wait(500);
		 ActionHandler.click(homeContainer.checkOutButton);
	}
	
	 @When("^I click the paynow button$") 
	 public void i_click_the_paynow_button() 
	  { 
		  ActionHandler.scrollElementIntoView(orderConfirmationContainer.payNowButton);
		  BrowserDriver.wait(500);
		  ActionHandler.click(orderConfirmationContainer.payNowButton);
	  }

	 @Then("^I can see the thank you message$") 
	 public void i_can_see_the_thank_you_message() 
	  { 
		 VerifyHandler.verifyElementPresent(orderConfirmationContainer.thankYouText);
		 VerifyHandler.verifyTitleContainsText("Thanks for your Order");
	  }
	 
	@Then("^It should display Order Confirmation page$")
	public void display_order_confirmation() {
		@SuppressWarnings("unused")
		String orderdetails = checkorderconfirmation();
	}

}
