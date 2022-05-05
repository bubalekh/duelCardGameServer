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

    public MatchController(WebSocketSession player) {
        this.currentPlayer = PlayerController.FIRST_PLAYER;
        this.readyPlayers = new ArrayList<>();
        this.players = new HashMap<>();
        this.players.put(player, PlayerController.FIRST_PLAYER);
        this.objectMapper = new ObjectMapper();
    }

    //Empty constructor
    public MatchController() {
        this.players = new HashMap<>();
        this.readyPlayers = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
    }

    public void addPlayer(WebSocketSession player) {
        if (this.players.size() < 2) {
            if (this.players.containsValue(PlayerController.FIRST_PLAYER))
                this.players.put(player, PlayerController.SECOND_PLAYER);
            else
                this.players.put(player, PlayerController.FIRST_PLAYER);
        }
        else {
            System.out.println("Maximum players count reached!");
        }
    }

    public void removePlayer(WebSocketSession player) {
        try {
            this.players.remove(player);
        } catch (NullPointerException ignored) {
            System.out.println("Can't remove non-existing player");
        }
    }

    public boolean waitingForPlayers() {
        return players.size() < 2;
    }

    public void updateMatch(WebSocketSession player, MatchEvent event) throws IOException {
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
                String response = objectMapper.writeValueAsString(event);
                this.players.forEach((session, playerController) -> {
                    try {
                        session.sendMessage(new TextMessage(response));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                delegateTurn(); //Передача хода противнику
            }
        }
    }

    private boolean allPlayersReady() {
        return readyPlayers.size() >= 2;
    }

    public boolean hasPlayer(WebSocketSession session) {
        return this.players.containsKey(session);
    }

    private void delegateTurn() {
        currentPlayer = (currentPlayer == PlayerController.FIRST_PLAYER) ? PlayerController.SECOND_PLAYER : PlayerController.FIRST_PLAYER;
    }
}
