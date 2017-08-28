package com.minpet.local.interf;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.minpet.model.FileCandidate;

public interface IFileCandidateRepository extends Serializable{

	File findByHashedName(String fileParam);

	List<FileCandidate> getFileCandidates();

}
