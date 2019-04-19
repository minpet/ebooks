package com.minpet.service;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jboss.logging.Logger;

import com.minpet.service.ocr.OcrEngine;

@ApplicationScoped
public class Base64ContentEncoder implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Base64ContentEncoder.class);

	public String encodeFileToBase64Binary(File file) throws IOException {

		String str = loadFile(file);
		return Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
	}

	private String loadFile(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		try(RandomAccessFile raf = new RandomAccessFile(file, "r")){
			PDFParser parser = new PDFParser(raf);
			parser.parse();
			PDDocument pdoc = parser.getPDDocument();
			PDFTextStripper textStripper = new PDFTextStripper();
			
			OcrEngine ocrEngine = OcrEngine.getInstance();
			for(int i=0; i<pdoc.getNumberOfPages(); i++) {
				PDPage page = pdoc.getPage(i);
				textStripper.setStartPage(i);
				textStripper.setEndPage(i);
				String str = null;
				
				try{
					str = textStripper.getText(pdoc);
				}catch(IOException e) {
					LOG.debug(e.getMessage(), e);
				}
				
				if(!StringUtils.isEmpty(str)) {
					sb.append(str);
				} else {
					LOG.warn("no text found on page "+i);
				}
				
				List<RenderedImage> images = new ArrayList<>();
				try {
					images = getImagesForPage(page.getResources());
				} catch(IOException e) {
					LOG.debug(e.getMessage(), e);
				}
				
				for(RenderedImage image : images) {
					try {
						sb.append(ocrEngine.performOcr(image));
					} catch(Exception e) {
						LOG.warn("cannot perform OCR on image - page "+i);
						LOG.debug(e.getMessage(), e);
					}
				}
			}
		}
		return sb.toString();
	}

	private List<RenderedImage> getImagesForPage(PDResources resources) throws IOException {
		List<RenderedImage> images = new ArrayList<>();
		for(COSName xObjectName: resources.getXObjectNames()) {
			PDXObject xObject = resources.getXObject(xObjectName);
			
			if(xObject instanceof PDFormXObject) {
				images.addAll(getImagesForPage(((PDFormXObject) xObject).getResources()));
			} else if(xObject instanceof PDImageXObject) {
				images.add(((PDImageXObject) xObject).getImage());
			}
		}
		return images;
	}
}
