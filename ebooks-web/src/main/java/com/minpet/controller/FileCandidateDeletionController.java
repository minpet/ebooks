package com.minpet.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.minpet.model.FileCandidate;

@ViewScoped
@Named
public class FileCandidateDeletionController implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FileCandidateDeletionController.class.getName());

	private FileCandidate selectedFile;

	public FileCandidate getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(FileCandidate selectedFile) {
		this.selectedFile = selectedFile;
	}
	
	public void deleteFile() {
		if(selectedFile != null) {
			if(!selectedFile.getUnderlyingFile().delete()) {
				LOGGER.warning("file "+selectedFile.getUnderlyingFile().getAbsolutePath()+" could not be deleted!");
			}
		}
	}
	
	public void nothing() {}
}
