package com.example.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.websocket.message.MatchEvent;

public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {

    ObjectMapper objectMapper;
    Backend backend;

    public EventHandler(Backend backend) {
        objectMapper =  new ObjectMapper();
        this.backend = backend;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //TODO: Add an additional check for reconnect to existing game
        System.out.println("Socket Connected: " + session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        if (message.getPayload().contains("host")) {
            backend.createGame(session);
        }
        else if (message.getPayload().contains("join")) {
            String gameId = message.getPayload().substring(5);
            if (backend.hasOpenedGame().equals(message.getPayload().substring(5)))
            backend.joinGame(session, gameId);
        }
        else if (message.getPayload().contains("type")) {
            try {
                MatchEvent matchEvent = objectMapper.readValue(message.getPayload(), MatchEvent.class);
                backend.handleGame(session, matchEvent);
            } catch (JsonProcessingException ignored) {
                System.out.println("Broken event received. Payload: " + message.getPayload());
            }
        }
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        backend.leaveGame(session);
        super.afterConnectionClosed(session, closeStatus);
    }
}
