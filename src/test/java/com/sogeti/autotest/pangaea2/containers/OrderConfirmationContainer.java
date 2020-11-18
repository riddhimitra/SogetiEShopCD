package com.sogeti.autotest.pangaea2.containers;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OrderConfirmationContainer {
	@FindBy(how = How.ID, using = "orderConfirmation")
	public WebElement orderconfirmPageDiv;


}

