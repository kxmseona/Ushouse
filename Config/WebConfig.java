package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer을 구현하는 이유
// 이를 사용하면 override가 가능하다.(즉, @EnableWebMvc가 자동으로 세팅해주는 설정에 개발자가 원하는 설정을 추가할 수 있다.)
// 정리! 일일이 다 개발자가 만드는 @Bean과 달리
// WebMvcConfigurer은 기본적으로 세팅되어 있는 상태에서 개발자가 원하는 부분만 override해서 구현할 수 있다.



@Configuration
public class WebConfig implements WebMvcConfigurer{

	private String resourcePath = "/upload/**"; // view에서 접근할 경로
	private String savePath = "file:///C:/ushouse_img/"; // 실제 파일 저장 경로
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourcePath)
				.addResourceLocations(savePath);
		
	}
	
}
