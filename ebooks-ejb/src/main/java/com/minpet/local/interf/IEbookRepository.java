package com.minpet.local.interf;

import java.io.Serializable;
import java.util.List;

import com.minpet.model.Ebook;

public interface IEbookRepository extends Serializable{

	Ebook findById(Long id);

	Ebook findEbookByFileName(String name);

	List<Ebook> findAllOrderedByName();

	Ebook findEbookByHashedName(String hashedName);

	void save(Ebook ebook);

	List<String> findConflictsFor(String name);

}
