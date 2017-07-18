package com.minpet.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.minpet.service.VersionService;

@Model
public class VersionController {

	@Inject
	private VersionService versionService;
	
	public String getVersion(){
		return versionService.getVersion();
	}
	
	public String getBuildDate(){
		return versionService.getBuildDate();
	}
}
