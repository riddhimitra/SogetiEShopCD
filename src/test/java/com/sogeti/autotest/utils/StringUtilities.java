package com.sogeti.autotest.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


public class StringUtilities
{

	public static String getFirstLetters(String text)
	{
		String firstLetters = "";
		text = text.replaceAll("[.,]", ""); // Replace dots, etc (optional)
		for (final String s : text.split(" "))
		{
			firstLetters += s.charAt(0);
		}
		return firstLetters;
	}

	public static String replaceSpaceWithUnderscore(final String value)
	{

		if (StringUtils.isNotBlank(value))
		{
			return value.replaceAll("\\s", "_");
		}
		return value;
	}

	public static String createUniqueGuestId()
	{

		return UUID.randomUUID().toString();
	}

}
