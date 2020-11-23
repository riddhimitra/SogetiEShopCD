package com.sogeti.autotest.utils.browserstack;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Optional;

import com.sogeti.autotest.utils.Config;
import com.sogeti.autotest.utils.browserstack.Build.AutomationBuild;
import com.sogeti.autotest.utils.browserstack.Session.AutomationSession;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class BrowserStackResults
{
	private static final String REMOTE_BROWSER_DRIVER_PROVIDER = "RemoteBrowserDriverProvider";
	private static final String BROWSER_STACK = "BrowserStack";

	public Optional<String> getLatestSessionResults()
	{
		String buildId = getBuildId();

		if (buildId != null)
		{
			List<Session> sessions = getSessions(buildId);
			if (sessions.size() > 0)
			{
				AutomationSession automationSession = sessions.get(0).getAutomation_session();
				return Optional.ofNullable(automationSession.getPublic_url());
			}
		}
		return Optional.empty();
	}

	private String getBuildId()
	{
		String remoteBrowserDriverProvider = System.getenv(REMOTE_BROWSER_DRIVER_PROVIDER);
		if (remoteBrowserDriverProvider == null) {
			remoteBrowserDriverProvider = Config.getCommonOptionalPropValue(REMOTE_BROWSER_DRIVER_PROVIDER);
		}
		if (remoteBrowserDriverProvider == null || !remoteBrowserDriverProvider.equalsIgnoreCase(BROWSER_STACK))
		{
			return null;
		}

		List<Build> builds = getBuilds();

		BrowserStackJobDetails jobDetails = new BrowserStackJobDetails();
		String buildName = jobDetails.getBuild();
		String buildId = null;
		for (Build build : builds)
		{
			AutomationBuild automationBuild = build.getAutomation_build();
			if (automationBuild.getName().equals(buildName))
			{
				buildId = automationBuild.getHashed_id();
			}
		}
		return buildId;
	}

	private List<Build> getBuilds()
	{
		JsonPath response = restRequest("automate/", "builds.json");
		return response.getList("", Build.class);
	}

	private List<Session> getSessions(String buildId)
	{
		JsonPath response = restRequest("automate/builds", buildId + "/sessions.json");

		return response.getList("", Session.class);
	}

	public Integer getQueue()
	{
		JsonPath response = restRequest("automate", "/plan.json");

		return response.getInt("queued_sessions");
	}

	private JsonPath restRequest(String basePath, String path)
	{
		String username = System.getenv("BROWSERSTACK_USERNAME");
		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

		RestAssured.baseURI = "https://api.browserstack.com/";
		RestAssured.basePath = basePath;

		return given().auth().preemptive().basic(username, accessKey).when().get(path)
				.jsonPath();
	}
}
