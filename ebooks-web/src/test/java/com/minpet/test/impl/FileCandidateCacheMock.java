package com.minpet.test.impl;

import java.util.ArrayList;
import java.util.List;

import com.minpet.local.interf.IFileCandidateCache;
import com.minpet.model.FileCandidate;

public class FileCandidateCacheMock implements IFileCandidateCache {

	private List<FileCandidate> cache = new ArrayList<>();
	private boolean valid = false;

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public void invalidate() {
		valid = false;
	}

	@Override
	public List<FileCandidate> getCachedValues() {
		return cache;
	}

	@Override
	public void setCachedValues(List<FileCandidate> cache) {
		System.out.println("refreshing cache");
		this.cache = cache;
		valid = true;
	}

}
