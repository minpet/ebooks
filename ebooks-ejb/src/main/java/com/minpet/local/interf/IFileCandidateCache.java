package com.minpet.local.interf;

import java.util.List;

import com.minpet.model.FileCandidate;

public interface IFileCandidateCache {
	boolean isValid();
	void invalidate();
	List<FileCandidate> getCachedValues();
	void setCachedValues(List<FileCandidate> result);
}
