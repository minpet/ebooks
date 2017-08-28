package com.minpet.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.minpet.local.interf.IBookstoreTranslator;
import com.minpet.model.Ebook;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@ApplicationScoped
public class BookstoreTranslator implements IBookstoreTranslator{
	
	@SuppressFBWarnings
	@Resource(lookup="java:global/ebooks/bookstore")
    private URL bookstoreUrl;
	private Logger log;

	@Inject
	public BookstoreTranslator(Logger log){
		this.log=log;
	}
	
	public File getFileFor(Ebook currentEbook) {
		try {
			return new File(new File(bookstoreUrl.toURI()).getAbsolutePath()+File.separator+currentEbook.getFile());
		} catch (URISyntaxException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}	
}
