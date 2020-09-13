package com.minpet.rest;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.minpet.local.interf.IEbookCoverRegistration;
import com.minpet.model.EbookImage;

@Path("/ebook/image")
@RequestScoped
public class EbookImageResource {
	
	@Inject
	private IEbookCoverRegistration imageRepository;
	
	@GET
	@Produces("application/json")
	@Path("/{id}")
	public Response getEbookImage(@PathParam("id") Long id, @Context UriInfo uriInfo) throws Exception {
		EbookImage im = imageRepository.findImageForEbook(id);
		
		if(im != null) {
			byte[] raw = Base64.getDecoder().decode(im.getContent());
			
			String mimeType = im.getContentType();
			if(mimeType == null) {
				mimeType = "image/jpg";
			}
			
			return Response.status(Response.Status.OK).header("Content-type", mimeType).header("Content-length", raw.length).entity(new ByteArrayInputStream(raw)).build();
		} else {
			throw new NotFoundException();
		}
	}
}