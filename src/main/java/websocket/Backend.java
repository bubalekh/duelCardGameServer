package websocket;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websocket.controller.MatchController;
import websocket.message.MatchEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Backend {
    private final HashMap<WebSocketSession, String> gameUrlsHashMap;
    private final HashMap<String, MatchController> gameControllerHashMap;

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
