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

import com.minpet.local.interf.IEbookRegistration;
import com.minpet.local.interf.IEbookRepository;
import com.minpet.local.interf.IFileCandidateCache;
import com.minpet.model.Ebook;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class EbookRegistration implements IEbookRegistration{

	private static final long serialVersionUID = 1L;
	private transient Logger log;
    private IEbookRepository ebookRepository;
    private transient Event<Ebook> ebookEventSrc;
	private IFileCandidateCache fileCandidateCache;

    @Inject
    public EbookRegistration(Logger log, IEbookRepository ebookRepository, Event<Ebook> ebookEvenrSrc,
    		IFileCandidateCache fileCandidateCache){
    	this.log=log;
    	this.ebookRepository=ebookRepository;
    	this.ebookEventSrc=ebookEvenrSrc;
    	this.fileCandidateCache = fileCandidateCache;
    }
    
    public void register(Ebook ebook) {
        log.info("Registering " + ebook.getName());
        ebookRepository.save(ebook);
        fileCandidateCache.invalidate();
        ebookEventSrc.fire(ebook);
    }
}
