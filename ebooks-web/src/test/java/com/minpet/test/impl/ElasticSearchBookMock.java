package com.minpet.test.impl;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IElasticSearchEbook;
import com.minpet.model.Ebook;

@ApplicationScoped
public class ElasticSearchBookMock implements IElasticSearchEbook{

	private static final long serialVersionUID = 1L;

	@Override
	public void createContent(Ebook selectedEbook) throws IOException {
		
	}

	@Override
	public String[] searchForString(String fulltext) throws IOException {
		return new String[] {"searchMock"};
	}

}
