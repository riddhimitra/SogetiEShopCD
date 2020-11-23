package com.sogeti.autotest.utils.browserstack;

public class Build
{
	private AutomationBuild automation_build;

	public AutomationBuild getAutomation_build()
	{
		return automation_build;
	}

	public void setAutomation_build(AutomationBuild automation_build)
	{
		this.automation_build = automation_build;
	}

	static class AutomationBuild
	{
		private String name;
		private int duration;
		private String status;
		private String hashed_id;
		private String build_tag;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getDuration()
		{
			return duration;
		}

		public void setDuration(int duration)
		{
			this.duration = duration;
		}

		public String getStatus()
		{
			return status;
		}

		public void setStatus(String status)
		{
			this.status = status;
		}

		public String getHashed_id()
		{
			return hashed_id;
		}

		public void setHashed_id(String hashed_id)
		{
			this.hashed_id = hashed_id;
		}

		public String getBuild_tag() {
			return build_tag;
		}

		public void setBuild_tag(String build_tag) {
			this.build_tag = build_tag;
		}
	}
}

