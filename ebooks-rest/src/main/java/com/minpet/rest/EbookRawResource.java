package com.minpet.rest;

import java.io.File;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.minpet.data.EbookRepository;
import com.minpet.model.Ebook;
import com.minpet.service.BookstoreTranslator;

@Path("/raw")
@RequestScoped
public class EbookRawResource {

	@Inject
	private BookstoreTranslator bookstoreTranslator;
	
	@Inject
	private EbookRepository ebookRepository;
	
	@GET
	@Path("/{id}")
	@Produces("application/pdf")
	public File getContentsForEbook(@PathParam("id") long id) {
		Ebook ebook = ebookRepository.findById(id);
		return bookstoreTranslator.getFileFor(ebook);
	}
}
