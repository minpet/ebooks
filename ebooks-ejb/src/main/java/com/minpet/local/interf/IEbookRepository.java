package com.minpet.local.interf;

import java.util.List;

import com.minpet.model.Ebook;

public interface IEbookRepository {

	Ebook findById(Long id);

	Ebook findEbookByFileName(String name);

	List<Ebook> findAllOrderedByName();

	Ebook findEbookByHashedName(String hashedName);

	void save(Ebook ebook);

}
