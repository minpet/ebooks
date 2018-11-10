package com.minpet.test.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.minpet.local.interf.IEbookRepository;
import com.minpet.model.Ebook;

@ApplicationScoped
public class EbookRepositoryMock implements IEbookRepository{

	private Ebook ebook;
	
	@PostConstruct
	private void init(){
		ebook = new Ebook();
		ebook.setFile("test.pdf");
		ebook.setName("test");
		ebook.setId(1L);
		ebook.setIndexed(false);
		ebook.setRegistered(true);
	}
	
	@Override
	public Ebook findById(Long id) {
		return ebook;
	}

	@Override
	public Ebook findEbookByFileName(String name) {
		return ebook;
	}

	@Override
	public List<Ebook> findAllOrderedByName() {
		return new ArrayList<Ebook>(){{
			add(ebook);
			}};
	}

	@Override
	public Ebook findEbookByHashedName(String hashedName) {
		return ebook;
	}

	@Override
	public void save(Ebook ebook) {
		
	}

	@Override
	public List<String> findConflictsFor(String name) {
		return new ArrayList<>();
	}

}
