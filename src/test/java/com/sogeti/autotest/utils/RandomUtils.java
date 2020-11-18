package com.sogeti.autotest.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class RandomUtils
{

	public static String[] randomDateOfBirth()
	{

		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1900, 2010);

		gc.set(Calendar.YEAR, year);

		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

		String[] birthDate =
		{ Integer.toString(gc.get(Calendar.DAY_OF_MONTH)), Integer.toString(gc.get(Calendar.MONTH)),
				Integer.toString(gc.get(Calendar.YEAR)) };

		return birthDate;
	}


	public static int randBetween(int start, int end)
	{
		return ThreadLocalRandom.current().nextInt(start, end + 1);
	}

	public static String generateRandomNumber(int charLength)
	{
		return String.valueOf(charLength < 1 ? 0
				: new Random()
						.nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
						+ (int) Math.pow(10, charLength - 1));
	}

}
