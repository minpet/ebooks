package com.minpet.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TestHttpClient {

	public static void setupPipeline(int port) throws IOException {
		HttpHost target = new HttpHost("localhost", port, "http");

		String uri = "/_ingest/pipeline/attachment";
		try(CloseableHttpClient httpclient = HttpClients.createMinimal()){

			@SuppressWarnings("deprecation")
			RequestConfig config = RequestConfig.custom()
				.setAuthenticationEnabled(false)
				.setStaleConnectionCheckEnabled(false).build();

			HttpPut request = new HttpPut(uri);
			request.setConfig(config);
			request.addHeader("content-type", "application/x-www-form-urlencoded");

			StringBuilder builder = new StringBuilder();
			builder.append("{ ");
			builder.append("\"description\":\"Extract attachment information\",");
			builder.append("\"processors\":[");
			builder.append("   { ");
			builder.append("     \"attachment\":{");
			builder.append("        \"field\":\"data\",");
			builder.append("        \"indexed_chars\": -1");
			builder.append("     } ");
			builder.append("   } ");
			builder.append("  ]");
			builder.append(" } ");

			StringEntity params =new StringEntity(builder.toString());
		    request.setEntity(params);
			
			CloseableHttpResponse response = httpclient.execute(target, request);

			int responseCode = response.getStatusLine().getStatusCode();
			if(responseCode < 200 || responseCode >= 400){
				
				throw new IOException(response.getStatusLine().getReasonPhrase()+": "+read(response.getEntity().getContent()));
			}
		}
	}

	private static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
