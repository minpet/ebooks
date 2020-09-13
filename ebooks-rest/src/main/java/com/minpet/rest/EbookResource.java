package com.minpet.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IImageRepository;
import com.minpet.model.Ebook;
import com.minpet.model.EbookImage;
import com.minpet.rest.json.EbookJson;

@Path("/ebook")
@RequestScoped
public class EbookResource {

	@Inject
	private IEbookRepository ebookRepository;
	
	@Inject
	private IImageRepository imageRepository;
	
	@GET
	@Produces("application/json")
	public List<EbookJson> listEbooks(@Context UriInfo uriInfo) throws Exception {
		List<EbookJson> result = new ArrayList<>();
	
		for(Ebook ebook : ebookRepository.findAllOrderedByName()) {
			result.add(convert(ebook, imageRepository.findImageForEbook(ebook.getId()), uriInfo));
		}
		return result;
	}
	
	@GET
	@Produces("application/json")
	@Path("/{id}")
	public EbookJson getEbook(@PathParam("id") Long id, @Context UriInfo uriInfo) throws Exception {
		return convert(ebookRepository.findById(id), imageRepository.findImageForEbook(id), uriInfo);
	}
	
	public static EbookJson convert(Ebook ebook, EbookImage image, UriInfo uriInfo) throws Exception {
		EbookJson json = new EbookJson();
		json.setId(ebook.getId());
		json.setName(ebook.getName());
		json.setUri(uriInfo.getBaseUriBuilder().path("/raw/{id}").build(ebook.getId()));
		int selectedPage = ebook.getSelectedPage() == null ? 0 : ebook.getSelectedPage();
		json.setSelectedPage(selectedPage);
		json.setUnderlyingFileName(ebook.getFile());
		if(image != null) {
			json.setImageUrl(uriInfo.getBaseUriBuilder().path("/ebook/image/{id}").build(ebook.getId()));
		}
		return json;
	}
}
