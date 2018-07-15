package com.minpet.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.minpet.local.interf.IElasticSearchEbook;

@Model
public class SearchController {

	private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());
	
	@Inject
	private IElasticSearchEbook elasticSearchBook;
	
	private String searchValue;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public void search() {
		try {
			elasticSearchBook.searchForString(searchValue);
		} catch(IOException e){
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
