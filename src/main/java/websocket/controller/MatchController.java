package websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gameModels.GameModel;
import gameModels.PlayerModel;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import websocket.message.MatchEvent;
import websocket.message.EventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchController extends Controller {

    private final HashMap<WebSocketSession, PlayerType> players;
    private final ArrayList<WebSocketSession> readyPlayers;
    private PlayerType currentPlayer;
    private final ObjectMapper objectMapper;
    private final GameModel gameModel;

    public MatchController(WebSocketSession player) {
        this.currentPlayer = PlayerType.FIRST_PLAYER;
        this.readyPlayers = new ArrayList<>();
        this.players = new HashMap<>();
        this.players.put(player, currentPlayer);
        this.objectMapper = new ObjectMapper();
        this.gameModel = new GameModel();
    }

    //Empty constructor
    public MatchController() {
        this.players = new HashMap<>();
        this.readyPlayers = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        this.gameModel = new GameModel();
    }

    public void addPlayer(WebSocketSession player) {
        if (this.players.size() < 2) {
            if (this.players.containsValue(PlayerType.FIRST_PLAYER)) {
                this.players.put(player, PlayerType.SECOND_PLAYER);
                this.gameModel.addPlayer(new PlayerModel(PlayerType.SECOND_PLAYER));
            }
            else {
                this.players.put(player, PlayerType.FIRST_PLAYER);
                this.gameModel.addPlayer(new PlayerModel(PlayerType.FIRST_PLAYER));
            }
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
            if (event.getType() == EventType.READY) {
                if (!allPlayersReady()) {
                    if (!waitingForPlayers()) {
                        readyPlayers.add(player);
                        System.out.println(players.get(player) + " is Ready!");
                    }
                    else System.out.println("Not enough players to start game");
                }
                else System.out.println("All players are ready already");
            }
            else if (currentPlayer == this.players.get(player)) {
                switch (event.getType()) {

                    case DRAFT:
                        if (allPlayersReady()) {
                            //TODO: Add a selected guild to players DECK
                            System.out.println(currentPlayer + " has select the " + event.getCards().getFirst().getGuild());
                            delegateTurn();
                        }
                        else {
                            MatchEvent err = new MatchEvent(EventType.ERROR);
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
            }
            else System.out.println("Event received from " + this.players.get(player) + " but its " + currentPlayer +"'s turn");
            handleConnections(event);
        }
    }

    private boolean allPlayersReady() {
        return readyPlayers.size() >= 2;
    }

    public boolean hasPlayer(WebSocketSession session) {
        return this.players.containsKey(session);
    }

    private void delegateTurn() {
        currentPlayer = (currentPlayer == PlayerType.FIRST_PLAYER) ? PlayerType.SECOND_PLAYER : PlayerType.FIRST_PLAYER;
    }

    private void handleConnections(MatchEvent event) throws JsonProcessingException {
        String response = objectMapper.writeValueAsString(event);
        this.players.forEach((session, playerType) -> {
            try {
                session.sendMessage(new TextMessage(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
