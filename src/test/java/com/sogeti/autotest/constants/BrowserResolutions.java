package com.sogeti.autotest.constants;

import java.util.HashMap;
import java.util.Map;

public enum BrowserResolutions {

	PHONE_PORTRAIT("PHONE_PORTRAIT","480,640",false),
	PHONE_LANDSCAPE("PHONE_LANDSCAPE","640,480",false),
	TABLET_PORTRAIT("TABLET_PORTRAIT","768,1024",false),
	TABLET_LANDSCAPE("TABLET_LANDSCAPE","1024,768",true),
	DESKTOP_SMALL("DESKTOP_SMALL","1280,1024",true),
	DESKTOP_MEDIUM("DESKTOP_MEDIUM","1400,1050",true),
	DESKTOP_LARGE("DESKTOP_LARGE","1600,1200",true),
	DESKTOP_XLARGE("DESKTOP_XLARGE","1920,1080",true),
	DESKTOP_XXLARGE("DESKTOP_XXLARGE","2048,1536",true);

    private final String name;
    private final String dimensions;
    private final Boolean browserSupported;
    private static Map<String, String> mMap;
    private static Map<String, Boolean> mMapSupp;
    
    private BrowserResolutions(String name, String dimensions, Boolean browserSupported) {
        this.name = name;
        this.dimensions = dimensions;
        this.browserSupported = browserSupported;
    }
    
    public String getName() {
        return name;
    }

    public String getDimensions() {
        return dimensions;
    }
    
    public static String getDimensionByName(String name) {
        if (mMap == null) {
            initializeMapping();
        }
        if (mMap.containsKey(name)) {
            return mMap.get(name);
        }
        return null;
    }
    
    public static Boolean getBrowserSupportedByName(String name) {
        if (mMapSupp == null) {
            initializeSupportMapping();
        }
        if (mMapSupp.containsKey(name)) {
            return mMapSupp.get(name);
        }
        return false;
    }

    private static void initializeMapping() {
        mMap = new HashMap<>();
        for (BrowserResolutions s : BrowserResolutions.values()) {
            mMap.put(s.name, s.dimensions);
        }
    }
    
    private static void initializeSupportMapping() {
	mMapSupp = new HashMap<>();
        for (BrowserResolutions s : BrowserResolutions.values()) {
            mMapSupp.put(s.name, s.browserSupported);
        }
    }
}
