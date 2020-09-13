package com.minpet.rest.json;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EbookJson {

	private long id;
	private String name;
	private URI uri;
	private String underlyingFileName;
	private int selectedPage;
	private URI imageUrl;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getUnderlyingFileName() {
		return underlyingFileName;
	}
	public void setUnderlyingFileName(String underlyingFileName) {
		this.underlyingFileName = underlyingFileName;
	}
	public int getSelectedPage() {
		return selectedPage;
	}
	public void setSelectedPage(int selectedPage) {
		this.selectedPage = selectedPage;
	}
	public URI getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(URI imageUrl) {
		this.imageUrl = imageUrl;
	}

}
