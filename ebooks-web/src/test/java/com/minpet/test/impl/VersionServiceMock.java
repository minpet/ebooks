package com.minpet.test.impl;

import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IVersionService;

@ApplicationScoped
public class VersionServiceMock implements IVersionService{

	@Override
	public String getVersion() {
		return "dummyVersion";
	}

	@Override
	public String getBuildDate() {
		return "dummyBuildDate";
	}

}
