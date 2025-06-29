package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing//now()로 LocalDateTime 컬럼 default_value 입력
public class StudyBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyBoardApplication.class, args);
	}

}
