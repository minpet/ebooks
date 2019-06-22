package com.minpet.rest.json;

import java.util.List;

public class FileCandidateJson {
	private List<String> conflicts;
	private String hashedName;
	
	public List<String> getConflicts() {
		return conflicts;
	}

	public String getHashedName() {
		return hashedName;
	}

	public String getUnderlyingFileName() {
		return underlyingFileName;
	}

	private String underlyingFileName;
	
	public void setConflicts(List<String> conflicts) {
		this.conflicts = conflicts;
	}

	public void setHashedName(String hashedName) {
		this.hashedName = hashedName;
	}

	public void setUnderlyingFileName(String name) {
		this.underlyingFileName = name;
	}

}
