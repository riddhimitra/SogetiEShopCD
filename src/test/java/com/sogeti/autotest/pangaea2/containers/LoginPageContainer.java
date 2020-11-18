package com.sogeti.autotest.pangaea2.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageContainer {
	@FindBy(how = How.CSS, using = ".esh-identity-name")
	public WebElement loginPageLink;

	@FindBy(how = How.CSS, using = "input#Input_Email")
	public WebElement usernameInput;

	@FindBy(how = How.CSS, using = "input#Input_Password")
	public WebElement passwordInput;
	
	@FindBy(how = How.CSS, using = ".account-login-container button")
	public WebElement signinbutton;

	@FindBy(how = How.CSS, using = ".validation-summary-errors ul")
	public WebElement loginError;
	
	@FindBy(how = How.CSS, using = "#logoutForm a[href*='logoutForm']")
	public WebElement logoutPageLink;

	@FindBy(how = How.CSS, using = "input#Input_RememberMe")
	public WebElement rememberMeCheckBox;

	@FindBy(how = How.CSS, using = ".account-login-container")
	public WebElement loginPageHeader;
	
	
	
	
	

}