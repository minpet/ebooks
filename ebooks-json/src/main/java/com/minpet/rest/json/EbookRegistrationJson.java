package com.minpet.rest.json;

public class EbookRegistrationJson {
	private String name;
	private String underlyingFile;
	private boolean autoIndex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnderlyingFile() {
		return underlyingFile;
	}
	public void setUnderlyingFile(String underlyingFile) {
		this.underlyingFile = underlyingFile;
	}
	public boolean isAutoIndex() {
		return autoIndex;
	}
	public void setAutoIndex(boolean autoIndex) {
		this.autoIndex = autoIndex;
	}
	
	
}
