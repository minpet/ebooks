package com.minpet.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.omnifaces.util.Beans;

import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.model.FileCandidate;

@RequestScoped
public class FileCandidatesList {

	private IFileCandidateRepository fileCandidateRepository;
	
	public FileCandidatesList(){
		fileCandidateRepository = Beans.getReference(IFileCandidateRepository.class);
	}
	
	@Produces
    @Named
    public List<FileCandidate> getFileCandidates() {
        return fileCandidateRepository.getFileCandidates();
    }
}