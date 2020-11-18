package com.sogeti.autotest.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sogeti.autotest.helper.Helper;

import io.cucumber.java.en.Then;



public class OrderConfirmSteps extends Helper {
	private static final Logger logger = LoggerFactory.getLogger(OrderConfirmSteps.class.getName());
	public String checkorderconfirmation() {
		String customerEmailText = orderConfirmationContainer.orderconfirmPageDiv.getText();
		logger.info("Email Text is :" + customerEmailText);

		return customerEmailText;
	}

	@Then("^It should display Order Confirmation page$")
	public void display_order_confirmation() {
		String orderdetails = checkorderconfirmation();
	}

}
