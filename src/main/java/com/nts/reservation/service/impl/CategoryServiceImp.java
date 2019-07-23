package com.nts.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dao.CategoryDao;
import com.nts.reservation.dto.response.CategoryJSON;
import com.nts.reservation.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {

	private CategoryDao categoryDao;

	@Autowired
	public CategoryServiceImp(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public CategoryJSON getCategoryData() {
		CategoryJSON categoryJSON = new CategoryJSON();
		categoryJSON.setItems(categoryDao.getCategoryList());
		return categoryJSON;
	}

}
