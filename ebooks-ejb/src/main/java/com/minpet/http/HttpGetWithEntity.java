package com.minpet.http;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
	public final static String METHOD_NAME = "GET";

	public HttpGetWithEntity(String uri) {
		super();
		setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
	    return METHOD_NAME;
	}
}
