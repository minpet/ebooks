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
	private URL bookstoreUrl;
	
	private List<File> fileCandidates;
	
	@Produces
    @Named
    public List<File> getFileCandidates() {
        return fileCandidates;
    }
	
	@PostConstruct
	private void init() throws URISyntaxException{
		String protocol = bookstoreUrl.getProtocol();
		if("file".equals(protocol)){
			fileCandidates = new ArrayList<File>();
			File f = new File(bookstoreUrl.toURI());
			for(File file : f.listFiles()){
				fileCandidates.add(file);
			}
		}else{
			throw new UnsupportedOperationException("'java:global/ebooks/bookstore' needs to have file:// protocol, but "+protocol+" found");
		}
	}
}
