package com.acfun;

import com.acfun.domain.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class AcerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcerApplication.class, args);
		List<Person> list=new ArrayList<>();
		list.forEach(person -> System.out.println(person.getFirstName()));
	}
	@RequestMapping("/")
	public String test(){
		return "Hello,World";
	}
}
