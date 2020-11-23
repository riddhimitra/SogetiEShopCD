package com.sogeti.autotest.utils.browserstack;

public class Session
{
	private AutomationSession automation_session;

	public AutomationSession getAutomation_session()
	{
		return automation_session;
	}

	public void setAutomation_session(AutomationSession automation_session)
	{
		this.automation_session = automation_session;
	}

	static class AutomationSession
	{
		private String appium_logs_url;
		private String browser;
		private String browser_console_logs_url;
		private String browser_url;
		private String browser_version;
		private String build_name;
		private String device;
		private String duration;
		private String har_logs_url;
		private String hashed_id;
		private String logs;
		private String name;
		private String os;
		private String os_version;
		private String project_name;
		private String public_url;
		private String reason;
		private String selenium_logs_url;
		private String status;
		private String video_url;
		private String created_at;
		private String browserstack_status;
		private String test_priority;

		public String getAppium_logs_url()
		{
			return appium_logs_url;
		}

		public void setAppium_logs_url(String appium_logs_url)
		{
			this.appium_logs_url = appium_logs_url;
		}

		public String getBrowser()
		{
			return browser;
		}

		public void setBrowser(String browser)
		{
			this.browser = browser;
		}

		public String getBrowser_console_logs_url()
		{
			return browser_console_logs_url;
		}

		public void setBrowser_console_logs_url(String browser_console_logs_url)
		{
			this.browser_console_logs_url = browser_console_logs_url;
		}

		public String getBrowser_url()
		{
			return browser_url;
		}

		public void setBrowser_url(String browser_url)
		{
			this.browser_url = browser_url;
		}

		public String getBrowser_version()
		{
			return browser_version;
		}

		public void setBrowser_version(String browser_version)
		{
			this.browser_version = browser_version;
		}

		public String getBuild_name()
		{
			return build_name;
		}

		public void setBuild_name(String build_name)
		{
			this.build_name = build_name;
		}

		public String getDevice()
		{
			return device;
		}

		public void setDevice(String device)
		{
			this.device = device;
		}

		public String getDuration()
		{
			return duration;
		}

		public void setDuration(String duration)
		{
			this.duration = duration;
		}

		public String getHar_logs_url()
		{
			return har_logs_url;
		}

		public void setHar_logs_url(String har_logs_url)
		{
			this.har_logs_url = har_logs_url;
		}

		public String getHashed_id()
		{
			return hashed_id;
		}

		public void setHashed_id(String hashed_id)
		{
			this.hashed_id = hashed_id;
		}

		public String getLogs()
		{
			return logs;
		}

		public void setLogs(String logs)
		{
			this.logs = logs;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getOs()
		{
			return os;
		}

		public void setOs(String os)
		{
			this.os = os;
		}

		public String getOs_version()
		{
			return os_version;
		}

		public void setOs_version(String os_version)
		{
			this.os_version = os_version;
		}

		public String getProject_name()
		{
			return project_name;
		}

		public void setProject_name(String project_name)
		{
			this.project_name = project_name;
		}

		public String getPublic_url()
		{
			return public_url;
		}

		public void setPublic_url(String public_url)
		{
			this.public_url = public_url;
		}

		public String getReason()
		{
			return reason;
		}

		public void setReason(String reason)
		{
			this.reason = reason;
		}

		public String getSelenium_logs_url()
		{
			return selenium_logs_url;
		}

		public void setSelenium_logs_url(String selenium_logs_url)
		{
			this.selenium_logs_url = selenium_logs_url;
		}

		public String getStatus()
		{
			return status;
		}

		public void setStatus(String status)
		{
			this.status = status;
		}

		public String getVideo_url()
		{
			return video_url;
		}

		public void setVideo_url(String video_url)
		{
			this.video_url = video_url;
		}

		public String getCreated_at() {
			return created_at;
		}

		public void setCreated_at(String created_at) {
			this.created_at = created_at;
		}

		public String getBrowserstack_status() {
			return browserstack_status;
		}

		public void setBrowserstack_status(String browserstack_status) {
			this.browserstack_status = browserstack_status;
		}

		public String getTest_priority() {
			return test_priority;
		}

		public void setTest_priority(String test_priority) {
			this.test_priority = test_priority;
		}
	}
}
