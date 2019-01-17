package com.minpet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.minpet.local.interf.IFileCandidateCache;
import com.minpet.model.FileCandidate;

@ApplicationScoped
public class FileCandidateCache implements IFileCandidateCache{

	private List<FileCandidate> cache = new ArrayList<>();
	private boolean valid = false;

	@Inject
	private Logger log;
	
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
		log.log(Level.FINER, "refreshshing cache contents");
		System.out.println("refreshing cache contents");
		this.cache = cache;
		valid = true;
	}

}
