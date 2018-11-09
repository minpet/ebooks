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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.omnifaces.util.Beans;

import com.minpet.local.interf.IEbookRegistration;
import com.minpet.model.Ebook;

@Model
public class NewEbookController {
	
    private FacesContext facesContext;
    private IEbookRegistration ebookRegistration;
    private CurrentRegistrationContext currentRegistrationContext;
    private Ebook newEbook;
    private List<String> previewImages;
    private boolean ebookSaved = false;
    
    public NewEbookController(){
    	facesContext = Beans.getReference(FacesContext.class);
    	ebookRegistration = Beans.getReference(IEbookRegistration.class);
    	currentRegistrationContext = Beans.getReference(CurrentRegistrationContext.class);
    }
    
    @Produces
    @Named
    public Ebook getNewEbook() {
        return newEbook;
    }

    @Produces
    @Named
    public Collection<String> getPreviewImages(){
    	return previewImages;
    }
    
    @Produces
    @Named
    public boolean isEbookSaved(){
    	return ebookSaved;
    }
    
    public void register(){
        try {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            ebookSaved = true;
            currentRegistrationContext.clear();
            previewImages = currentRegistrationContext.getCurrentPreviewImages();
            ebookRegistration.register(newEbook);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration Unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    @PostConstruct
    private void initNewMember() throws IOException {
    	String fileParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("file"); 
    	newEbook = currentRegistrationContext.getCurrentEbook(fileParam);
        previewImages = currentRegistrationContext.getCurrentPreviewImages();
        ebookSaved = false;
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
