package com.minpet.test.impl;

import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IEbookRegistration;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookRegistrationMock implements IEbookRegistration{

	@Override
	public void register(Ebook newEbook) {
		throw new RuntimeException("test exception");
	}

}
