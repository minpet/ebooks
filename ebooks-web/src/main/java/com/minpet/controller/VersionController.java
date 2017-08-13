package com.minpet.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.minpet.local.interf.IVersionService;

@Model
public class VersionController {

	@Inject
	private IVersionService versionService;
	
	public String getVersion(){
		return versionService.getVersion();
	}
	
	public String getBuildDate(){
		return versionService.getBuildDate();
	}
}
