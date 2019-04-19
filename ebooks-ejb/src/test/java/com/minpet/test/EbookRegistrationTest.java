/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minpet.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.awaitility.Awaitility;
import org.awaitility.proxy.ProxyCreator;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;

import com.minpet.data.EbookRepository;
import com.minpet.local.interf.IEbookRegistration;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IEbookService;
import com.minpet.local.interf.IElasticSearchEbook;
import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.local.interf.IVersionService;
import com.minpet.model.Ebook;
import com.minpet.model.FileCandidate;
import com.minpet.service.Base64ContentEncoder;
import com.minpet.service.BookstoreTranslator;
import com.minpet.service.EbookRegistration;
import com.minpet.service.EbookService;
import com.minpet.service.ElasticSearchEbook;
import com.minpet.service.VersionService;
import com.minpet.service.ocr.OcrEngine;
import com.minpet.util.Resources;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.MethodInterceptor;
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.objenesis.Objenesis;

@RunWith(Arquillian.class)
@ServerSetup(CreateJndiResource.class)
public class EbookRegistrationTest {
    private static EmbeddedElastic elasticSearchServer;

	@Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(
                		Ebook.class,
                		FileCandidate.class, 
                		EbookRegistration.class,
                		EbookService.class,
                		Resources.class,
                		ElasticSearchEbook.class,
                		BookstoreTranslator.class,
                		Base64ContentEncoder.class,
                		VersionService.class,
                		OcrEngine.class,
                		//TEST
                		MethodInterceptor.class,
                		Callback.class,
                		TestHttpClient.class
                		)
                .addPackages(true,
                		IEbookRepository.class.getPackage(),
                		EbookRepository.class.getPackage(),
                		Awaitility.class.getPackage(),
                		ProxyCreator.class.getPackage(),
                		Objenesis.class.getPackage(),
                		Properties.class.getPackage(),
                		IOException.class.getPackage()
                		)
                .addPackages(true, "com.fasterxml", "org.apache.commons.lang3", "org.apache.commons.io", "net.lingala", "org.omnifaces")
                .addAsLibraries(new File("target/test-libs").listFiles())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("version.txt")
                .addAsResource("all.png")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }

    @Inject
    IEbookRegistration memberRegistration;

    @Inject
    IFileCandidateRepository fileCandidateRepository;
    
    @Inject
    IElasticSearchEbook elasticSearchEbook;
    
    @Inject
    Logger log;
    
    @Inject
    IVersionService versionService;
    
    @Inject
    IEbookService ebookService;
    
	@SuppressFBWarnings
	@Resource(lookup="java:global/elasticsearch/host")
	private String elasticSearchHost;
	
	@SuppressFBWarnings
	@Resource(lookup="java:global/elasticsearch/port")
	private int elasticSearchPort;

    @Test
    public void testRegister() throws Exception {
    	init();
    	List<FileCandidate> candidates = fileCandidateRepository.getFileCandidates();
    	
    	assertEquals(candidates.size(), 2);
        
        for(int i =0; i<candidates.size(); i++) {
        	Ebook newEbook = new Ebook();
        	newEbook.setName("Jane Doe "+i);
        	newEbook.setFile(candidates.get(i).getUnderlyingFile().getName());
        	newEbook.setHashedName(candidates.get(i).getHashedName());
        	memberRegistration.register(newEbook);
        	assertNotNull(newEbook.getId());
        	log.info(newEbook.getName() + " was persisted with id " + newEbook.getId());
        	elasticSearchEbook.createContent(newEbook);
        	valideContent(newEbook);
        	ebookService.createIndex(newEbook.getId());
        }
        
        versionService.getBuildDate();
        versionService.getVersion();
        
    }

	private void valideContent(Ebook ebook) throws IOException {
		HttpHost host = new HttpHost(elasticSearchHost, elasticSearchPort);		
		RestClient client = RestClient.builder(host).build();
		
		String uri = "/ebook/pdf/"+ebook.getId();
		
		Request req = new Request("GET", uri);
	    Response response = client.performRequest(req);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    IOUtils.copy(response.getEntity().getContent(), baos);
	    log.warning(baos.toString());
	}

	public static void init() {
		try {
			elasticSearchServer = EmbeddedElastic.builder()
					.withDownloadUrl(new URL("https://build-machine.local/devOnly/embedded/elasticsearch-5.4.1.zip"))
					.withStartTimeout(5, TimeUnit.MINUTES).withSetting(PopularProperties.HTTP_PORT, 12200)
					.withSetting(PopularProperties.CLUSTER_NAME, "testCluster")
					.withSetting("discovery.zen.ping.unicast.hosts", new String[]{})
					.withEsJavaOpts("-Xms128m -Xmx256m").build();
			elasticSearchServer.start();
			TestHttpClient.setupPipeline(12200);
		} catch (IOException | InterruptedException e) {
			Logger.getLogger(EbookRegistration.class.getName()).log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
