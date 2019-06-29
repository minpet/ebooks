package com.minpet.rest.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.model.FileCandidate;
import com.minpet.rest.json.FileCandidateJson;

@Path("/admin/fileCandidate")
@RequestScoped
public class FileCandidateResource {
	
	@Inject
	private IFileCandidateRepository fileCandidateRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FileCandidateJson> listFileCandidates(){
		List<FileCandidateJson> result = new ArrayList<>();
		for(FileCandidate candidate: fileCandidateRepository.getFileCandidates()) {
			result.add(transform(candidate));
		}
		
		return result;
	}

	@DELETE
	@Path("/{hashedName}")
	public void deleteFileCandidate(@PathParam("hashedName") String hashedName) {
		File toDelete = fileCandidateRepository.findByHashedName(hashedName);
		if(toDelete.exists()) {
			toDelete.delete();
			return;
		}
		throw new NotFoundException();
	}
	
	private FileCandidateJson transform(FileCandidate candidate) {
		FileCandidateJson result = new FileCandidateJson();
		result.setUnderlyingFileName(candidate.getUnderlyingFile().getName());
		result.setHashedName(candidate.getHashedName());
		result.setConflicts(candidate.getConflicts());
		return result;
	}
}
