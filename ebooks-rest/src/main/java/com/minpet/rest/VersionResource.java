package com.minpet.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.minpet.local.interf.IVersionService;

@Path("version")
@RequestScoped
public class VersionResource {

	@Inject
	private IVersionService versionService;
	
	@Path("text")
	@Produces("text/plain")
	@GET
	public String getVersion() {
		return versionService.getVersion() + "("+versionService.getBuildDate()+")";
	}
}
