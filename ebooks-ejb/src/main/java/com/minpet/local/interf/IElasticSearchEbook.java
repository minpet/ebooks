package com.minpet.local.interf;

import java.io.IOException;

import com.minpet.model.Ebook;

public interface IElasticSearchEbook {

	void createContent(Ebook selectedEbook) throws IOException;

}
