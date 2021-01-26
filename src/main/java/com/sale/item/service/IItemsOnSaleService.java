package com.sale.item.service;

import java.util.List;

import com.sale.item.model.ItemOnSale;

public interface IItemsOnSaleService {
	List<ItemOnSale> getItems(String userId);
}
