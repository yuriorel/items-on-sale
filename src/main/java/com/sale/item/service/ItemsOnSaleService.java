package com.sale.item.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sale.item.model.ItemOnSale;

@Service
public class ItemsOnSaleService implements IItemsOnSaleService {
	public static final Logger logger = LoggerFactory.getLogger(ItemsOnSaleService.class);

	private ScheduledExecutorService executor;
	ReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Value(value = "${sales.item.refresh.timeout:3600}")
	private int refreshTimeout;
	
	@Value(value = "${sales.item.source}")
	private String dataSource;
	
	private Map<String, List<ItemOnSale>> dataMap;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<ItemOnSale> getItems(String userId) {
		lock.readLock().lock();
		try {
			return this.dataMap.getOrDefault(userId, new ArrayList<>());
		} finally {
			lock.readLock().unlock();
		}
	}

	@PostConstruct
	protected void afterInit() {
		this.executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
			lock.writeLock().lock();
			try {
				//String jsonData = "{\"customer\": [{\"itemId\":\"123\",\"itemName\":null},{\"itemId\":\"001\",\"itemName\":null}]}";		
				logger.info(String.format("Reding items-on-sale file: %s", this.dataSource));
				String jsonData = new String(Files.readAllBytes(Paths.get(this.dataSource)));
				logger.info(String.format("Items-on-sale: %s", jsonData));
				
				TypeReference<HashMap<String, List<ItemOnSale>>> typeRef 
				  = new TypeReference<HashMap<String, List<ItemOnSale>>>() {};
				this.dataMap = mapper.readValue(jsonData, typeRef);
				logger.info(String.format("Items-on-sale data: %s", this.dataMap));
				
			} catch (IOException e) {
				logger.error(String.format("Error in runnable task thread: %s", e.getMessage()));
			} finally {
				lock.writeLock().unlock();
			}
		};

		executor.scheduleWithFixedDelay(task, 0, this.refreshTimeout, TimeUnit.SECONDS);
	}

	@PreDestroy
	protected void beforeFestroy() {
		if (this.executor != null && !this.executor.isShutdown()) {
			this.executor.shutdown();
			logger.error("executor has been shutdown");
		}
	}
}
