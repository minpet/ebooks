package com.minpet.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

import com.minpet.model.Ebook;

@ApplicationScoped
public class BookstoreTranslator {
	
	@Resource(lookup="java:global/ebooks/bookstore")
    private URL bookstoreUrl;

	public File getFileFor(Ebook currentEbook) throws URISyntaxException {
		return new File(new File(bookstoreUrl.toURI()).getAbsolutePath()+File.separator+currentEbook.getFile());
	}

	
	
}
