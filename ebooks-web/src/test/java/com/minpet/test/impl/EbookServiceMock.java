package com.minpet.test.impl;

import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IEbookService;

@ApplicationScoped
public class EbookServiceMock implements IEbookService{

	@Override
	public void createIndex(Long valueOf) {
		
	}

}
