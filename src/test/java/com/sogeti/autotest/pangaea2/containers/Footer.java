package com.sogeti.autotest.pangaea2.containers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Footer {
	@FindBy(how = How.XPATH, using = "//div[@class='pull-left js-legal-links']/a[@href='/termsAndConditions']")
	public WebElement termsAndConditionsFooterLink;

	@FindBy(how = How.XPATH, using = "//div[@class='pull-left js-legal-links']/a[@href='/privacyPolicy']")
	public WebElement privacyPolicyFooterLink;

	@FindBy(how = How.XPATH, using = "//li[@class='yCmsComponent width100']/a[@href='/contact-us']")
	public WebElement contactUsFooterLink;

	@FindBy(how = How.LINK_TEXT, using = "BOOK APPOINTMENT")
	public WebElement bookAppointmentFooterLink;

	@FindBy(how = How.LINK_TEXT, using = "France")
	public WebElement countryFooterFrance;

	@FindBy(how = How.LINK_TEXT, using = "Germany")
	public WebElement countryFooterGermany;

	@FindBy(how = How.LINK_TEXT, using = "Netherland")
	public WebElement countryFooterNetherland;

	@FindBy(how = How.LINK_TEXT, using = "Europe")
	public WebElement countryFooterEurope;

	@FindBy(how = How.LINK_TEXT, using = "Spain")
	public WebElement countryFooterSpain;

	@FindBy(how = How.ID, using = "subscribeToEmail")
	public WebElement subscribeInput;

	@FindBy(how = How.CSS, using = ".subscribe-email")
	public WebElement subscribeOptionsExpand;

	@FindBy(how = How.CSS, using = ".select-all-preferences")
	public WebElement subscribeAllButton;

	@FindBy(how = How.CSS, using = "button.btn-join-sogeti")
	public WebElement subscribeButton;


	@FindBy(how = How.CSS, using = "div.join-button p.text-success")
	public WebElement subscribeSuccessMessage;







}
