package com.minpet.local.interf;

import com.minpet.model.EbookImage;

public interface IEbookCoverRegistration {

	EbookImage registerImage(long ebookId, String mimeType, String encodedContent);

	EbookImage findImageForEbook(long ebookId);

}
