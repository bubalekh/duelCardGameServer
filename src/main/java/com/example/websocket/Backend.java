package com.example.websocket;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.example.websocket.controller.MatchController;
import com.example.websocket.message.MatchEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Backend {
    private final Map<WebSocketSession, String> gameUrlsHashMap;
    private final Map<String, MatchController> gameControllerHashMap;

    public Backend() {
        gameUrlsHashMap = new HashMap<>();
        gameControllerHashMap = new HashMap<>();
    }

    public void createGame(WebSocketSession session) throws IOException {
        String newGameUrl = generateGameUrl();
        gameUrlsHashMap.put(session, newGameUrl);
        gameControllerHashMap.put(newGameUrl, new MatchController(session));
        System.out.println("New game created! GameID: " + newGameUrl);
        session.sendMessage(new TextMessage(newGameUrl));
    }

    public void createGame() {
        String newGameUrl = generateGameUrl();
        gameControllerHashMap.put(newGameUrl, new MatchController());
        //TODO: Notify clients about created game
    }

    public void joinGame(WebSocketSession session, String gameUrl) throws IOException {
        if (gameUrlsHashMap.containsValue(gameUrl)) {
            if (gameControllerHashMap.get(gameUrl).waitingForPlayers()) {
                gameUrlsHashMap.put(session, gameUrl);
                gameControllerHashMap.get(gameUrl).addPlayer(session);
                System.out.println("Successfully joined to game " + gameUrl);
                session.sendMessage(new TextMessage("success"));
            }
        }
    }

    public void leaveGame(WebSocketSession session) {
        String gameUrl = "";
        try {
            gameUrl = gameUrlsHashMap.get(session);
            if (gameControllerHashMap.get(gameUrl).hasPlayer(session)) {
                gameControllerHashMap.get(gameUrl).removePlayer(session);
            }
        } catch (NullPointerException ignored) {
            System.out.println("No game to leave");
        }
    }

    public String hasOpenedGame() {
        AtomicReference<String> foundGameId = new AtomicReference<>();
        foundGameId.set("null");
        gameControllerHashMap.forEach((gameId, matchController) -> {
            if (matchController.waitingForPlayers()) {
                foundGameId.set(gameId);
            }
        });
        return foundGameId.get();
    }

    @Async
    public void handleGame(WebSocketSession session, MatchEvent event) {
        System.out.println("Updating game " + gameUrlsHashMap.get(session) + ". Event Type: " + event.getType());
        try {
            gameControllerHashMap.get(gameUrlsHashMap.get(session)).updateMatch(session, event);
        } catch (NullPointerException | IOException e) {
            System.out.println("Game does not exist!!!");
        }
    }

    private String generateGameUrl() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
