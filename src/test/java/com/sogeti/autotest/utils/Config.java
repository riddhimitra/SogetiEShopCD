package com.sogeti.autotest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.specification.ProxySpecification;


public class Config
{
	private static final String GLOBAL_PROPERTIES = "GLOBAL.properties";
	private static final String LOCAL_PROPERTIES_FILE = "localisedPropertiesFile";
	private static final String REGION_PROPERTIES_FILE = "regionalPropertiesFile";
	private static final String COMMON_PROPERTIES_FILE = "commonPropertiesFile";
	public static final Properties props = new Properties();
	public static HashMap<String, Properties> propertyObjMap = new HashMap<String, Properties>();
	private static HashMap<String, String> localisedPropertyMap = new HashMap<String, String>();

	private static final Logger logger = LoggerFactory.getLogger(Config.class.getName());


	public static Map<String, String> getLocalisedPropertyMap()
	{
		if (localisedPropertyMap.size() == 0)

		{
			Properties globalProps = loadPropertiesFile(props.getProperty(GLOBAL_PROPERTIES));
			globalProps.keySet()
					.forEach(keyValue -> localisedPropertyMap.put(keyValue.toString(), globalProps.getProperty(keyValue.toString())));

			Properties regionProps = loadPropertiesFile(props.getProperty(REGION_PROPERTIES_FILE));
			regionProps.keySet()
					.forEach(keyValue -> localisedPropertyMap.put(keyValue.toString(), regionProps.getProperty(keyValue.toString())));

			Properties localProps = loadPropertiesFile(props.getProperty(LOCAL_PROPERTIES_FILE));
			localProps.keySet()
					.forEach(keyValue -> localisedPropertyMap.put(keyValue.toString(), localProps.getProperty(keyValue.toString())));
		}


		return localisedPropertyMap;
	}

	public static void emptyLocalisedPropertyMap()
	{
		if (localisedPropertyMap.size() != 0)
		{
			localisedPropertyMap.clear();
		}

	}

	public static synchronized String getPropertyValue(String property, String propertyFile)
	{
		String hostOverride = System.getProperty("host.name");
		String hostInternal = System.getProperty("host.internal");
		String result = "";
		Properties prop = propertyObjMap.get(propertyFile);

		if (null == prop)
		{
			prop = loadPropertiesFile(propertyFile);
			propertyObjMap.put(propertyFile, prop);
		}

		if (StringUtils.isNotBlank(hostOverride) && Boolean.valueOf(hostInternal) && StringUtils.startsWith(property, "url."))
		{
			result = prop.getProperty(property.concat(".internal"));
			if (result != null)
			{
				result = result.replace("<ChangeMe>", hostOverride);
				logger.info("switching to the internal spin up env {}", result);

			}
			return result;
		}
		else if (StringUtils.isNotBlank(hostOverride) && property.contains("url."))
		{
			result = prop.getProperty(property);
			if (result != null)
			{
				result = result.replace("<ChangeMe>", hostOverride);
				logger.info("switching to the external spin up env {}", result);
			}

			return result;
		}
		else
		{
			return prop.getProperty(property);
		}


	}


	public static Properties loadPropertiesFile(String propertyFile)
	{
		Properties prop = new Properties();
		FileInputStream input = null;
		try
		{
			input = new FileInputStream(new File("properties/" + propertyFile));
		}
		catch (FileNotFoundException e1)
		{
			logger.info("couldn't open propertyfile {}: {}", propertyFile, e1);
		}
		try
		{
			if (input != null)
			{

				prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));
			}

		}
		catch (Exception e)
		{
			logger.error("Exception: " + e);
		}
		finally
		{

			if (input != null)
			{

				try
				{
					input.close();
				}
				catch (IOException e)
				{
					logger.info("couldn't close input");
				}

			}
		}
		return prop;
	}


	private static void assertMandatoryPropertyValue(String result, String property)
	{
		if (StringUtils.isEmpty(result))
		{
			logger.error("mandatory property missing: {}", property);
			org.junit.Assert.fail("Mandatory Property " + property + " either empty or not specified");
		}
	}

	/**
	 * This method gets the specified property from the Common Properties file, eg DEV.properties, GB3DEV.properties.
	 * @param property String value of the property to get.
	 * @return Returns a static String.
	 */
	public static synchronized String getCommonOptionalPropValue(String property)
	{
		String propFileName = Config.props.getProperty(COMMON_PROPERTIES_FILE);
		String result = getPropertyValue(property, propFileName);
		if (StringUtils.isBlank(result))
		{
			logger.debug("{} not in the env specific common property file, trying global", property);
			result = getPropertyValue(property, GLOBAL_PROPERTIES);
		}
		return result;
	}

	public static String getResultIfNull(String property, String region)
	{
		String result = null;
		logger.debug("{} not in the env specific localised property file, trying global localised", property);
		result = getPropertyValue(property, "GLOBAL/" + region + ".properties");
		if (StringUtils.isBlank(result))
		{
			logger.debug("{} not in the global localised property file, trying global", property);
			result = getPropertyValue(property, GLOBAL_PROPERTIES);
		}
		return result;
	}

	public static synchronized String getLocalisedOptionalPropValue(String property)
	{
		String propFileName = Config.props.getProperty(LOCAL_PROPERTIES_FILE);
		String region = Config.props.getProperty("region");
		String result = getPropertyValue(property, propFileName);
		if (StringUtils.isBlank(result))
		{
			result = getResultIfNull(property, region);
		}
		return result;
	}


	public static synchronized String getCommonMandatoryPropValue(String property)
	{
		String propFileName = Config.props.getProperty(COMMON_PROPERTIES_FILE);
		String result = getPropertyValue(property, propFileName);
		if (StringUtils.isBlank(result))
		{
			logger.debug("{} not in the env specific common property file, trying global", property);
			result = getPropertyValue(property, GLOBAL_PROPERTIES);
		}
		assertMandatoryPropertyValue(result, property);
		return result;
	}

	public static synchronized String getLocalisedMandatoryPropValue(String property)
	{

		String propFileName = Config.props.getProperty(LOCAL_PROPERTIES_FILE);
		String region = Config.props.getProperty("region");
		String result = getPropertyValue(property, propFileName);

		if (StringUtils.isBlank(result))
		{
			result = getResultIfNull(property, region);
		}
		assertMandatoryPropertyValue(result, property);
		return result;
	}

	public static synchronized String getTunnelIdentifier()
	{
		return Config.getCommonOptionalPropValue("sauce.tunnel");
	}

	public static synchronized String getHubUrl()
	{

		String username = System.getenv("SAUCE_USERNAME");
		String accesskey = System.getenv("SAUCE_ACCESS_KEY");

		if (StringUtils.isEmpty(username))
		{
			username = Config.getCommonMandatoryPropValue("sauce.username");
		}

		if (StringUtils.isEmpty(accesskey))
		{
			accesskey = Config.getCommonMandatoryPropValue("sauce.key");
		}

		String hubURL = "http://" + username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub";
		return hubURL;
	}
}
