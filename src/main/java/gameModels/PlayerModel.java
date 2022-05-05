package gameModels;

import websocket.controller.PlayerType;

import java.util.LinkedList;

public class PlayerModel {

    private int points;
    private final PlayerType type;
    private final DeckModel deckModel;
    private final HandModel handModel;
    private final LinkedList<CreatureModel> graveyard;

    public PlayerModel(PlayerType type) {
        this.points = 0;
        this.type = type;
        this.deckModel = new DeckModel();
        this.handModel = new HandModel();
        this.graveyard = new LinkedList<>();
    }

    public void activateCreature(CreatureModel creature) {
        // ask a server to activate a creature № Creature_id from field Field_id
    }

    public void getCreature() {
        // ask a server to get a creature from the deck
    }

    public void checkAndDraw() {
        // ask a server to perform check and draw
    }

    public PlayerType getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int amount) {
        setPoints(getPoints() + amount);
    }
}
