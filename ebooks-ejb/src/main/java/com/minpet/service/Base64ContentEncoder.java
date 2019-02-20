package com.minpet.service;

import com.aspose.ocr.ImageStream;
import com.aspose.ocr.ImageStreamFormat;
import com.aspose.ocr.OcrEngine;

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
			
			OcrEngine ocrEngine = new OcrEngine();
			for(int i=0; i<pdoc.getNumberOfPages(); i++) {
				PDPage page = pdoc.getPage(i);
				textStripper.setStartPage(i);
				textStripper.setEndPage(i);
				String str = textStripper.getText(pdoc);
				
				sb.append(str);
				for(RenderedImage image : getImagesForPage(page.getResources())) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(image, "png", baos);
					try(ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())){
					ocrEngine.setImage(ImageStream.fromStream(bais, ImageStreamFormat.Png));
						try {
							if(ocrEngine.process()) {
								sb.append(ocrEngine.getText());
							}
						} catch(Exception e) {
							LOG.error(e.getMessage(), e);
						}
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
