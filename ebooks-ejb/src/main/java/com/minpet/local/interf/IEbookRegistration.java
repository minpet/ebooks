package com.minpet.local.interf;

import java.io.Serializable;

import com.minpet.model.Ebook;

public interface IEbookRegistration extends Serializable{

	void register(Ebook newEbook);

}
