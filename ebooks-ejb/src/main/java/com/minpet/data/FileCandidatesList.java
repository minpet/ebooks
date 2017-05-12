package com.minpet.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.minpet.model.FileCandidate;

@RequestScoped
public class FileCandidatesList {

	@Inject
	private FileCandidateRepository fileCandidateRepository;
	
	@Produces
    @Named
    public List<FileCandidate> getFileCandidates() {
        return fileCandidateRepository.getFileCandidates();
    }
}
