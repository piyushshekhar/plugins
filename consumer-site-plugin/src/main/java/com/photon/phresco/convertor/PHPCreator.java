package com.photon.phresco.convertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.photon.phresco.plugin.commons.MavenProjectInfo;
import com.photon.phresco.vo.CsvFileVO;

public class PHPCreator {

	public PHPCreator() {
	}

	public List<CsvFileVO> createPHPFile(List<CsvFileVO> list, MavenProjectInfo mavenProjectInfo) throws IOException,
			Exception {
		Set<String> uniqueFunctionNamesSet = new HashSet<String>();
		for (CsvFileVO csvo : list) {
			uniqueFunctionNamesSet.add(csvo.getPhpFunction());
		}
		StringBuffer phpFile = new StringBuffer();
		phpFile.append("<?php\n" + "/**\n" + "* @file\n" + "* ass\n" + "*/\n" + "/**\n"
				+ "* Check if the drush is executed from command line\n" + "*/\n" + "if (!drush_verify_cli()) {\n"
				+ "die('drush.php is designed to run via the command line.');\n" + "}\n");
		phpFile.append("include drupal_get_path('module', "
				+ "'itrinno_mobilecontent_api') . \"/includes/mobilecontent.admin.inc\";\n");
		Iterator<String> iter = uniqueFunctionNamesSet.iterator();
		while (iter.hasNext()) {
			String funcName = iter.next();
			if (funcName != null && funcName.length() > 0) {
				phpFile.append(funcName + "\n");
			}
		}
		List<String> buildCreateContentLineList = getBuildCreateContentLines(list);
		for (String buildCreateContentLine : buildCreateContentLineList) {
			phpFile.append(buildCreateContentLine + "\n");
		}
		phpFile.append("?>");
		System.out.println(mavenProjectInfo.getBaseDir() + File.separator + "source" + File.separator + "sites"
				+ File.separator + "all" + File.separator + "modules" + File.separator + "jnj_site_build"
				+ File.separator + "build" + File.separator + "scripts" + File.separator
				+ mavenProjectInfo.getProject().getProperties().getProperty("phresco.content.php.file.name"));
		new File(mavenProjectInfo.getBaseDir() + File.separator + "source" + File.separator + "sites" + File.separator
				+ "all" + File.separator + "modules" + File.separator + "jnj_site_build" + File.separator + "build"
				+ File.separator + "scripts").mkdir();
		File file = new File(mavenProjectInfo.getBaseDir() + File.separator + "source" + File.separator + "sites"
				+ File.separator + "all" + File.separator + "modules" + File.separator + "jnj_site_build"
				+ File.separator + "build" + File.separator + "scripts" + File.separator
				+ mavenProjectInfo.getProject().getProperties().getProperty("phresco.content.php.file.name"));
//		FileWriter fileWriter = new FileWriter(file);
//		fileWriter.write(phpFile.toString());
//		fileWriter.flush();

		FileOutputStream fos = new FileOutputStream(mavenProjectInfo.getBaseDir() + File.separator + "source" + File.separator + "sites"
				+ File.separator + "all" + File.separator + "modules" + File.separator + "jnj_site_build"
				+ File.separator + "build" + File.separator + "scripts" + File.separator
				+ mavenProjectInfo.getProject().getProperties().getProperty("phresco.content.php.file.name"));
		Writer out = new OutputStreamWriter(fos, "UTF8");
		out.write(phpFile.toString());
		out.flush();
		out.close();
		return list;
	}

	private List<String> getBuildCreateContentLines(List<CsvFileVO> list) {
		List<String> buildCreateContentLineList = new ArrayList<String>();
		for (CsvFileVO csvo : list) {

			String buildCreateContentLine = "build_create_content('" + csvo.getLanguage() + "','"
					+ csvo.getContentType() + "','" + csvo.getContentTypeName() + "','";

			Map<String, String> map = csvo.getTitleMap();
			Set<String> keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += map.get(k).replace("'", "\\'");
			}
			buildCreateContentLine += "','";
			map = csvo.getDescriptionMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += map.get(k).replace("'", "\\'");
			}
			buildCreateContentLine += "',";
			buildCreateContentLine += "array(";

			map = csvo.getExtraMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += "'" + k + "'=>";
				buildCreateContentLine += "'" + map.get(k).replace("'", "\\'") + "\'";
				buildCreateContentLine += ",";
			}
			buildCreateContentLine += "),array(";
			map = csvo.getImageMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += "'" + k + "'=>";
				buildCreateContentLine += "'" + map.get(k).replace("'", "\\'") + "'";
				buildCreateContentLine += ",";
			}
			buildCreateContentLine += "),array(";
			map = csvo.getCategoryMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += "'" + k + "'=>";
				buildCreateContentLine += "'" + map.get(k).replace("'", "\\'") + "'";
				buildCreateContentLine += ",";
			}
			buildCreateContentLine += "),array(";
			map = csvo.getUrlMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += "'" + k + "'=>";
				buildCreateContentLine += "'" + map.get(k).replace("'", "\\'") + "'";
				buildCreateContentLine += ",";
			}
			buildCreateContentLine += "),array(";
			map = csvo.getMetadataMap();
			keys = map.keySet();
			for (String k : keys) {
				buildCreateContentLine += "\'" + k + "\'=>";
				buildCreateContentLine += "\'" + map.get(k).replace("'", "\\'") + "\'";
				buildCreateContentLine += ",";
			}
			buildCreateContentLine = buildCreateContentLine.replace(",)", ")");
			buildCreateContentLine += "));";
			buildCreateContentLineList.add(buildCreateContentLine);
		}
		return buildCreateContentLineList;
	}
}