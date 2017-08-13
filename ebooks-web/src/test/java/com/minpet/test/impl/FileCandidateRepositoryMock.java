package com.minpet.test.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.model.FileCandidate;

@ApplicationScoped
public class FileCandidateRepositoryMock implements IFileCandidateRepository{

	File underlyingFile;
	
	@PostConstruct
	private void init() throws IOException{
		underlyingFile = File.createTempFile("test", "pdf");
		underlyingFile.deleteOnExit();
	}
	
	@Override
	public File findByHashedName(String fileParam) {
		return underlyingFile;
	}

	@Override
	public List<FileCandidate> getFileCandidates() {
		List<FileCandidate> result = new ArrayList<FileCandidate>();
		
		FileCandidate fc = new FileCandidate();
		fc.setUnderlyingFile(underlyingFile);
		fc.setHashedName(underlyingFile.getName());
		
		result.add(fc);
		return result;
	}

}
