package com.sogeti.autotest.page.containers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageContainer {

	// @FindBy(how = How.CSS, using = ".image-banner")
	// public WebElement imageBanner;

	@FindBy(how = How.XPATH, using = "/html/body/div/div/div[2]/div[1]/form/input[1]")
	public WebElement addToBasKetButtonWhitemug;

	@FindBy(how = How.XPATH, using = "/html/body/div/div/div[2]/div[2]/form/input[1]")
	public WebElement addToBasKetButtonWorldQualityReport;

	@FindBy(how = How.XPATH, using = "/html/body/div/header/div/article/section[1]/a/img")
	public WebElement eshopLogo;

}
