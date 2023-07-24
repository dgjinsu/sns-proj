package com.snsproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //jpa 가 자동으로 데이터 베이스 url 을 찾는 동작을 막음
public class SnsprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsprojApplication.class, args);
	}

}
