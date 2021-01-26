package com.sale.item.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.sale.item.controller","com.sale.item.config","com.sale.item.service"})
public class ItemsOnSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemsOnSaleApplication.class, args);
	}

}
