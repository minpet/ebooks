package com.minpet.local.interf;

import java.io.File;

import com.minpet.model.Ebook;

public interface IBookstoreTranslator {

	File getFileFor(Ebook currentEbook);

}
