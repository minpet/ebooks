package com.minpet.data;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IFileCandidateRepository;
import com.minpet.model.FileCandidate;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Singleton
public class FileCandidateRepository implements IFileCandidateRepository{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FileCandidateRepository.class.getName());

	@SuppressFBWarnings
	@Resource(lookup="java:global/ebooks/bookstore")
	private URL bookstoreUrl;

	private IEbookRepository ebookRepository;
	private ArrayList<File> files;
	private Map<String, File> candidatesMap;

	@Inject
	public FileCandidateRepository(IEbookRepository ebookRepository){
		this.ebookRepository = ebookRepository;
	}
	
	public List<FileCandidate> getFileCandidates(){
		candidatesMap = new HashMap<>();
		String protocol = bookstoreUrl.getProtocol();
		if("file".equals(protocol)){
			files = new ArrayList<File>();
			try {
				File f = new File(bookstoreUrl.toURI());
				for(File file : f.listFiles()){
					files.add(file);
				}
			} catch(URISyntaxException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}else{
			throw new UnsupportedOperationException("'java:global/ebooks/bookstore' needs to have file:// protocol, but "+protocol+" found");
		}
		
		List<FileCandidate> result = new ArrayList<>();
		for(File file : files){
			if(ebookRepository.findEbookByFileName(file.getName()) == null)
			{
				FileCandidate candidate = new FileCandidate();
				candidate.setUnderlyingFile(file);
				candidatesMap.put(candidate.getHashedName(), candidate.getUnderlyingFile());
				result.add(candidate);
			}
		}
		
		return result;
	}
	
	public File findByHashedName(String string) {
		return candidatesMap.get(string);
	}
}
