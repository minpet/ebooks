package com.minpet.service;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VersionService {

	private String version;
	private String buildDate;

	@PostConstruct
	private void init() throws IOException{
		Properties props = new Properties();
		props.load(VersionService.class.getResourceAsStream("/version.txt"));
		version = props.get("version").toString();
		buildDate = props.get("build.date").toString();
	}

	public String getVersion() {
		return version;
	}

	public String getBuildDate() {
		return buildDate;
	}
	
	
}
