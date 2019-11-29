package com.minpet.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.minpet.local.interf.IEbookRepository;

@Path("/ebookSelectedPage")
@RequestScoped
public class EbookSelectedPageResource {

	@Inject
	private IEbookRepository ebookRepository;
	
	@POST
	@Path("/{ebookId}/{selectedPage}")
	public void setSelectedPage(@PathParam("ebookId") long ebookId, @PathParam("selectedPage") int selectedPage) {
		ebookRepository.updateSelectedPage(ebookId, selectedPage);
	}
}
