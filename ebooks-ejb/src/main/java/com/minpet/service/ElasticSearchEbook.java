package com.minpet.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import com.minpet.http.HttpGetWithEntity;
import com.minpet.local.interf.IBookstoreTranslator;
import com.minpet.local.interf.IElasticSearchEbook;
import com.minpet.model.Ebook;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@ApplicationScoped
public class ElasticSearchEbook implements IElasticSearchEbook{
	private static final long serialVersionUID = 1L;
	private transient Logger log;
	private IBookstoreTranslator bookstoreTranslator;
	private Base64ContentEncoder base64ContentEncoder;

	@SuppressFBWarnings
	@Resource(lookup="java:global/elasticsearch/host")
	private String elasticSearchHost;
	
	@SuppressFBWarnings
	@Resource(lookup="java:global/elasticsearch/port")
	private int elasticSearchPort;

	@Inject
	public ElasticSearchEbook(Logger log, IBookstoreTranslator bookstoreTranslator,
			Base64ContentEncoder base64ContentEncoder){
		this.log=log;
		this.bookstoreTranslator=bookstoreTranslator;
		this.base64ContentEncoder=base64ContentEncoder;
	}
	
	@Override
	public void createContent(Ebook selectedEbook) throws IOException{
		
		HttpHost host = new HttpHost(elasticSearchHost, elasticSearchPort);		
		RestClient client = RestClient.builder(host).build();
		
		String uri = "/ebook/pdf/"+selectedEbook.getId();

		try {
			File pdf = bookstoreTranslator.getFileFor(selectedEbook);
			String base64Data = base64ContentEncoder.encodeFileToBase64Binary(pdf);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{ ");
			builder.append("\"name\":\""+selectedEbook.getName()+"\",");
			builder.append("\"hashedName\":\""+selectedEbook.getHashedName()+"\",");
			builder.append("\"file\":\""+selectedEbook.getFile()+"\",");
			builder.append("\"data\":\""+base64Data+"\"");
			builder.append(" } ");
			
			NStringEntity params =new NStringEntity(builder.toString(), ContentType.APPLICATION_JSON);
			
		    String message = "sending request to http://"+elasticSearchHost+":"+elasticSearchPort+"/"+uri;
		    log.fine(message);
		    Request req = new Request("POST", uri);
		    req.addParameter("pipeline", "attachment");
		    req.setEntity(params);
			Response response = client.performRequest(req);
		    
			int responseCode = response.getStatusLine().getStatusCode();
			log.fine("response = "+responseCode);
			if(responseCode < 200 || responseCode >= 400){
				throw new IOException(response.getStatusLine().getReasonPhrase());
			}
		}catch(IOException e){
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String[] searchForString(String fulltext) throws IOException {
		HttpHost target = new HttpHost(elasticSearchHost, elasticSearchPort, "http");

		String uri = "/ebook/pdf/_search";
		try(CloseableHttpClient httpclient = HttpClients.createMinimal()){

			@SuppressWarnings("deprecation")
			RequestConfig config = RequestConfig.custom()
				.setAuthenticationEnabled(false)
				.setStaleConnectionCheckEnabled(false).build();

			HttpGetWithEntity request = new HttpGetWithEntity(uri);
			request.setConfig(config);
			//request.addHeader("content-type", "application/x-www-form-urlencoded");

			StringBuilder builder = new StringBuilder();
			builder.append("{ ");
			builder.append("\"query\": {");
			builder.append("    \"match_phrase\": {");
			builder.append("         \"attachment.content\": \""+fulltext+"\"");
			builder.append("    }");
			builder.append(" } ");
			builder.append("}");
			
			StringEntity params =new StringEntity(builder.toString());
		    request.setEntity(params);

			CloseableHttpResponse response = httpclient.execute(target, request);

			int responseCode = response.getStatusLine().getStatusCode();
			if(responseCode < 200 || responseCode >= 400){
				throw new IOException(response.getStatusLine().getReasonPhrase());
			}
			
			StringBuilder respBuilder = new StringBuilder();
			try(InputStream is= response.getEntity().getContent()) {
				byte[] buffer = new byte[2048];
		        int length;
		        while ((length = is.read(buffer)) != -1) {
		            respBuilder.append(new String(buffer, 0, length));
		        }
			}
			log.severe(respBuilder.toString());
			return null;
		}catch(IOException e){
			log.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}
	}
}
