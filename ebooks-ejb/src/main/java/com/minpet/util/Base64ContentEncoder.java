package com.minpet.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

@ApplicationScoped
public class Base64ContentEncoder implements Serializable{

	private static final long serialVersionUID = 1L;

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

	public String encodeBytesToBase64Binary(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtils.copy(in, out);
		byte[] raw = out.toByteArray();
		return Base64.encodeBase64String(raw);
	}

	public void decodeToFile(byte[] reqStream, File tmp) throws IOException {
		try(FileOutputStream fos = new FileOutputStream(tmp)){
			fos.write(Base64.decodeBase64(reqStream));
		}
		
	}
}
