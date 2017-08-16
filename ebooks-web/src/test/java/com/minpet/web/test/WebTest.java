package com.minpet.web.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.minpet.controller.EbookController;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;
import com.minpet.test.impl.VersionServiceMock;
import com.minpet.util.WebResources;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

@RunWith(Arquillian.class)
public class WebTest {

	private static final Logger LOGGER = Logger.getLogger(WebTest.class.getName());

	@Deployment(testable=false)
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
	public void testInitialPage() throws IOException, URISyntaxException{
		URL phantomDriver;
		
		if(SystemUtils.IS_OS_WINDOWS)
		{
			phantomDriver = WebTest.class.getResource("/phantomjs.exe");
		}
		else
		{
			phantomDriver = WebTest.class.getResource("/phantomjs");
		}
		
		File driverFile = new File(phantomDriver.toURI());
		driverFile.setExecutable(true);
		assertTrue(driverFile.exists());
		
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverFile.getAbsolutePath());
		
		PhantomJSDriver driver = new PhantomJSDriver(capabilities);		
		driver.manage().window().setSize(new Dimension(1024, 768));
		driver.setLogLevel(java.util.logging.Level.ALL);
		//System.out.println(driver.getCapabilities());
		
		int port = 11080;
		
		WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver, "http://localhost:"+port);
		selenium.open("/test/index.html");
		
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try 
		{
			File f = new File("target/screen1.png");
			FileUtils.copyFile(srcFile, f);
			LOGGER.log(Level.WARNING, "screenshot taken to "+f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.close();
		driver.quit();
	}
}
