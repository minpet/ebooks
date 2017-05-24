package com.minpet.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.minpet.data.EbookRepository;
import com.minpet.model.Ebook;

@WebServlet("/read/*")
public class BookReadingServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(BookReadingServlet.class);

	@Resource(lookup="java:global/ebooks/bookstore")
	private URL bookstoreUrl;
	
	@Inject
	private EbookRepository ebookRepository;

	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException{
		String[] parts = req.getRequestURI().split("/");
		String hashedName = null;
		for(String part: parts){
			if(part.endsWith(".ebook")){
				hashedName = part.split(".ebook")[0];
			}
		}
		
		if(hashedName == null){
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "book not specified");
		}else{
			Ebook ebook = ebookRepository.findEbookByHashedName(hashedName);
			File file;
			try {
				file = new File(new File(bookstoreUrl.toURI()).getAbsolutePath()+File.separator+ebook.getFile());
				if(!file.exists()){
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "specified book not found");
				}else{
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.setContentLengthLong(file.length());
					if(file.getName().endsWith(".pdf")){
						resp.setContentType("application/pdf");
					}else{
						resp.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					}
					try(OutputStream out = resp.getOutputStream(); InputStream in = new FileInputStream(file)){
						byte[] buff = new byte[1024];
						int c;
						while((c = in.read(buff))>0){
							out.write(buff, 0, c);
						}
					}catch(IOException e){
						LOGGER.error(e.getMessage(), e);
						throw e;
					}
				}
			} catch (URISyntaxException e1) {
				LOGGER.error(e1.getMessage(), e1);
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "cannot locate bookstore");
			}
		}
	}
}
