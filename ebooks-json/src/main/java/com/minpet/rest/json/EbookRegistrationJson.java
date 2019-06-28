package com.minpet.rest.json;

public class EbookRegistrationJson {
	private String name;
	private String hashedName;
	private boolean autoIndex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHashedName() {
		return hashedName;
	}
	public void setHashedName(String hashedName) {
		this.hashedName = hashedName;
	}
	public boolean isAutoIndex() {
		return autoIndex;
	}
	public void setAutoIndex(boolean autoIndex) {
		this.autoIndex = autoIndex;
	}
	
	
}
