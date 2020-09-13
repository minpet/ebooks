package com.minpet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class EbookImage {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Lob
	private String content;
	
	private String contentType;
	private long ebookId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getEbookId() {
		return ebookId;
	}

	public void setEbookId(long ebookId) {
		this.ebookId = ebookId;
	}
}
