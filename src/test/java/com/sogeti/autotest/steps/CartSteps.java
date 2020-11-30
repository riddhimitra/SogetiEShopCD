package com.sogeti.autotest.steps;

import java.io.IOException;

import org.openqa.selenium.Keys;
import com.sogeti.autotest.handlers.ActionHandler;
import com.sogeti.autotest.handlers.AssertHandler;
import com.sogeti.autotest.helper.Helper;
import com.sogeti.autotest.utils.BrowserDriver;
import com.sogeti.autotest.utils.CacheService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartSteps extends Helper
{
	

	 @When("^I add Capgemini whitemug in the cart$") 
	 public void i_add_white_mug_in_the_cart() 
	  { 
		  ActionHandler.click(homeContainer.addToBasKetButtonWhitemug);
		  
	  }
	 	
	 @When("^I add report in the cart$") 
	 public void i_add_report_in_the_cart() 
	  { 
		  ActionHandler.click(homeContainer.addToBasKetButtonWorldQualityReport);
		  
	  }
	
	@Then("^I select qty as two in the cart$")
	public void i_select_qty_as_two_in_the_cart() throws Throwable
	{
		BrowserDriver.waitForElement(cartContainer.basketQty);
		ActionHandler.scrollElementIntoView(cartContainer.basketQty);
		ActionHandler.click(cartContainer.basketQty);
		cartContainer.basketQty.sendKeys(Keys.ARROW_UP);
		cartContainer.basketQty.sendKeys(Keys.RETURN);
		//ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
		BrowserDriver.wait(500);
		ActionHandler.isElementNotPresent(cartContainer.basketQty, BrowserDriver.BROWSER_TYPE);
	}

	@Then("^I am unable to increase qty again$")
	public void i_am_unable_to_increase_again() throws Throwable
	{

		ActionHandler.isElementNotPresent(cartContainer.IncreaseQty, BrowserDriver.BROWSER_TYPE);
	}

	@When("^I update the product quantity$")
	public void i_update_the_product_quantity() throws Throwable
	{
		BrowserDriver.waitForElement(cartContainer.UpdateQty);
		ActionHandler.click(cartContainer.UpdateQty);

	}

	
	@When("^I select to decrease qty$")
	public void i_select_to_decrease_qty() throws Throwable
	{

		BrowserDriver.waitForElement(cartContainer.DecreaseQty);
		ActionHandler.click(cartContainer.DecreaseQty);

	}

	@Then("^Product is removed from the basket$")
	public void product_is_removed_from_the_basket() throws Throwable
	{

		ActionHandler.isElementNotPresent(cartContainer.cartProductDetails, BrowserDriver.BROWSER_TYPE);

	}

	@Then("^I extract all the products in my shopping bag$")
	public void should_be_on_Shopping_Bag_page() throws IOException
	{
		ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
		AssertHandler.assertElementPresent(cartContainer.shoppingBagHeader);
		String cartid = ActionHandler.getText(cartContainer.cartId).trim().split(":")[1];
		CacheService.getInstance().setDataMap("cartId", cartid);
		
	}

	@And("^I start checkout from my bag$")
	public void checkout_from_bag() throws IOException
	{
		ActionHandler.scrollElementIntoView(cartContainer.checkoutNowbutton);
		ActionHandler.click(cartContainer.checkoutNowbutton, BrowserDriver.BROWSER_TYPE);
	}


	@Then("^I verify that I can checkout from my bag$")
	public void verifyCheckoutFromBag()
	{
		ActionHandler.waitForJSandJQueryToLoad(BrowserDriver.BROWSER_TYPE);
		ActionHandler.click(cartContainer.checkoutNowbutton);
	}




}

