package com.photon.phresco.plugins.blackberry;

import org.apache.maven.plugin.logging.Log;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugin.commons.MavenProjectInfo;
import com.photon.phresco.plugins.PhrescoBasePlugin;
import com.photon.phresco.plugins.api.ExecutionStatus;
import com.photon.phresco.plugins.impl.DefaultExecutionStatus;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;

public class BlackBerryPlugin extends PhrescoBasePlugin {

	public BlackBerryPlugin(Log log) {
		super(log);
	}
	
	@Override
	public ExecutionStatus pack(Configuration configuration,
			MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		Package pack = new Package();
		pack.pack(configuration, mavenProjectInfo, log);
		return new DefaultExecutionStatus();
	}

	@Override
	public ExecutionStatus deploy(Configuration configuration,
			MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		Deploy deploy = new Deploy();
		deploy.deploy(configuration, mavenProjectInfo, log);
		return new DefaultExecutionStatus();
	}
}
