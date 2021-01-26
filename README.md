# items-on-sale

This application is simple Spring boot REST service to return list items in response. Spring boot version 2 and Java 1.8 has been used for development
Gradle 6.4 has been used to build executable JAR file.

Files
1. com.sale.item.application.ItemsOnSaleApplication   Spring boot application.
2. com.sale.item.config.SecurityAdapter   Spring security configuration.
3. com.sale.item.config.AuthenticationEntryPoint   Component to send 401 and request authentication.
4. com.sale.item.controller.ItemOnSaleController   REST controller.
5. com.sale.item.exception.CustomAccessDeniedHandler   403 Not Authorized handler.
6. com.sale.item.model.ItemOnSale   Entity object.
7. com.sale.item.service.IItemsOnSaleService   Service interface.
7. com.sale.item.service.ItemsOnSaleService   IItemsOnSaleService implementation to read items from file, load to cache and process requests.

I used spring security with basic authentication. There are two users defined in memory 'user' what may be authenticated but does not have permissions to get data and get 403 and 'customer' what may read data. If authenticated user is the same what items requested for than no need to include it in path because used known from authenticated instance. I assume that requirements to access service from “shopping.rbc.com” means CORS and I implemented it in the same spring security class. 

I did not use any database to simplify service and make it workable in any environment and just read items from json file and desirialize it to Java HashMap to keep it in lokal memory because data updated dayly and mostly static. After updating data service should be notified in some way to upload latest data but I just run task in separate thread using Java ScheduledExecutorService to read file periodically and refresh HashMap. Java ReadWriteLock has been used to synchronize therads because in most cases read only required.

Executable JAR file located under build/libs folder. File name is items-on-sale-0.0.1-SNAPSHOT.jar. It is executable JAR file and can be run from CLI. Default parameters in application.properties file but may be owerriten in CLI. 

Simple file with data located under items folder. File name is items.json.

Log will be cretaed unde log folder in location where JAR executed.

I used postman to test service.

HTTPS connection used self-signed certificate.