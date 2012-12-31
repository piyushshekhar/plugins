package com.photon.phresco.plugins;

import org.apache.maven.plugin.logging.Log;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugin.commons.MavenProjectInfo;
import com.photon.phresco.plugins.drupal.DrupalPlugin;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;

public class ConsumerMobilePlugin extends DrupalPlugin {

	public ConsumerMobilePlugin(Log log) {
		super(log);
	}

	public void themeValidator(MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		log.info("Theme validation is being done");
	
	}

	public void themeConvertor(MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		log.info("Theme conversion is being done");
		
	}

	public void contentValidator(MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		log.info("Content validator is being done");
		
	}

	public void contentConvertor(MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		log.info("Content conversion is being done");
		
	}

	public void pack(Configuration configuration,
			MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		themeValidator(mavenProjectInfo);
		themeConvertor(mavenProjectInfo);
		contentValidator(mavenProjectInfo);
		contentConvertor(mavenProjectInfo);
		super.pack(configuration, mavenProjectInfo);
	}

}