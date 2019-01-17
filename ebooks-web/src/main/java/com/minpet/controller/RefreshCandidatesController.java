package com.minpet.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.minpet.local.interf.IFileCandidateCache;

@Model
public class RefreshCandidatesController {
	
	@Inject
	private IFileCandidateCache fileCandidateCache;
	
	public void refresh() {
		fileCandidateCache.invalidate();
	}
}
