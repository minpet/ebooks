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
package com.minpet.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.omnifaces.util.Beans;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IEbookService;
import com.minpet.model.Ebook;

@ViewScoped
@Named
public class EbookController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(EbookController.class);

	private IEbookRepository ebookRepository;
	private IEbookService ebookService;
	private Ebook selectedEbook;
	private long selectedId;

	public EbookController(){
		ebookRepository = Beans.getReference(IEbookRepository.class);
		ebookService = Beans.getReference(IEbookService.class);
	}
	
	public void onload(){
		LOGGER.debug("preloading selected ebook (id) "+selectedId);
		selectedEbook = ebookRepository.findById(selectedId);
		LOGGER.debug("preloaded selected ebook "+selectedEbook);
	}

	public long getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(long selectedId) {
		this.selectedId = selectedId;
	}
	
	public Ebook getSelectedEbook(){
		return selectedEbook;
	}
	
	public void createIndex(String selectedEbookId) throws IOException{
		if(!selectedEbookId.trim().equals("")){
			ebookService.createIndex(Long.valueOf(selectedEbookId));
		}
	}
}
