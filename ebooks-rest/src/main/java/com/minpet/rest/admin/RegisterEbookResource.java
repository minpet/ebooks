package com.minpet.rest.admin;

import java.io.File;

import javax.annotation.security.RolesAllowed;
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
import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.model.Ebook;
import com.minpet.rest.EbookResource;
import com.minpet.rest.json.EbookJson;
import com.minpet.rest.json.EbookRegistrationJson;

@Path("/admin/ebookRegister")
@RequestScoped
@RolesAllowed("authenticated")
public class RegisterEbookResource {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterEbookResource.class);
	
	@Inject
	private IEbookRegistration ebookRegistration;
	
	@Inject
	private IFileCandidateRepository fileCandidateRepository;
	
	@Context 
	private UriInfo uriInfo;
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public EbookJson registerEbook(EbookRegistrationJson registrationReq) throws Exception{
		Ebook ebook = new Ebook();
		
		File f = fileCandidateRepository.findByHashedName(registrationReq.getHashedName());
		
		ebook.setName(registrationReq.getName());
		ebook.setHashedName(registrationReq.getHashedName());
		ebook.setFile(f.getName());
		ebookRegistration.register(ebook);
		
		if(ebook.isRegistered()) {
			LOGGER.debug("ebook "+ebook.getName()+" registered");
			return EbookResource.convert(ebook, uriInfo);
		}
		
		return null;
	}
}
