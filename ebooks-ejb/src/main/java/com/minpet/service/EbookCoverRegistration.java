/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minpet.service;

import com.minpet.local.interf.IEbookCoverRegistration;
import com.minpet.local.interf.IImageRepository;
import com.minpet.model.EbookImage;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class EbookCoverRegistration implements IEbookCoverRegistration {

	@Inject
    private IImageRepository imageRepository;
    
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EbookImage registerImage(long ebookId, String mimeType, String encodedContent) {
		return imageRepository.saveEbookImage(ebookId, mimeType, encodedContent);
    }
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EbookImage findImageForEbook(long ebookId) {
		EbookImage image = imageRepository.findImageForEbook(ebookId);
		//force LOB loading
		image.getContent();
		return image;
	}
}
