package com.minpet.rest.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.NotSupportedException;

import com.minpet.local.interf.IEbookCoverRegistration;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.EbookImage;
import com.minpet.rest.EbookResource;
import com.minpet.rest.json.EbookJson;
import com.minpet.util.Base64ContentEncoder;

@Path("/admin/ebookImage/upload")
@RequestScoped
@RolesAllowed("authenticated")
public class UploadImageEbookResource {

	@Inject
	private IEbookCoverRegistration ebookCoverRegistration;
	
	@Inject
	private IEbookRepository ebookRepository;
	
	@Inject
	private Base64ContentEncoder base64ContentEncoder;
	
	@POST
	@Path("/{ebookId}")
	@Produces("application/json")
	@Consumes("text/plain")
	public EbookJson uploadAlbumArt(@PathParam("ebookId") long ebookId, byte[] reqStream, @Context UriInfo uriInfo) throws IOException {
		
		EbookImage im = ebookCoverRegistration.findImageForEbook(ebookId);
		if(im == null){
			File tmp = File.createTempFile("ebookImage", "tmp");
			
			base64ContentEncoder.decodeToFile(reqStream, tmp);
			EbookImage image;
			try(FileInputStream fis = new FileInputStream(tmp); BufferedInputStream bus = new BufferedInputStream(fis);){
				String mimeType = URLConnection.guessContentTypeFromStream(bus);
				image = ebookCoverRegistration.registerImage(ebookId, mimeType, base64ContentEncoder.encodeFileToBase64Binary(tmp));
				return EbookResource.convert(ebookRepository.findById(ebookId), image, uriInfo);
			} catch (Exception e) {
				throw new InternalServerErrorException(e);
			}
			finally {
				tmp.delete();
			}
		} else {
			throw new NotSupportedException("ebook (id "+ebookId+") already has image associated"); 
		}
	}
}
