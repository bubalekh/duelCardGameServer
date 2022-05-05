package gameModels;

import websocket.controller.CardGuildType;
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

    public void addCreaturesToHand(CardGuildType type) {
        GuildModel guildModel = new GuildModel(type);
        try {
            deckModel.addToDeck(guildModel.getDeck());
            deckModel.shuffleDeck();
            deckModel.shuffleDeck();
            deckModel.shuffleDeck();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

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

    public HandModel getHandModel() {
        return handModel;
    }

    public DeckModel getDeckModel() {
        return deckModel;
    }

    public void addToGraveyard(CreatureModel creature) {
        try {
            this.graveyard.add(creature);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
