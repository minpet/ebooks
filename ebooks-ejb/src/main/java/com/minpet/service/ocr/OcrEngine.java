package com.minpet.service.ocr;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

//import com.uddernetworks.newocr.OCRHandle;
//import com.uddernetworks.newocr.ScannedImage;
//import com.uddernetworks.newocr.database.DatabaseManager;
//import com.uddernetworks.newocr.database.OCRDatabaseManager;

public class OcrEngine {
	
	private static volatile OcrEngine instance;
	private static Object mutex =new Object();
//	private OCRHandle ocrHandle;

	private OcrEngine() throws IOException {
		/*
		File tempdb = File.createTempFile("ocr", "db");
		DatabaseManager db = new OCRDatabaseManager(tempdb);
		ocrHandle = new OCRHandle(db);
		File tempTrain = File.createTempFile("ocr", "train.png");
		try(FileOutputStream fos = new FileOutputStream(tempTrain);
			InputStream input = OcrEngine.class.getResourceAsStream("/all.png")){
				IOUtils.copy(input, fos);
		}
		ocrHandle.trainImage(tempTrain);
		tempTrain.delete();
		*/
	}
	
	public static OcrEngine getInstance() throws IOException {
		OcrEngine result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new OcrEngine();
			}
		}
		return result;
	}

	public String performOcr(RenderedImage img) throws IOException{
		/*
		File tmpImage = File.createTempFile("img", ".png");
		ImageIO.write(img, "PNG", tmpImage);
		ScannedImage scanned = ocrHandle.scanImage(tmpImage);
		String result = scanned.getPrettyString();
		tmpImage.delete();
		return result;
		*/
		throw new IOException("OCR disabled");
	}
}
