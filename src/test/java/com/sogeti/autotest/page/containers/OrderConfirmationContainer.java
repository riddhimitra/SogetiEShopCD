package com.sogeti.autotest.page.containers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OrderConfirmationContainer {
	@FindBy(how = How.ID, using = "orderConfirmation")
	public WebElement orderconfirmPageDiv;

	@FindBy(how = How.CSS, using = "section.col-xs-4 input.btn")
	public WebElement payNowButton;

	@FindBy(how = How.CSS, using = "div.container h1")
	public WebElement thankYouText;

}
