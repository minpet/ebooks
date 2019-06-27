package com.minpet.rest.admin;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.logging.Logger;

import com.minpet.local.interf.IEbookRegistration;
import com.minpet.model.Ebook;
import com.minpet.rest.EbookResource;
import com.minpet.rest.json.EbookJson;
import com.minpet.rest.json.EbookRegistrationJson;

@Path("/admin/registerEbook")
@RequestScoped
public class RegisterEbookResource {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterEbookResource.class);
	
	@Inject
	private IEbookRegistration ebookRegistration;
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public EbookJson registerEbook(@Context UriInfo uriInfo, EbookRegistrationJson registrationReq){
		Ebook ebook = new Ebook();
		ebook.setFile(registrationReq.getUnderlyingFile());
		ebook.setName(registrationReq.getName());
		try {
			ebookRegistration.register(ebook);
			if(ebook.isRegistered()) {
				return EbookResource.convert(ebook, uriInfo);
			}
		} catch(Exception e) {LOGGER.error(e.getMessage(), e);}
	
		throw new IllegalArgumentException("Wrong registration provided");
	}
}
