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
package com.minpet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookRepository implements IEbookRepository{

	private static final long serialVersionUID = 1L;
	@Inject
	private transient EntityManager em;
	@Inject
	private transient Logger log;
	
    @Transactional
    @Override
    public Ebook findById(Long id) {
        return em.find(Ebook.class, id);
    }

    @Transactional
    @Override
	public Ebook findEbookByFileName(String name) {
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Ebook> criteria = cb.createQuery(Ebook.class);
	        Root<Ebook> ebook = criteria.from(Ebook.class);
	        criteria.select(ebook).where(cb.equal(ebook.get("file"), name)).orderBy(cb.asc(ebook.get("name")));
	        return em.createQuery(criteria).getSingleResult();
		}catch(NoResultException e){
			log.log(Level.FINE, e.getMessage()+" ["+name+"]", e);
			return null;
		}
	}

    @Transactional
    @Override
	public List<Ebook> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ebook> criteria = cb.createQuery(Ebook.class);
        Root<Ebook> ebook = criteria.from(Ebook.class);
        criteria.select(ebook).orderBy(cb.asc(ebook.get("name")));
        return em.createQuery(criteria).getResultList();
	}

    @Transactional
    @Override
	public Ebook findEbookByHashedName(String hashedName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ebook> criteria = cb.createQuery(Ebook.class);
        Root<Ebook> ebook = criteria.from(Ebook.class);
        criteria.select(ebook).where(cb.equal(ebook.get("hashedName"), hashedName)).orderBy(cb.asc(ebook.get("name")));
        return em.createQuery(criteria).getSingleResult();
	}
	
	@Transactional
	@Override
	public void save(Ebook ebook){
		em.persist(ebook);
	}

	@Override
	public List<String> findConflictsFor(String name) {
		String normalizedName = normalize(name);
		List<String> result = new ArrayList<>();
		for(Ebook candidate : findAllOrderedByName()) {
			String normalizedCandidate = normalize(candidate.getName());
			if(normalizedCandidate.contains(normalizedName) || normalizedName.contains(normalizedCandidate)) {
				result.add(candidate.getName());
			}
		}
		return result;
	}

	private String normalize(String name) {
		return name.replaceAll("[^A-Za-z]", "").toLowerCase();
	}
}
