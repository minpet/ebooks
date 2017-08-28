package com.minpet.test.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.io.FileUtils;

import com.minpet.local.interf.IBookstoreTranslator;
import com.minpet.model.Ebook;

@ApplicationScoped
public class BookstoreTranslatorMock implements IBookstoreTranslator{

	@Override
	public File getFileFor(Ebook currentEbook) {
		try(InputStream is = BookstoreTranslatorMock.class.getResource("/pdf.pdf").openStream()) {
			File tmpF = File.createTempFile("testPdf", ".pdf");
			tmpF.deleteOnExit();
			
			FileUtils.copyInputStreamToFile(is, tmpF);
			return tmpF;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
