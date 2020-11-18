package com.sogeti.autotest.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sogeti.autotest.model.User;
import com.sogeti.autotest.utils.Config;

public class Users {

	public static User createExistingValidUser() {
		return createExistingValidUser(1);
	}

	public static User createExistingValidUser(int userid) {
		User user = new User();
		String username;

		switch (userid) {
		case 1:
			username = Config.getLocalisedMandatoryPropValue("user.existingusername");
			break;
		default:
			username = Config.getLocalisedMandatoryPropValue("user.existingusername" + Integer.toString(userid));

		}

		String password = Config.getLocalisedMandatoryPropValue("user.existingpassword");
		user.withUserName(username).withPassword(password)
				.withFirstName(Config.getLocalisedMandatoryPropValue("guest.firstname"))
				.withLastName(Config.getLocalisedMandatoryPropValue("guest.lastname"));
		return user;
	}

	/**
	 * Create a User with a valid email address but invalid password.
	 */
	public static User createExistingInvalidUser() {
		User user = new User();
		user.withUserName(Config.getLocalisedMandatoryPropValue("user.existingusername")).withPassword("password0");
		return user;
	}

	public static User createGivenValidUser(String email, String password) {
		User user = new User();
		user.withUserName(email).withPassword(password);
		return user;
	}

	public static User createNewValidUniqueNonSpamUser() {
		User user = new User();
		String prefix = Config.getLocalisedOptionalPropValue("user.env");
		String timeStamp = new SimpleDateFormat("yyMMdd.HH.mm.ss.SS").format(new Date());
		String uniqueemail = "sogeti.pangaea.dev+" + timeStamp + "@gmail.com";
		user.withUserName(uniqueemail).withPassword("Password123").withRepeatPassword("Password123")
				.withFirstName(prefix).withLastName("Kent");
		return user;
	}

	public static User createNewValidUniqueUser() {
		User user = new User();
		String prefix = Config.getLocalisedOptionalPropValue("user.env");
		String timeStamp = new SimpleDateFormat("yyMMdd.HH.mm.ss.SS").format(new Date());
		String uniqueemail = timeStamp + prefix.toLowerCase() + "@mailinator.com";
		user.withUserName(uniqueemail).withPassword("Password123").withRepeatPassword("Password123")
				.withFirstName(prefix).withLastName("Kent");
		return user;
	}

	public static User createNewInvalidUniqueUser() {
		User user = new User();
		String prefix = Config.getLocalisedMandatoryPropValue("user.env");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String uniqueemail = timeStamp + "@mailinator.com";
		user.withUserName(uniqueemail).withPassword("password").withRepeatPassword("password1").withFirstName(prefix)
				.withLastName("Kent");
		return user;
	}

}
