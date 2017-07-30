package com.minpet.service;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.minpet.data.EbookRepository;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookService {

	@Inject
	private ElasticSearchEbook elasticSearchEbook;
	
	@Inject
	private EbookRepository ebookRepository;
	
	@Transactional
	public void createIndex(long selectedEbookId) throws IOException {
		
		Ebook selectedEbook = ebookRepository.findById(selectedEbookId);
		elasticSearchEbook.createContent(selectedEbook);
		
		selectedEbook.setIndexed(true);
		ebookRepository.save(selectedEbook);
	}

}
