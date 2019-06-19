package com.minpet.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.minpet.data.EbookRepository;
import com.minpet.model.Ebook;
import com.minpet.rest.json.EbookJson;

@Path("/ebook")
@RequestScoped
public class EbookResource {

	@Inject
	private EbookRepository ebookRepository;
	
	@GET
	@Produces("application/json")
	public List<EbookJson> listEbooks(@Context UriInfo uriInfo) throws Exception {
		List<EbookJson> result = new ArrayList<>();
	
		for(Ebook ebook : ebookRepository.findAllOrderedByName()) {
			result.add(convert(ebook, uriInfo));
		}
		return result;
	}
	
	@GET
	@Produces("application/json")
	@Path("/{id}")
	public EbookJson getEbook(@PathParam("id") Long id, @Context UriInfo uriInfo) throws Exception {
		return convert(ebookRepository.findById(id), uriInfo);
	}
	
	@POST
	@Produces("application/json")
	public List<EbookJson> updateListEbooks(@Context UriInfo uriInfo) throws Exception {
		
		List<EbookJson> result = new ArrayList<>();
	
		for(Ebook ebook : ebookRepository.findAllOrderedByName()) {
			EbookJson json = convert(ebook, uriInfo);
			result.add(json);
		}
		return result;
	}
	
	private EbookJson convert(Ebook ebook, UriInfo uriInfo) throws Exception {
		EbookJson json = new EbookJson();
		json.setId(ebook.getId());
		json.setName(ebook.getName());
		json.setUri(uriInfo.getBaseUriBuilder().path("/raw/{id}").build(ebook.getId()));
		return json;
	}
}
