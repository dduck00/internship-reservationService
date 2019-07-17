package com.nts.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.reservation.service.PromotionService;

@RestController
@RequestMapping(path = "/api")
public class ApiSet {

	@Autowired
	PromotionService promotionService;

	@GetMapping("/promotions")
	public String getPromotion() {
		String promotionJSON = "{\"items\": " + promotionService.getPromotionList() + "}";

		return promotionJSON;
	}

	@GetMapping("/categories")
	public String getCategories() {
		String promotionJSON = "{\"items\": " + promotionService.getPromotionList() + "}";

		return promotionJSON;
	}

	@GetMapping("/products")
	public String getProducts(@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId,
		@RequestParam(name = "start", required = false, defaultValue = "0") int start) {
		String promotionJSON = "{\"items\": " + promotionService.getPromotionList() + "}";

		return promotionJSON;
	}
}
