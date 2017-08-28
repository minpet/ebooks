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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.omnifaces.util.Beans;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;

@RequestScoped
public class EbookListProducer {
    private static final Logger LOGGER = Logger.getLogger(EbookListProducer.class.toString());

    private IEbookRepository ebookRepository;
    private List<Ebook> ebooks;

    public EbookListProducer(){
    	ebookRepository = Beans.getReference(IEbookRepository.class);
    }

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Ebook> getEbooks() {
    	LOGGER.log(Level.FINE, "returning ebooks: "+ebooks);
        return ebooks;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Ebook member) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        ebooks = ebookRepository.findAllOrderedByName();
    }
}
