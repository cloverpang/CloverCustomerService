package com.clover.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven/>
@SpringBootApplication
public class CloverCustomerServiceApplication {

	@Value("${env}")
	private  String env;

	@RequestMapping("/")
	public String index(){
		return "Hello My First Spring Boot! the env is : " + env;
	}

	@RequestMapping("/conn")
	public String conn(){
		String successConn = "Access database successful!";
		return successConn;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloverCustomerServiceApplication.class, args);
	}
}
