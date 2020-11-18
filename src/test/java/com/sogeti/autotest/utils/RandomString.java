package com.sogeti.autotest.utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;


public class RandomString
{

	/**
	 * Generate a random string.
	 * From https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	 */


	public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

	public static final String DIGITS = "0123456789";

	public static final String ALPHANUM = UPPER + LOWER + DIGITS;

	private final Random random;

	private final char[] symbols;

	private final char[] buf;

	public RandomString(int length, Random random, String symbols)
	{
		if (length < 1)
			throw new IllegalArgumentException();
		if (symbols.length() < 2)
			throw new IllegalArgumentException();
		this.random = Objects.requireNonNull(random);
		this.symbols = symbols.toCharArray();
		this.buf = new char[length];
	}

	/**
	 * Create an alphanumeric string generator.
	 */
	public RandomString(int length, Random random)
	{
		this(length, random, ALPHANUM);
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 */
	public RandomString(int length)
	{
		this(length, new SecureRandom());
	}

	/**
	 * Create session identifiers.
	 */
	public RandomString()
	{
		this(21);
	}

	public String nextString()
	{
		for (int idx = 0; idx < buf.length; ++idx)
		{
			buf[idx] = symbols[random.nextInt(symbols.length)];
		}
		return new String(buf);
	}
	
	/**
	 * Return a string of random Alphanumeric chars of length length 
	 */
	public String getrandomString(final int length) {
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < length; i++) {
	   	 sb.append( ALPHANUM.charAt( random.nextInt(ALPHANUM.length()) ) );
	    }
	    return sb.toString();
	}

}
