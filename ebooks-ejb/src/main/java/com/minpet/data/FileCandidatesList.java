package com.minpet.data;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@RequestScoped
public class FileCandidatesList {

	@Resource(lookup="java:global/ebooks/bookstore")
	private URL bookstoreURL;
	
	private List<File> bookFiles;
	
	@PostConstruct
	public void init() throws URISyntaxException{
		String protocol = bookstoreURL.getProtocol();
		if("file".equals(protocol)){
			File bookstoreDir = new File(bookstoreURL.toURI());
			bookFiles = new ArrayList<File>();
			for(File file: bookstoreDir.listFiles()){
				bookFiles.add(file);
			}
		}else{
			throw new UnsupportedOperationException("java:global/ebooks/bookstore must be set to file:// protocol (found "+protocol+")");
		}
	}
	
	@Produces
	@Named
	public List<File> getBookFiles(){
		return bookFiles;
	}
}
