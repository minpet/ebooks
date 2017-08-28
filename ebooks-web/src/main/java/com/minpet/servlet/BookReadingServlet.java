package com.minpet.servlet;

import java.io.File;
import javax.servlet.annotation.WebServlet;
import org.omnifaces.util.Beans;

import com.minpet.local.interf.IBookstoreTranslator;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;

@WebServlet("/read/*")
public class BookReadingServlet extends ResumableServlet{

	private static final long serialVersionUID = 1L;

	private final IEbookRepository ebookRepository;
	private final IBookstoreTranslator bookstoreTranslator;
	
	public BookReadingServlet(){
		ebookRepository = Beans.getReference(IEbookRepository.class);
		bookstoreTranslator = Beans.getReference(IBookstoreTranslator.class);
	}

	@Override
	protected File getResponseFileFromUrlParts(String[] parts) {
		String hashedName = null;
		for(String part: parts){
			if(part.endsWith(".ebook")){
				hashedName = part.split(".ebook")[0];
				break;
			}
		}
		if(hashedName != null){
			Ebook ebook = ebookRepository.findEbookByHashedName(hashedName);
			return bookstoreTranslator.getFileFor(ebook);
		}
		return null;
	}

	@Override
	protected String getSupportedExtension() {
		return ".pdf";
	}
}
