package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements  WebSocketMessageBrokerConfigurer{

	// stomp는 broker를 사용하여 메세지 전송에 도움을 준다.
	// websocket으로만 코딩할때는 handler를 사용해서 코딩했지만 stomp는 broker를 사용하여 코딩한다.
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat")
		.setAllowedOrigins("http://localhost:8081/")
		.withSockJS();
	// handshake가 일어날 endpoint설정	
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// messageBroker 설정
		registry.enableSimpleBroker("/sub");
		// /sub가 prefix로 붙은 destination의 클라이언트에게 메세지를 보낼 수 있도록 등록
		registry.setApplicationDestinationPrefixes("/pub");
		// /pub가 prefix로 붙은 메세지들은 @MessageMapping에 붙은 method로 바운드된다.(controller)
		
	}
	

}
