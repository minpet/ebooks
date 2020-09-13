package com.minpet.local.interf;

import com.minpet.model.EbookImage;

public interface IImageRepository {

	EbookImage saveEbookImage(long ebookId, String mimeType, String encodeFileToBase64Binary);

	EbookImage findImageForEbook(Long id);
	
}
