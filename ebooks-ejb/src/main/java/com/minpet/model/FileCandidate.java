package com.minpet.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileCandidate {

	private File underlyingFile;
	private String hashedName;
	private List<String> conflicts = new ArrayList<>();

	public File getUnderlyingFile() {
		return underlyingFile;
	}
	public void setUnderlyingFile(File underlyingFile) {
		this.underlyingFile = underlyingFile;
		setHashedName(underlyingFile.getName().replaceAll(" ", "").replaceAll("&", "").replaceAll(",", ""));
	}
	public String getHashedName() {
		return hashedName;
	}
	public void setHashedName(String hashedName) {
		this.hashedName = hashedName;
	}
	
	public void setConflicts(List<String> conflicts) {
		this.conflicts  = conflicts;
	}
	
	public List<String> getConflicts() {
		return conflicts;
	}
}
