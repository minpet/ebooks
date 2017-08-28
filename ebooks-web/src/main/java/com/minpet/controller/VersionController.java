package com.minpet.controller;

import javax.enterprise.inject.Model;

import org.omnifaces.util.Beans;

import com.minpet.local.interf.IVersionService;

@Model
public class VersionController {

	private IVersionService versionService;

	public VersionController(){
		versionService = Beans.getReference(IVersionService.class);
	}
	
	public String getVersion(){
		return versionService.getVersion();
	}
	
	public String getBuildDate(){
		return versionService.getBuildDate();
	}
}
