package com.sogeti.autotest.utils.browserstack;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class BrowserStackJobDetails
{
	private static final String INVALID_CHARACTERS_REGEX = "[^A-Za-z0-9 :._]";
	private static final Pattern pattern = Pattern.compile(INVALID_CHARACTERS_REGEX);
	private static final String SUBSTITUTE_CHARACTER = " ";

	private static final String LOCAL_ENVIRONMENT = "Local";
	private static final String BROWSERSTACK_LOCAL_JOB_NAME = "BROWSERSTACK_LOCAL_JOB_NAME";
	private static final String BROWSERSTACK_LOCAL_BUILD_NUMBER = "BROWSERSTACK_LOCAL_BUILD_NUMBER";
	private static final String BUILD_NUMBER = "BUILD_NUMBER";
	private static final String JOB_NAME = "JOB_NAME";

	private String project;
	private String build;

	public BrowserStackJobDetails()
	{
		StringBuilder projectBuilder = new StringBuilder(LocalDate.now().toString()).append(" : ");
		StringBuilder buildBuilder = new StringBuilder();

		String jobName = System.getenv(JOB_NAME);
		if (StringUtils.isBlank(jobName))
		{
			jobName = System.getenv(BROWSERSTACK_LOCAL_JOB_NAME);
		}
		if (StringUtils.isNotBlank(jobName))
		{
			if (jobName.startsWith("Tests/"))
			{
				String[] jobNameDivisions = jobName.split("/", 3);
				jobNameDivisions[1] = pattern.matcher(jobNameDivisions[1]).replaceAll(SUBSTITUTE_CHARACTER);
				jobNameDivisions[2] = pattern.matcher(jobNameDivisions[2]).replaceAll(SUBSTITUTE_CHARACTER);
				if (jobNameDivisions.length < 2)
				{
					projectBuilder.append(LOCAL_ENVIRONMENT);
					buildBuilder.append(jobNameDivisions[1]);
				}
				else
				{
					projectBuilder.append(jobNameDivisions[1]);
					buildBuilder.append(jobNameDivisions[2]);
				}
			}
			else
			{
				projectBuilder.append(LOCAL_ENVIRONMENT);
				buildBuilder.append(jobName);
			}
		}
		else
		{
			projectBuilder.append(LOCAL_ENVIRONMENT);
			buildBuilder.append("LOCAL");
		}

		buildBuilder.append(" : ").append(getBuildNumber());

		project = projectBuilder.toString();
		build = buildBuilder.toString();
	}

	public String getProject()
	{
		return project;
	}

	public String getBuild()
	{
		return build;
	}

	private String getBuildNumber()
	{
		String buildNumber = System.getenv(BUILD_NUMBER);
		if (StringUtils.isBlank(buildNumber))
		{
			buildNumber = System.getenv(BROWSERSTACK_LOCAL_BUILD_NUMBER);
		}
		if (StringUtils.isBlank(buildNumber))
		{
			buildNumber = "0";
		}
		return buildNumber;
	}
}
