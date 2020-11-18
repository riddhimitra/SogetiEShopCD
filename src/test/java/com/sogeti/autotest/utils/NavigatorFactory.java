package com.sogeti.autotest.utils;

import com.sogeti.autotest.utils.BrowserFactory.BROWSER_TYPES;
import com.sogeti.autotest.utils.Navigation;

public class NavigatorFactory {
	private static Navigation navigator = null;
	private static Navigation secondNavigator = null;
	private static Navigation adminNavigator = null;
	
	
	public static synchronized Navigation getNavigator(BROWSER_TYPES browser){
		switch (browser) {
		case TWO:
		    return getSecondNavigator();
		case ADMIN:
		    return getAdminNavigator();
		default:
		case ONE:
		    return getNavigator();
		}
	   
	    
	}
	
	public static synchronized void clearNavigators(){
		if(navigator != null){
		    navigator = null;
		}
		if(adminNavigator != null){
		    adminNavigator = null;
		}
		if(secondNavigator != null){
		    secondNavigator = null;
		}
		
	}
	
	public static synchronized Navigation getNavigator(){
		if(navigator == null){
			navigator = new Navigation();
		}
		return navigator;
	}
	
	public static synchronized Navigation getSecondNavigator(){
		if(secondNavigator == null){
			secondNavigator = new Navigation();
		}
		return secondNavigator;
	}
	
	public static synchronized Navigation getAdminNavigator(){
		if(adminNavigator == null){
		    adminNavigator = new Navigation();
		}
		return adminNavigator;
	}
	

	

}
