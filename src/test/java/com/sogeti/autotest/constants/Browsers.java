package com.sogeti.autotest.constants;

public enum Browsers {

	FIREFOX,
	CHROME,
	SAFARI,
	REMOTE,
	HEADLESSCHROME,
	EDGE,
	IE;

	public static Browsers browserForName(String browser) {
        for(Browsers b: values()){
    		if(b.toString().equalsIgnoreCase(browser)){
    			return b;
    		}
        }
        throw browserNotFound(browser);
    }

    private static IllegalArgumentException browserNotFound(String outcome) {
        return new IllegalArgumentException(("Invalid browser [" + outcome + "]"));
    }
}
