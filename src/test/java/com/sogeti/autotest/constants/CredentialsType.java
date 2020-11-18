package com.sogeti.autotest.constants;

public enum CredentialsType
{

	VALID(new String[]{"valid"}),
	INVALID(new String[]{"invalid"}),
	CORRECT((new String[]{"correct"})),
	GIVEN((new String[]{"given"})),
	EXISTING(new String[]{"existing"}),
	ADMIN((new String[]{"admin"})),
	STOREMANAGER((new String[]{"storemanager"})),
	BUYER((new String[]{"buyer"})),
	VIEWONLY((new String[]{"viewonly"})),
	EMPTY(new String[]{"empty"}),
	UNREGISTERED(new String[]{"unregistered"}), 
	EXPIRED(new String[]{"expired"}), 
	INVALIDFORMAT((new String[]{"invalidformat"})); 

	private String[] aliases;

	private CredentialsType(String[] aliases)
	{
		this.aliases = aliases;
	}

	public static CredentialsType credentialsTypeForName(String credentialsType)
	{
		for (CredentialsType ct : values())
		{
			for (String alias : ct.aliases)
			{
				if (alias.equalsIgnoreCase(credentialsType))
				{
					return ct;
				}
			}
		}
		throw credentialsTypeNotFound(credentialsType);
	}

	private static IllegalArgumentException credentialsTypeNotFound(String ct)
	{
		return new IllegalArgumentException(("Invalid credentials type [" + ct + "]"));
	}
}
