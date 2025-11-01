package com.blogapp.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BlogAppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppConfigServerApplication.class, args);
	}

}
