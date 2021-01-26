package com.sales.item.service.test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.sale.item.application.ItemsOnSaleApplication;
import com.sale.item.model.ItemOnSale;
import com.sale.item.service.IItemsOnSaleService;

@SpringBootTest(classes={ItemsOnSaleApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemsOnSaleServiceTests {

	@Autowired
	IItemsOnSaleService itemsRepo;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void itemsTest(){
		List<ItemOnSale> res = this.itemsRepo.getItems("123456");
		Assertions.assertTrue(res.get(0).getItemId().equalsIgnoreCase("007"));
	}
}
