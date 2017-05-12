/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minpet.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import com.minpet.data.FileCandidateRepository;
import com.minpet.model.Ebook;
import com.minpet.service.EbookRegistration;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class EbookController {
	
	private static final int PREVIEW_IMAGES_NUM = 5;

    @Inject
    private FacesContext facesContext;

    @Inject
    private FileCandidateRepository fileCandidateRepository;
    
    @Inject
    private EbookRegistration memberRegistration;

    @Resource(lookup="java:global/ebooks/bookstore")
    private URL bookstoreUrl;
    
    private Ebook newEbook;

    private String[] previewImages;
    
    @Produces
    @Named
    public Ebook getNewEbook() {
        return newEbook;
    }

    @Produces
    @Named
    public String[] getPreviewImages(){
    	return previewImages;
    }
    
    public void register() throws Exception {
        try {
            memberRegistration.register(newEbook);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    public void initNewMember() throws Exception {
        newEbook = new Ebook();
        
    	FacesContext fc = FacesContext.getCurrentInstance();
    	File candidate = fileCandidateRepository.findByHashedName(fc.getExternalContext().getRequestParameterMap().get("file"));
    	newEbook.setFile(candidate.getAbsolutePath());

        
        if("file".equals(bookstoreUrl.getProtocol())){
        	previewImages = new String[PREVIEW_IMAGES_NUM];
        	
        	File file = new File(newEbook.getFile());
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

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
}
