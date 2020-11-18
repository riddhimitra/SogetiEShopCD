package com.sogeti.autotest.pangaea2.containers;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Header {
	@FindBy(how = How.CSS, using =  "[data-test='hamburger-btn']")
	public WebElement hamburgerBtn;

	@FindBy(how = How.CSS, using = "[data-test='shopping-cart']")
	public WebElement shoppingBagLink;

	@FindBy(how = How.CSS, using = "[data-test='mobile-shopping-cart'], [data-test='shopping-cart']")
	public WebElement mobileShoppingBagLink;

	@FindBy(how = How.CSS, using = "[data-test='logout-link']")
	public WebElement logoutLink;

}