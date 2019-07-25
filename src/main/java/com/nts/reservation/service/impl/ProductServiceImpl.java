package com.nts.reservation.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nts.reservation.dao.CategoryDao;
import com.nts.reservation.dao.ProductDao;
import com.nts.reservation.dto.response.ProductMap;
import com.nts.reservation.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private static final int MAX_SHOW_COUNT = 4;

	private ProductDao productDao;
	private CategoryDao categoryDao;
	private Logger logger;

	@Autowired
	public ProductServiceImpl(ProductDao productDao, CategoryDao categoryDao, Logger logger) {
		this.productDao = productDao;
		this.categoryDao = categoryDao;
		this.logger = logger;
	}

	@Override
	public int getProductCount(int categoryId) {

		if (isValidCategory(categoryId) == false) {
			categoryId = 0;
		}

		return productDao.selectProductCount(categoryId);
	}

	@Override
	public ProductMap getProductMap(int categoryId, int startProductIndex) {

		ProductMap productMap = new ProductMap();

		if (isValidCategory(categoryId) == false) {
			categoryId = 0;
		}

		productMap.setItems(productDao.selectProductList(categoryId, startProductIndex, MAX_SHOW_COUNT));

		productMap.setTotalCount(getProductCount(categoryId));

		return productMap;

	}

	private boolean isValidCategory(int categoryId) {
		int categoryCount = categoryDao.selectCategoryCount();

		if (categoryId >= 0 && categoryId <= categoryCount) {
			return true;
		}

		logger.error("잘못된 카테고리 값을 요청하였습니다.");

		return false;
	}
}