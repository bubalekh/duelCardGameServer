package websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websocket.message.MatchEvent;
import websocket.message.MatchEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchController extends Controller {
    private final HashMap<WebSocketSession, PlayerController> players;
    private final ArrayList<PlayerController> readyPlayers;
    private PlayerController currentPlayer;
    private final ObjectMapper objectMapper;

    //Empty constructor
    public MatchController(WebSocketSession player) {
        this.currentPlayer = PlayerController.FIRST_PLAYER;
        this.readyPlayers = new ArrayList<>();
        this.players = new HashMap<>();
        this.players.put(player, PlayerController.FIRST_PLAYER);
        this.objectMapper = new ObjectMapper();
    }

    public void addPlayer(WebSocketSession player) {
        if (this.players.size() < 2) {
            this.players.put(player, PlayerController.SECOND_PLAYER);
        }
        else {
            System.out.println("Maximum players count reached!");
        }
    }

    public boolean waitingForPlayers() {
        return players.size() < 2;
    }

    public boolean updateMatch(WebSocketSession player, MatchEvent event) throws IOException {
        //TODO: Add a proper game update method
        if (this.players.containsKey(player)) {
            if (currentPlayer == this.players.get(player)) {
                switch (event.getType()) {

                    case READY:
                        readyPlayers.add(currentPlayer);
                        break;
                    case DRAFT:
                        if (allPlayersReady()) {
                            //TODO: Add a selected guild to players DECK
                            System.out.println(currentPlayer + " has select the " + event.getCards().getFirst().getGuild());
                        }
                        else {
                            MatchEvent err = new MatchEvent(MatchEventType.ERROR);
                            player.sendMessage(new TextMessage(objectMapper.writeValueAsString(err)));
                        }
                        break;
                    case ACTIVATION:
                        break;
                    case CHECK:
                        break;
                    case SUMMON:
                        break;
                    case SYNC:
                        break;
                    case END:
                        break;
                }

                System.out.println("Game update called from " + this.players.get(player));
                this.players.forEach((session, playerController) -> {
                    try {
                        String response = objectMapper.writeValueAsString(event);
                        session.sendMessage(new TextMessage(response));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                currentPlayer = (currentPlayer == PlayerController.FIRST_PLAYER) ? PlayerController.SECOND_PLAYER : PlayerController.FIRST_PLAYER;
            }
        }
        return true;
    }

    private boolean allPlayersReady() {
        return readyPlayers.size() >= 2;
    }
}
