package com.web;

import com.web.domain.Board;
import com.web.domain.User;
import com.web.domain.enums.BoardType;
import com.web.repository.BoardRepository;
import com.web.repository.UserRepository;

import org.h2.server.web.WebServlet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootWebApplication implements WebMvcConfigurer {

	public static void main(String[] args) {

		SpringApplication.run(BootWebApplication.class, args);
	}

	@Configuration
	public class Config {
		@Bean
		public ServletRegistrationBean h2servletRegistration() {
			ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
			registration.addUrlMappings("/console/*");
			return registration;
		}
	}

	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) {
		return (args) -> {
			User user = userRepository.save(User.builder()
					.name("havi")
					.password("test")
					.email("havi@gmail.com")
					.createdDate(LocalDateTime.now())
					.build());

			IntStream.rangeClosed(1, 200).forEach(index ->
				boardRepository.save(Board.builder()
                    .title("게시글"+index)
                    .subTitle("순서"+index)
                    .content("컨텐츠")
                    .boardType(BoardType.free)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .user(user).build()));
		};
	}
}