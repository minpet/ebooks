package com.minpet.test.impl;

import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IBookstoreTranslator;
import com.minpet.model.Ebook;

@ApplicationScoped
public class BookstoreTranslatorMock implements IBookstoreTranslator{

	@Override
	public String getFileFor(Ebook currentEbook) {
		return "undefined";
	}

}
