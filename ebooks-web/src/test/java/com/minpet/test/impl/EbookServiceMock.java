package com.minpet.test.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IEbookService;

@ApplicationScoped
public class EbookServiceMock implements IEbookService{

	@Inject
	private IEbookRepository repo;
	
	@Override
	public void createIndex(Long valueOf) {
		repo.findById(valueOf).setIndexed(true);
	}

}
