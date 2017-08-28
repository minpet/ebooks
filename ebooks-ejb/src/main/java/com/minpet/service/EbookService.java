package com.minpet.service;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IElasticSearchEbook;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookService {

	private IElasticSearchEbook elasticSearchEbook;
	private IEbookRepository ebookRepository;

	@Inject
	public EbookService(IElasticSearchEbook elasticSearchEbook, IEbookRepository ebookRepository){
		this.ebookRepository=ebookRepository;
		this.elasticSearchEbook=elasticSearchEbook;
	}
	
	@Transactional
	public void createIndex(long selectedEbookId) throws IOException {
		
		Ebook selectedEbook = ebookRepository.findById(selectedEbookId);
		elasticSearchEbook.createContent(selectedEbook);
		
		selectedEbook.setIndexed(true);
		ebookRepository.save(selectedEbook);
	}

}
