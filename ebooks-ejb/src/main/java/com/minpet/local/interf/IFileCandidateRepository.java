package com.minpet.local.interf;

import java.io.File;
import java.util.List;

import com.minpet.model.FileCandidate;

public interface IFileCandidateRepository {

	File findByHashedName(String fileParam);

	List<FileCandidate> getFileCandidates();

}
