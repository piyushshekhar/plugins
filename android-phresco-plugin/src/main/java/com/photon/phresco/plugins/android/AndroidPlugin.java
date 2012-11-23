package com.photon.phresco.plugins.android;

import org.apache.maven.plugin.logging.Log;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugin.commons.MavenProjectInfo;
import com.photon.phresco.plugins.PhrescoBasePlugin;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;

public class AndroidPlugin extends PhrescoBasePlugin {

	public AndroidPlugin(Log log) {
		super(log);
	}

	@Override
	public void pack(Configuration configuration,
			MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		Package pack = new Package();
		pack.pack(configuration, mavenProjectInfo, log);
	}

	@Override
	public void deploy(Configuration configuration,
			MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		Deploy deploy = new Deploy();
		deploy.deploy(configuration, mavenProjectInfo, log);
	}
	
	@Override
	public void runUnitTest(Configuration configuration, MavenProjectInfo mavenProjectInfo) throws PhrescoException{
		UnitTest test = new UnitTest();
		test.unitTest(configuration, mavenProjectInfo);
	}
	
	@Override
	public void runFunctionalTest(Configuration configuration, MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		FunctionalTest functionalTest = new FunctionalTest();
		functionalTest.functionalTest(configuration, mavenProjectInfo);
	}
	
	@Override
	public void runPerformanceTest(Configuration configuration, MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		PerformanceTest performanceTest = new PerformanceTest();
		performanceTest.performanceTest(configuration, mavenProjectInfo);
	}
}
