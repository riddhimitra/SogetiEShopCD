package com.sogeti.autotest.pangaea2.containers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePageContainer {
	
	@FindBy(how = How.CSS, using = ".image-banner")
	public WebElement imageBanner;

}
