package com.minpet.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IEbookService;
import com.minpet.local.interf.IElasticSearchEbook;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookService implements IEbookService{

	private static final long serialVersionUID = 1L;
	private IElasticSearchEbook elasticSearchEbook;
	private IEbookRepository ebookRepository;
	private transient Logger log;

	@Inject
	public EbookService(IElasticSearchEbook elasticSearchEbook, IEbookRepository ebookRepository, Logger log){
		this.ebookRepository=ebookRepository;
		this.elasticSearchEbook=elasticSearchEbook;
		this.log = log;
	}
	
	@Transactional
	public void createIndex(Long selectedEbookId) {
		
		Ebook selectedEbook = ebookRepository.findById(selectedEbookId);
		try {
			elasticSearchEbook.createContent(selectedEbook);
			selectedEbook.setIndexed(true);
			ebookRepository.save(selectedEbook);
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
	}

}
