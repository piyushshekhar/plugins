package com.photon.phresco.plugins.android;

import org.apache.maven.project.MavenProject;

import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.plugin.commons.MavenProjectInfo;
import com.photon.phresco.plugins.model.Mojos.Mojo.Configuration;
import com.photon.phresco.util.Constants;

public class UnitTest {
	
	public void unitTest(Configuration configuration, MavenProjectInfo mavenProjectInfo) throws PhrescoException {
		MavenProject project = mavenProjectInfo.getProject();
		String workingDir = project.getProperties().getProperty(Constants.POM_PROP_KEY_UNITTEST_DIR);
		RunAndroidTest.runAndroidTest(configuration, mavenProjectInfo, workingDir);
	}
}
