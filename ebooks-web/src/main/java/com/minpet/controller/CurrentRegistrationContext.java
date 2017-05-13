package com.minpet.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import com.minpet.data.FileCandidateRepository;
import com.minpet.model.Ebook;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

@SessionScoped
public class CurrentRegistrationContext implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final int PREVIEW_IMAGES_NUM = 5;
	
	private Ebook currentEbook;
	private String[] previewImages;

    @Resource(lookup="java:global/ebooks/bookstore")
    private URL bookstoreUrl;
	
    @Inject
    private FileCandidateRepository fileCandidateRepository;
    
	public Ebook getCurrentEbook(String fileParam) throws Exception {
		if(currentEbook == null){
			currentEbook = new Ebook();
			File candidate = fileCandidateRepository.findByHashedName(fileParam);
	    	currentEbook.setFile(candidate.getName());

	        if("file".equals(bookstoreUrl.getProtocol())){
	        	previewImages = new String[PREVIEW_IMAGES_NUM];
	        	
	        	File file = new File(new File(bookstoreUrl.toURI()).getAbsolutePath()+File.separator+currentEbook.getFile());
	        	RandomAccessFile raf = new RandomAccessFile(file, "r");
	        	FileChannel channel = raf.getChannel();
	        	ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
	        	PDFFile pdffile = new PDFFile(buf);
	        	for(int i=0; i<PREVIEW_IMAGES_NUM; i++){
	                // show the first page
	                PDFPage page = pdffile.getPage(i);
	                Rectangle rect = new Rectangle(0,0,
	                        (int)page.getBBox().getWidth(),
	                        (int)page.getBBox().getHeight());
	                
	                //generate the image
	                BufferedImage img = (BufferedImage) page.getImage(
	                        rect.width, rect.height, //width & height
	                        rect, // clip rect
	                        null, // null for the ImageObserver
	                        true, // fill background with white
	                        true  // block until drawing is done
	                        );
	                
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                ImageIO.write(img, "png", bos);
	                byte[] imageBytes = bos.toByteArray();
	     
	                byte[] result = Base64.getEncoder().encode(imageBytes);
	                previewImages[i] = new String(result);
	                bos.close();
	        	}
	        	raf.close();
	        }else{
	        	previewImages = new String[0];
	        }
		}
		return currentEbook;
	}

	public String[] getCurrentPreviewImages() {
		return previewImages;
	}

	public void clear() {
		currentEbook = null;
		previewImages = null;
	}

}
