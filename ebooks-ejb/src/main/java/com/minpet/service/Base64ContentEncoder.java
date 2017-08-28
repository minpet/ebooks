package com.minpet.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.codec.binary.Base64;

@ApplicationScoped
public class Base64ContentEncoder {

	public String encodeFileToBase64Binary(File file) throws IOException {

		byte[] bytes = loadFile(file);
		return Base64.encodeBase64String(bytes);
	}

	private static byte[] loadFile(File file) throws IOException {
		try (InputStream is = new FileInputStream(file)) {

			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				throw new IOException("file is too big");
			}
			byte[] bytes = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
			return bytes;
		}
	}
}
