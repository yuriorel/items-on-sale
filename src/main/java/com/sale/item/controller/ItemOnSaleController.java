package com.sale.item.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sale.item.model.ItemOnSale;
import com.sale.item.service.IItemsOnSaleService;

@RestController
@RequestMapping(value="/items-on-sale")
public class ItemOnSaleController {
	public static final Logger logger = LoggerFactory.getLogger(ItemOnSaleController.class);
	@Autowired
	IItemsOnSaleService itemsRepo;
	
	@GetMapping("/recommendations/{userId}")
	public List<ItemOnSale> getApplication(@PathVariable("userId") String userId) {
		List<ItemOnSale> res = this.itemsRepo.getItems(userId);
		logger.info(String.format("GET Response for userid: %s, data: %s", userId, res));
		return res;
	}
	
	@PostMapping("/recommendations/{userId}")
	public List<ItemOnSale> getApplication2(@PathVariable("userId") String userId) {
		List<ItemOnSale> res = this.itemsRepo.getItems(userId);
		logger.info(String.format("POST Response for userid: %s, data: %s", userId, res));
		return res;
	}
}
