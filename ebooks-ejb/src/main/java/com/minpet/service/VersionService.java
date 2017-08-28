package com.minpet.service;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IVersionService;

@ApplicationScoped
public class VersionService implements IVersionService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String version;
	private String buildDate;

	@PostConstruct
	private void init() throws IOException{
		Properties props = new Properties();
		props.load(VersionService.class.getResourceAsStream("/version.txt"));
		version = props.get("version").toString();
		buildDate = props.get("build.date").toString();
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getBuildDate() {
		return buildDate;
	}
	
	
}
