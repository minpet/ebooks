package com.minpet.local.interf;

import java.io.IOException;
import java.io.Serializable;

import com.minpet.model.Ebook;

public interface IElasticSearchEbook extends Serializable{

	void createContent(Ebook selectedEbook) throws IOException;
	
	String[] searchForString(String fulltext) throws IOException;

}
