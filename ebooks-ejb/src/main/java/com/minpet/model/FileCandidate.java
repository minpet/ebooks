package com.minpet.model;

import java.io.File;

public class FileCandidate {

	private File underlyingFile;
	private String hashedName;

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
}
