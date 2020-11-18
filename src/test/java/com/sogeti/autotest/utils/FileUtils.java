package com.sogeti.autotest.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.xfer.FileSystemFile;


public class FileUtils
{

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class.getName());

	public static File getFile(String filename)
	{
		File file = new File(filename);
		if (!file.exists())
		{
			try
			{
				file = new File(FileUtils.class.getClassLoader().getResource(filename).toURI());
			}
			catch (IllegalArgumentException | URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (file != null && file.exists() ? file : null);
	}

	public static JsonObject getJsonFromFile(String filePath) throws IOException
	{
		JsonObject jsonObject = null;
		Path file = getFile(filePath).toPath();
		try
		{
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(file.toString()));
			jsonObject = jsonElement.getAsJsonObject();
		}
		catch (IOException e)
		{
			logger.error("couldn't parse as Json");
		}

		return jsonObject;
	}

	public static SSHClient sshConnect(String hostname, String username) throws IOException
	{

		SSHClient ssh = new SSHClient();

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		ssh.addHostKeyVerifier(new PromiscuousVerifier());
		ssh.connect(hostname);
		logger.info("trying to connect to {} with username {}", hostname, username);

		if (Config.getCommonOptionalPropValue("test.keyfile") == null)
		{
			logger.info("trying to connect with default key");
			ssh.authPublickey(username);
		}
		else
		{
			logger.info("trying to connect with provided key file");
			PKCS8KeyFile keyFile = new PKCS8KeyFile();
			keyFile.init(new File(Config.getCommonMandatoryPropValue("test.keyfile")));
			ssh.authPublickey(username, keyFile);
		}

		ssh.useCompression();
		return ssh;

	}

	public static void scpFileUpload(String filename, String hostname, String username, String destDir)
			throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			if (destDir == null)
			{
			}

			ssh.newSCPFileTransfer().upload(new FileSystemFile(filename), destDir);
		}
		finally
		{
			if (ssh != null)
			{
				ssh.close();
				ssh.disconnect();
			}
		}
	}

	public static void scpFileDownload(String filename, String hostname, String username, String remoteDir,
			String localDir) throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			ssh.newSCPFileTransfer().download(remoteDir + "/" + filename, localDir);
		}
		finally
		{
			if (ssh != null)
			{
				ssh.close();
				ssh.disconnect();
			}
		}
		ssh.close();
	}

	public static String checkRemoteFile(String filename, String hostname, String username, String destDir)
			throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		String response = null;

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			final Session session = ssh.startSession();

			final Command cmd = session.exec("ls " + destDir + "/*" + filename + "* | wc -l");
			response = IOUtils.readFully(cmd.getInputStream()).toString().replace("\n", "");
			cmd.join(10, TimeUnit.SECONDS);

		}
		finally
		{
			if (ssh != null)
			{
				ssh.close();
				ssh.disconnect();
			}
		}
		ssh.close();

		return response;

	}

	public static String checkRemoteDirContainsText(String dir, String hostname, String username, String findText)
			throws IOException
	{

		return checkRemoteFilesContainsText(dir, hostname, username, findText, "*");
	}

	public static String checkRemoteFilesContainsText(String dir, String hostname, String username, String findText,
			String fileFilter) throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		String response = null;

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			final Session session = ssh.startSession();
			String shellCommand = "grep -i \"" + findText + "\" " + dir + "/" + fileFilter + "";
			final Command cmd = session.exec(shellCommand);
			response = IOUtils.readFully(cmd.getInputStream()).toString().replace("\n", "");
			cmd.join(10, TimeUnit.SECONDS);

		}
		finally
		{
			if (ssh != null)
			{
				ssh.close();
				ssh.disconnect();
			}
		}
		ssh.close();

		return response;

	}

	public static String renameRemoteFile(String originalfilename, String newfilename, String hostname, String username,
			String destDir) throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		String response = null;

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			final Session session = ssh.startSession();

			final Command cmd = session.exec("cd " + destDir + "/; mv " + originalfilename + " " + newfilename);
			response = IOUtils.readFully(cmd.getInputStream()).toString();
			cmd.join(10, TimeUnit.SECONDS);

		}
		finally
		{
			if (ssh != null)
			{
				ssh.close();
				ssh.disconnect();
			}
		}
		ssh.close();

		return response;

	}

	public static String emptyRemoteDir(String hostname, String username, String destDir) throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		String response = null;

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			final Session session = ssh.startSession();

			Command cmd = session.exec("rm " + destDir + "/* -f;ls " + destDir + "/* | wc -l");
			response = IOUtils.readFully(cmd.getInputStream()).toString().replace("\n", "");
			cmd.join(10, TimeUnit.SECONDS);
			// check the directory is empty
			assertThat(Integer.parseInt(response), equalTo(0));
		}
		catch (Exception e)
		{
			logger.error("couldn't ssh:" + e);
		}
		finally
		{
			ssh.disconnect();
		}
		ssh.close();

		return response;

	}

	public static String removeRemoteFiles(String hostname, String username, String destDir, String glob) throws IOException
	{

		if (hostname == null)
		{
			hostname = Config.getCommonMandatoryPropValue("test.servername");
		}
		if (username == null)
		{
			username = Config.getCommonMandatoryPropValue("test.serveruser");
		}

		String response = null;

		SSHClient ssh = null;
		try
		{

			ssh = sshConnect(hostname, username);

			final Session session = ssh.startSession();

			Command cmd = session.exec("rm " + destDir + "/" + glob + " -f;ls " + destDir + "/" + glob + " | wc -l");
			response = IOUtils.readFully(cmd.getInputStream()).toString().replace("\n", "");
			cmd.join(10, TimeUnit.SECONDS);
			// check the directory is empty
			assertThat(Integer.parseInt(response), equalTo(0));

		}
		finally
		{
			ssh.disconnect();
		}
		ssh.close();

		return response;

	}

}
