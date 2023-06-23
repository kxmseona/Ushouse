package com.example.demo.Handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {
	
	// 하나의 서버에 여러명의 클라이언트가 접속할 수 있으므로 list를 사용하여 사용자들의 연결유무를 저장한다.
	// 클라이언트가 접속하면 session값을 list에 저장(기록), 클라이언트가 접속을 해제하면 list에서 session값을 삭제(기록)
	private static List<WebSocketSession> list = new ArrayList<>();
	
	// 클라이언트들이 보내는 메세지를 log찍고, 반복문을 활용해서 list에 저장
	// payload란? 전송되는 데이터를 의미한다. 
	// 데이터를 전송할때, Header와 META데이터, 에러체크비트 등과 같은 다양한 요소들을 함께 보내 데이터 전송 효율과 안전성을 높히게된다.
	// 이때 보내고자 하는 데이터 자체를 payload라고 한다.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		String payload = message.getPayload();
		log.info("payload :" + payload);
		
		for(WebSocketSession sess: list) {
			sess.sendMessage(message);
		}
			
	}
	
	// 클라이언트가 접속시 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		list.add(session);
		
		log.info(session + "클라이언트 접속");
	}
	
	// 클라이언트가 접속 해제 시 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		
		log.info(session + "클라이언트 접속 해체");
		list.remove(session);
	}
	
	
	

}
