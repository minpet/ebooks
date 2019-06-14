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
	public List<EbookJson> listEbooks(){
		List<EbookJson> result = new ArrayList<>();
	
		for(Ebook ebook : ebookRepository.findAllOrderedByName()) {
			result.add(convert(ebook));
		}
		return result;
	}
	
	@GET
	@Produces("application/json")
	@Path("/{id}")
	public EbookJson getEbook(@PathParam("id") Long id) {
		return convert(ebookRepository.findById(id));
	}
	
	@POST
	@Produces("application/json")
	public List<EbookJson> updateListEbooks(){
		
		List<EbookJson> result = new ArrayList<>();
	
		for(Ebook ebook : ebookRepository.findAllOrderedByName()) {
			EbookJson json = new EbookJson();
			json.setId(ebook.getId());
			json.setName(ebook.getName());
			result.add(json);
		}
		return result;
	}
	
	private EbookJson convert(Ebook ebook) {
		EbookJson json = new EbookJson();
		json.setId(ebook.getId());
		json.setName(ebook.getName());
		return json;
	}
}
