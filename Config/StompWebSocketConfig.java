package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/stomp/chat")
			.setAllowedOrigins("http://localhost:8081/")
			.withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub");
		// 해당 경로를 simpleBroker를 등록
		// simpleBroker는 해당하는 경로를 구독(sub)하는 사용자에게 메세지를 전달하는 작업을 수행
		registry.enableSimpleBroker("/sub");
		// SimpleBroker의 기능과 외부 message broker에 메세지를 전달
	}

}
