package com.acfun;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AcerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcerApplication.class, args);
	}
	@RequestMapping("/")
	public String test(){
		return "Hello,World";
	}
}
