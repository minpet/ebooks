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
import com.minpet.local.interf.IFileCandidateCache;
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

	private ArrayList<File> files;
	private Map<String, File> candidatesMap;
	@Inject
	private IEbookRepository ebookRepository;
	@Inject
	private IFileCandidateCache fileCandidateCache;

	
	public List<FileCandidate> getFileCandidates(){
		if(fileCandidateCache.isValid()) {
			return fileCandidateCache.getCachedValues();
		} else {
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
					candidate.setConflicts(ebookRepository.findConflictsFor(candidate.getUnderlyingFile().getName()));
					candidatesMap.put(candidate.getHashedName(), candidate.getUnderlyingFile());
					result.add(candidate);
				}
			}
			
			fileCandidateCache.setCachedValues(result);
			return result;
		}
	}
	
	public File findByHashedName(String string) {
		return candidatesMap.get(string);
	}
}
