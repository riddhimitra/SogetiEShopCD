package com.sogeti.autotest.page.containers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageContainer {
	@FindBy(how = How.CSS, using = ".esh-identity-name")
	public WebElement loginPageLink;

//	@FindBy(how = How.CSS, using = "input#Input_Email")
//	public WebElement usernameInput;
//
//	@FindBy(how = How.CSS, using = "input#Input_Password")
//	public WebElement passwordInput;
//	
//	@FindBy(how = How.CSS, using = ".account-login-container button")
//	public WebElement signinbutton;
//
//	@FindBy(how = How.CSS, using = ".validation-summary-errors ul")
//	public WebElement loginError;
//	
//	@FindBy(how = How.CSS, using = "#logoutForm a[href*='logoutForm']")
//	public WebElement logoutPageLink;
//
//	@FindBy(how = How.CSS, using = "input#Input_RememberMe")
//	public WebElement rememberMeCheckBox;
//
//	@FindBy(how = How.CSS, using = ".account-login-container")
//	public WebElement loginPageHeader;

	@FindBy(how = How.CSS, using = "div.container.account-login-container h2")
	public WebElement loginText;

	@FindBy(how = How.CSS, using = "#Input_Email")
	public WebElement inputUsername;

	@FindBy(how = How.CSS, using = "#Input_Password")
	public WebElement inputPassword;

	@FindBy(how = How.CSS, using = "div.form-group button.btn.btn-default")
	public WebElement loginButton;

	@FindBy(how = How.CSS, using = "#logoutForm")
	public WebElement accountArea;
	

	@FindBy(how = How.CSS, using = "section.esh-identity-drop img.esh-identity-image")
	public WebElement logoutDropdownLink;

}