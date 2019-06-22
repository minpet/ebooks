package com.minpet.rest.admin;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.minpet.data.FileCandidateRepository;
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

	private FileCandidateJson transform(FileCandidate candidate) {
		FileCandidateJson result = new FileCandidateJson();
		result.setUnderlyingFileName(candidate.getUnderlyingFile().getName());
		result.setHashedName(candidate.getHashedName());
		result.setConflicts(candidate.getConflicts());
		return result;
	}
}
