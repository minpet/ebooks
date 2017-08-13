package com.minpet.web.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.jsfunit.jsfsession.JSFSession;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minpet.controller.EbookController;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;
import com.minpet.test.impl.VersionServiceMock;
import com.minpet.util.WebResources;

@RunWith(Arquillian.class)
public class WebTest {

	private static final Logger LOGGER = Logger.getLogger(WebTest.class.getName());

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(WebResources.class)
				.addPackages(true, 
							EbookController.class.getPackage(),
							IEbookRepository.class.getPackage(),
							VersionServiceMock.class.getPackage(),
							Ebook.class.getPackage()
						)
				//.addAsLibraries(new File("target/test-libs").listFiles())
				//.addAsWebResource(new File("src/main/webapp/resources"))
				.addAsWebResource(new File("src/main/webapp/index.html"))
				.addAsWebResource(new File("src/main/webapp/index.xhtml"))
				.addAsWebResource(new File("src/main/webapp/read.xhtml"))
				.addAsWebResource(new File("src/main/webapp/register.xhtml"))
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/templates", "default.xhtml"), "templates/default.xhtml")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF", "beans.xml"))
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF", "faces-config.xml"))
				;
	}
	
	@Test
	public void testInitialPage() throws IOException{
		JSFSession session = new JSFSession("/index.html");
		JSFServerSession server = session.getJSFServerSession();
		JSFClientSession client = session.getJSFClientSession();
		
		File f = new File("target/output.html");
		try(FileOutputStream fos = new FileOutputStream(f)){
			fos.write(client.getPageAsText().getBytes());
		}
		LOGGER.log(Level.INFO, "content written to " +f.getAbsolutePath());
	}
}
