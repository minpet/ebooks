package com.minpet.local.interf;

import java.io.File;
import java.io.Serializable;

import com.minpet.model.Ebook;

public interface IBookstoreTranslator extends Serializable{

	File getFileFor(Ebook currentEbook);

}
